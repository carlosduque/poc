#!/usr/bin/env python
"""
lookout -- migrate Outlook 97 addressbooks to open-source formats.

This script reads the 'Tab Separated Values (Windows)' export format
produced by Outlook 97 and translates it into a Linux-friendly format.
Formats presently supported include GnomeCard and KAB.  The code is
designed to make plugging in new translators relatively easy.
"""
version = "1.3"

import sys, os, string, re, getopt

class OutlookAddressBook:
    "Construct a key list and entry dictionary set from an Outlook 97 export."
    def __init__(self, ifp):
        "Make instance containing exported Outlook data from a file object."
        data = ifp.readlines()
        # Deal with Microsoft's brain-dead convention for including
        # newlines in an entry -- the fields containing the newline
        # are quoted.  So if a line has an odd number of quotes, treat
        # following lines as continuations until we get an even
        # number. Also remove embedded CRs.
        munged_data = []
        while data:
            entry = string.rstrip(data.pop(0))
            if not entry:
                continue
            while string.count(entry, '"') % 2:
                entry = entry + "\n" + data.pop(0)
            entry = string.replace(entry, "\r", "")
            munged_data.append(entry)
            # the first line contains the list of field names.
            # check the separator: Outlook uses "\t" but Outlook Express uses ","
            if string.find(munged_data[0], ",") >= 0:
                    SEP=","
            else:
                    SEP="\t"
                    # Now split the fields in each line
                    data = map(lambda x: string.split(string.rstrip(x), SEP), munged_data)
                    # Now, we have to assume that there could have been embedded
                    # tabs in each entry as well.  So we play a variation on the
                    # above theme.
                    munged_data = []
                    for entry in data:
                        munged_entry = []
                        while entry:
                            field = entry.pop(0)
                            while string.count(field, '"') % 2:
                                field = field + SEP + entry.pop(0)
                            munged_entry.append(field)
                        munged_data.append(munged_entry)
                    data = munged_data
                    # Handle quoting now.
                    munged_data = []
                    for entry in data:
                        munged_entry = []
                        while entry:
                            field = entry.pop(0)
                            if len(field) >= 2 and field[0] == '"' and field[-1] == '"':
                                field = string.replace(field[1:-1], '""', '"')
                            munged_entry.append(field)
                        munged_data.append(munged_entry)
                    data = munged_data
                    # Peel off the first entry, which is actually the list of fieldnames.
                    self.fieldnames = data.pop(0)
                    # Turn the parsed fields into a list of dictionaries, one per entry, 
                    # keyed by the fields in the first (pseudo-)entry.
                    self.entries = []
                    for record in data:
                        entry = {}
                        for i in range(len(record)):
                            if record[i]:
                                entry[self.fieldnames[i]] = record[i]
                        self.entries.append(entry)
    def consolidate(self, delimiter):
                    "Most output formats consolidate their multiline fields."
                    for entry in self.entries:
                        email1addr = entry.get("E-mail Address")
                        email1name = entry.get("E-mail Display Name")
                        if email1addr and email1name and email1name != email1addr:
                            entry["E-mail Address"] = email1addr + " ("+email1name+")"
                        email2addr = entry.get("E-mail 2 Address")
                        email2name = entry.get("E-mail 2 Display Name")
                        if email2addr and email2name and email2name != email2addr:
                            entry["E-mail 2 Address"] = email2addr + " ("+email2name+")"
                        email3addr = entry.get("E-mail 3 Address")
                        email3name = entry.get("E-mail 3 Display Name")
                        if email3addr and email3name and email3name != email3addr:
                            entry["E-mail 3 Address"] = email3addr + " ("+email3name+")"
                        for loc in ("Business", "Home", "Other"):
                            fulladdr = []
                            if entry.has_key(loc + " Street"):      fulladdr.append(entry[loc + " Street"])
                            if entry.has_key(loc + " Street 2"):      fulladdr.append(entry[loc + " Street 2"])
                            if entry.has_key(loc + " Street 3"):      fulladdr.append(entry[loc + " Street 3"])
                            fulladdr = string.join(fulladdr, delimiter)
                            if fulladdr:
                                entry[loc + " Street"] = fulladdr
    def fullname(self, entry):
        "Return a synthesized full-name field."
        fullname = []
        if entry.has_key("Title"):      fullname.append(entry["Title"])
        if entry.has_key("First Name"): fullname.append(entry["First Name"])
        if entry.has_key("Middle Name"):fullname.append(entry["Middle Name"])
        if entry.has_key("Last Name"):  fullname.append(entry["Last Name"])
        if entry.has_key("Suffix"):     fullname.append(entry["Suffix"])
        return string.join(fullname, " ")

# KAB save format is kind of weird.  It is actually a layer on top of
# something called QConfigDB, which fills in a small subset of
# QConfigDB fields and wedges the rest of the addressbook data into a
# 'custom' property which is parsed by the address book program
# itself.  Some of the QConfigDB fields look like they ought to
# correspond to Outlook stuff -- but Abbrowser never actually writes
# them so we don't either. This is what makes the report generator
# look so odd.

class KABTranslator:
    "Dump an Outlook 97 entry list in Kmail Address Book format."
    triggering_option = "--kab"
    indicator_directory = "~/.kde"
    default_location = "~/.kde/share/apps/kab/addressbook.kab"

    # Canned header always added when Abbrowser creates an address book file.
    fileheader = '''
# [File created by lookout version %s]
# subsections:
[config]
  # key-value-pairs:
  user_1="(User field 1)"
  user_2="(User field 2)"
  user_3="(User field 3)"
  user_4="(User field 4)"
  user_headline="(User fields)"
[END config]
[entries]
  # subsections:
'''

    # We put this in each entry because the legacy null address list
    # might actually be interpreted by other things using QConfigDB
    # save format.
    subsectheader= '''    # subsections:
    [addresses]
    [END addresses]
'''

    # Map Outlook fields to keys in the KAB custom string.
    # The keys in this map are a complete list of Outlook 97 export fields.
    # The values are corresponding internal fields, e.g. items that get
    # wedged into the custom string in the underlying QConfigDB format.
    custom_field_map = {
       #"Title" handled externally
       "First Name"		: "X-FirstName",
       "Middle Name"		: "X-MiddleName",
       "Last Name"		: "X-LastName",
       "Suffix"			: "X-Suffix",
       "Company"		: "ORG",
       "Department"		: "X-Department",
       "Job Title"		: "ROLE",
       "Business Street"	: "X-BusinessAddressStreet",
       #"Business Street 2"	# Note: concatenate to above
       #"Business Street 3"	# Note: concatenate to above
       "Business City"		: "X-BusinessAddressCity",
       "Business State"		: "X-BusinessAddressState",
       "Business Postal Code"	: "X-BusinessAddressPostalCode",
       "Business Country"	: "X-BusinessAddressCountry",
       "Home Street"		: "X-HomeAddressStreet",
       #"Home Street 2"		# Note: concatenate to above
       #"Home Street 3"		# Note: concatenate to above
       "Home City"		: "X-HomeAddressCity",
       "Home State"		: "X-HomeAddressState",
       "Home Postal Code"	: "X-HomeAddressPostalCode",
       "Home Country"		: "X-HomeAddressCountry",
       "Other Street"		: "X-OtherAddressStreet",
       #"Other Street 2"	# Note: concatenate to above
       #"Other Street 3"	# Note: concatenate to above
       "Other City"		: "X-OtherAddressCity",
       "Other State"		: "X-OtherAddressState",
       "Other Postal Code"	: "X-OtherAddressPostalCode",
       "Other Country"		: "X-OtherAddressCountry",
       "Assistant's Phone"	: "X-Assistant's-Phone",
       "Business Fax"		: "X-Business-Fax",
       "Business Phone"		: "X-BusinessPhone",
       "Business Phone 2"	: "X-BusinessPhone2",
       "Callback" 		: "X-Callback",
       "Car Phone"		: "X-CarPhone",
       "Company Main Phone"	: "X-CompanyMainPhone",
       "Home Fax"		: "X-HomeFax",
       "Home Phone"		: "X-HomePhone",
       "Home Phone 2"		: "X-HomePhone2",
       "ISDN"			: "X-ISDN",
       "Mobile Phone"		: "X-Mobile Phone",
       "Other Fax"		: "X-OtherFax",
       "Other Phone"		: "X-OtherPhone",
       "Pager"			: "X-Pager",
       "Primary Phone"		: "X-PrimaryPhone",
       "Radio Phone"		: "X-RadioPhone",
       "TTY/TDD Phone"		: "X-TtyTddPhone",
       "Telex"			: "X-Telex",
       "Account"		: "X-CUSTOM-Account",
       "Anniversary"		: "X-Anniversary",
       "Assistant's Name"	: "X-AssistantsName",
       "Billing Information"	: "X-CUSTOM-Billing Information",
       "Birthday"		: "BDAY",
       "Categories"		: "X-CUSTOM-Categories",
       "Children"		: "X-CUSTOM-Children",
       "E-mail Address"		: "EMAIL",
       #"E-mail Display Name"	# Merge into "EMAIL" field
       "E-mail 2 Address"	: "X-E-mail2",
       #"E-mail 2 Display Name"	# Merge into "X-E-mail2" field
       "E-mail 3 Address"	: "X-E-mail3",
       #"E-mail 3 Display Name"	# Merge into "X-E-mail3" field
       "Gender"			: "X-CUSTOM-Gender",
       "Government ID Number"	: "X-CUSTOM-GovernmentIDNumber",
       "Hobby"			: "X-CUSTOM-Hobby",
       "Initials"		: "X-CUSTOM-Initials",
       "Keywords"		: "X-CUSTOM-Keywords",
       "Language"		: "X-CUSTOM-Language",
       "Location"		: "X-CUSTOM-Location",
       "Mileage"		: "X-CUSTOM-Mileage",
       #"Notes"			# Handled externally: comment
       "Office Location"	: "X-CUSTOM-OfficeLocation",
       "Organizational ID Number"	: "X-CUSTOM-OrganizationalIDNumber",
       "PO Box"			: "X-CUSTOM-POBox",
       "Private"		: "X-CUSTOM-Private",
       "Profession"		: "X-Profession",
       "Referred By"		: "X-CUSTOM-Referred By",
       "Spouse"			: "X-SpousesName",
       #"User 1"		# Handled externally
       #"User 2"		# Handled externally
       #"User 3"		# Handled externally
       #"User 4"		# Handled externally
       #"Web Page" 		# Handled externally: URLs
    }    

    def __init__(self, outlook_data):
        self.addressbook = outlook_data

    def mangle_output(self, str):
        "Re-format string contents for KAB output."
        str = string.replace(str, "\\", "\\\\")
        str = string.replace(str, "\n", "\\n")
        str = string.replace(str, "\"", "\\\"")
        return str

    def report_field(self, entry, outlook_key, abb_key, multi, ofp):
        "Write a specified Outlook field as a KAB field."
        value = entry.get(outlook_key)
        if value is None:
            ofp.write('    %s=""\n' % (abb_key,))
        else:
            if multi:
                trailer = "\\e"
            else:
                trailer = ""
            ofp.write('    %s="%s%s"\n' % (abb_key, self.mangle_output(entry[outlook_key]), trailer))

    def translate(self, ofp, existing=""):
        "Dump the entry contents in KAB format."
        self.addressbook.consolidate("\n ")
        entryindex = 0
        if not existing:
            ofp.write(KABTranslator.fileheader % version)
        else:
            ofp.write("# [Data appended by lookout version %s]\n" % version)
            re_index = re.compile(r"^ *\[([0-9]+)\]$")
            for line in string.split(existing, "\n"):
                see = re_index.search(line)
                if see:
                    entryindex = int(see.group(1))
        # Now dump everything...
        for entry in self.addressbook.entries:
            entryindex = entryindex + 1
            # Do various pre-translations next.
            fullname = self.addressbook.fullname(entry) 
            # Here is the begining of the actual write.
            ofp.write("  [%d]\n" % entryindex)
            ofp.write(KABTranslator.subsectheader)

            # Curried reports....mmmmm...good...
            translate_entry = lambda okey, akey, multi=0, entry=entry, ofp=ofp, self=self: self.report_field(entry, okey, akey, multi, ofp) 

            # We write fields in exact mimicry of the sequence in the KAB file.
            translate_entry("Web Page", "URLs", multi=1)
            # AbbBrowser doesn't use the QConfigDB Birthday field, instead it
            # wedges the birthday into the Custom string.
            ofp.write('    birthday="0, 0, 0"\n')
            translate_entry("Notes", "comment")
            # Internal fields
            ofp.write('    custom="KMail:1.0\\n')
            for (key, value) in KABTranslator.custom_field_map.items():
                outlook_data = entry.get(key)
                if outlook_data:
                    ofp.write(self.mangle_output(" %s\n %s\n[EOR]\n" % (value, outlook_data)))
            # Write synthesized internal fields and trailer
            ofp.write(self.mangle_output(" X-FileAs\n %s\n[EOR]\n"%fullname))
            # Now put together the synthesized address fields
            fulladdr = []
            for loc in ("Business", "Home", "Other"):
                if entry.has_key(loc + " Street"):    fulladdr.append(entry[loc + " Street"])
                if entry.has_key(loc + " City"):      fulladdr.append(entry[loc + " City"])
                if entry.has_key(loc + " State"):     fulladdr.append(entry[loc + " State"])
                if entry.has_key(loc + " Postal Code"):fulladdr.append(entry[loc + " Postal Code"])
                if entry.has_key(loc + " Country"):    fulladdr.append(entry[loc + " Country"])
                ofp.write(self.mangle_output(" X-%sAddress\n %s\n[EOR]\n" % (loc, string.join(fulladdr, "\n "))))
            # Some weird legacy thing going on...we just emulate it.
            ofp.write(self.mangle_output(" N\n %s\n[EOR]\n" % fullname))
            ofp.write('[EOS]\\n\\e"\n')
            # Remaining external fields...
            # This one is odd -- terminated with \e like a multi record,
            # so you'd think it would include secondary and tertiary
            # email addresses.  It doesn't.
            translate_entry("E-mail Address", "emails", multi=1)
            translate_entry("First Name", "firstname", multi=0)
            ofp.write('    fn="%s"\n' % self.mangle_output(fullname))
            ofp.write('    keywords=""\n')
            translate_entry("Last Name", "lastname", multi=0)
            translate_entry("Middle Name", "middlename", multi=0)
            translate_entry("Title", "nameprefix", multi=0)
            ofp.write('    rank=""\n')
            ofp.write('    talk=""\n')
            # To be excruciatingly exact, this ought to composed from
            # some subset of the phone fields.  Unfortunately it is
            # not clear what that subset is.
            ofp.write('    telephone=""\n')
            ofp.write('    title=""\n')
            # Abbrowser doesn't appear to use these.  Stash them here
            # against the day it does.
            translate_entry("User 1", "user1", multi=0)
            translate_entry("User 2", "user2", multi=0)
            translate_entry("User 3", "user3", multi=0)
            translate_entry("User 4", "user4", multi=0)
            ofp.write("  [END %d]\n" % entryindex)
        ofp.write("[END entries]\n")

    def skip_to_end(self, ofp):
        "Skip to where the next entry should be appended."
        preserve = ""
        while 1:
            next = ofp.readline()
            # We want all but the last line
            if next[0:13] == "[END entries]":
                ofp.seek(-len(next), 1)
                break
            else:
                preserve = preserve + next
            # Should never happen -- only gets hit if [END entries] missing
            if not next:
                break
        return preserve

# GnomeCard format is much more straightforward, basically a Vcard subset.
# It lacks a lot of fields needed to represent Outlook97 entries, though.

class GnomeCardTranslator:
    "Dump an Outlook 97 entry list in GnomeCard format."
    triggering_option = "--gc"
    indicator_directory = "~/.gnome"
    default_location = "~/.gnome/GnomeCard.gcrd"

    def __init__(self, outlook_data):
        self.addressbook = outlook_data

    # Map Outlook fields to GnomeCard field names.
    # The keys in this map are a complete list of Outlook 97 export fields.
    # The values are either corresponding Vcard fields or (when a field is
    # compound) pairs of field name and subfield index. 
    field_map = {
       "Title"			: ("N", 3),
       "First Name"		: ("N", 1),
       "Middle Name"		: ("N", 2),
       "Last Name"		: ("N", 0),
       "Suffix"			: ("N", 4),
       "Company"		: "ORG",
       #"Department"		: None,
       "Job Title"		: "TITLE",
       "Business Street"	: ("ADR;WORK", 2),
       #"Business Street 2"	: None,
       #"Business Street 3"	: None,
       "Business City"		: ("ADR;WORK", 3),
       "Business State"		: ("ADR;WORK", 4),
       "Business Postal Code"	: ("ADR;WORK", 5),
       "Business Country"	: ("ADR;WORK", 6),
       "Home Street"		: ("ADR;HOME", 2),
       #"Home Street 2"		: None,
       #"Home Street 3"		: None,
       "Home City"		: ("ADR;HOME", 3),
       "Home State"		: ("ADR;HOME", 4),
       "Home Postal Code"	: ("ADR;HOME", 5),
       "Home Country"		: ("ADR;HOME", 6),
       "Other Street"		: ("ADR;DOM", 2),
       #"Other Street 2"	: None,
       #"Other Street 3"	: None,
       "Other City"		: ("ADR;DOM", 3),
       "Other State"		: ("ADR;DOM", 4),
       "Other Postal Code"	: ("ADR;DOM", 5),
       "Other Country"		: ("ADR;DOM", 6),
       "Assistant's Phone"	: None,
       "Business Fax"		: None,
       "Business Phone"		: "TEL:WORK",
       "Business Phone 2"	: None,
       "Callback" 		: None,
       "Car Phone"		: None,
       "Company Main Phone"	: None,
       "Home Fax"		: None,
       "Home Phone"		: "TEL:HOME",
       "Home Phone 2"		: None,
       "ISDN"			: "TEL;ISDN",
       "Mobile Phone"		: "TEL;CELL",
       "Other Fax"		: None,
       "Other Phone"		: None,
       "Pager"			: "TEL;PAGER",
       "Primary Phone"		: "TEL:PREF",
       "Radio Phone"		: None,
       "TTY/TDD Phone"		: None,
       "Telex"			: None,
       "Account"		: None,
       "Anniversary"		: None,
       "Assistant's Name"	: None,
       "Billing Information"	: None,
       "Birthday"		: "BDAY",
       "Categories"		: "CATEGORIES;QUOTED-PRINTABLE",
       "Children"		: None,
       "E-mail Address"		: "EMAIL;INTERNET",
       #"E-mail Display Name"	: None,
       #"E-mail 2 Address"	: None,
       #"E-mail 2 Display Name"	: None,
       #"E-mail 3 Address"	: None,
       #"E-mail 3 Display Name"	: None,
       "Gender"			: None,
       "Government ID Number"	: None,
       "Hobby"			: None,
       "Initials"		: None,
       "Keywords"		: None,
       "Language"		: None,
       "Location"		: None,
       "Mileage"		: None,
       "Notes"			: "NOTE;QUOTED-PRINTABLE",
       "Office Location"	: None,
       "Organizational ID Number"	:None,
       "PO Box"			: ("ADR;POSTAL", 0),
       "Private"		: None,
       "Profession"		: None,
       "Referred By"		: None,
       "Spouse"			: None,
       "User 1"			: None,
       "User 2"			: None,
       "User 3"			: None,
       "User 4"			: None,
       "Web Page" 		: "URL",
    }    
    # Synthesized fields:
    # FN -- full name, use as a primary key for filing.

    def error(self, msg):
        sys.stderr.write("lookout: entry %d: %s\n" % (self.entryindex, msg))

    def translate(self, ofp, existing=""):
        "Dump the entry contents in GnomeCard format (a subset of Vcard)."
        self.addressbook.consolidate("; ")
        self.entryindex = 0
        for entry in self.addressbook.entries:
            self.entryindex = self.entryindex + 1
            # Perform necessary field translations
            if entry.has_key("Department"):
                if not entry.has_key("Company"):
                    self.error("Department field `%s' without Company field" % entry["Department"])
                else:
                    entry["Company"]=entry["Company"]+" ("+entry["Department"]+")"
            # First, build a dictionary of GnomeCard fields and lists
            entrydict = {}
            untranslated = ""
            for (outlook_field, outlook_value) in entry.items():
                if not GnomeCardTranslator.field_map.has_key(outlook_field):
                    continue
                elif outlook_field == "Private" and outlook_value == "False":
                    continue
                gnomecard_field = GnomeCardTranslator.field_map[outlook_field]
                # Can't have multiline fields in this entry format
                outlook_value = string.replace(outlook_value, "\n", "; ")
                if gnomecard_field is None:
                    untranslated = untranslated + ("%s: %s=0A" % (outlook_field, outlook_value))
                    continue
                elif type(gnomecard_field) == type(""):
                    entrydict[gnomecard_field] = [outlook_value]
                else:
                    (name, ind) = gnomecard_field
                    if not entrydict.has_key(name):
                        entrydict[name] = []
                    if len(entrydict[name]) <= ind:
                        padding = ([""] * (ind - len(entrydict[name]) + 1))
                        entrydict[name] = entrydict[name] + padding
                    entrydict[name][ind] = outlook_value
            # Anything we could not translate gets stuffed in the notes field
            if untranslated:
                if not entrydict.has_key("NOTES;QUOTED-PRINTABLE"):
                    entrydict["NOTES;QUOTED-PRINTABLE"] = [untranslated]
                else:
                    entrydict["NOTES;QUOTED-PRINTABLE"] = entrydict["NOTES;QUOTED-PRINTABLE"] + "=0A" + [untranslated]

            # Dictionary is built, now dump it.
            ofp.write("# Translated Outlook entry %d\n" % self.entryindex)
            ofp.write("BEGIN:VCARD\r\n")
            ofp.write("FN:" + self.addressbook.fullname(entry) + "\r\n")
            for (key, value) in entrydict.items():
                ofp.write(key + ":" + string.join(value, ";") + "\r\n")
            ofp.write("END:VCARD\r\n\r\n")

    def skip_to_end(self, ofp):
        "Skip to where the next entry should be appended."
        return ofp.read()

# Register new translation classes here
translators = (KABTranslator, GnomeCardTranslator)

if __name__ == '__main__':

    # Start by computing a translation mode
    translations = []
    (opts, args) = getopt.getopt(sys.argv[1:], "", ["kab", "gc"])
    for (opt, val) in opts:
        for tr in translators:
            if opt == getattr(tr, "triggering_option"):
                translations.append(tr)
    # If user didn't specify a mode, we may be able to deduce one.
    if not translations:
        for tr in translators:
            if os.path.exists(os.path.expanduser(getattr(tr, "indicator_directory"))):
                translations.append(tr)
    # We can only handle one translation if we're filtering
    if len(translations) > 1 and not args:
        sys.stderr.write("lookout: one mode at a time, please!\n")
        raise SystemExit
    elif translations == []:
        sys.stderr.write("lookout: can't run without a translation mode!\n")
        raise SystemExit

    # Filtering is straightforward...
    if not args:
        translations[0](OutlookAddressBook(sys.stdin)).translate(sys.stdout)
    # The file-argument case is slightly less so.
    else:
        try:
            ifp = open(args[0], "r")
        except IOError:
            print "lookout: couldn't open " + args[0]
        data = OutlookAddressBook(ifp)
        for tr in translations:
            export = tr(data)
            where = os.path.expanduser(getattr(tr, "default_location"))
            try:
                ofp = open(where, "r+")
                # We need to have access to the existing file contents
                # in case, as in KAB format, entries have sequence numbers.
                existing = export.skip_to_end(ofp)
            except IOError:
                print "lookout: couldn't open addressbook."
                ofp = open(where, "w")
                existing = ""
            export.translate(ofp, existing)
            ofp.close()

# End
