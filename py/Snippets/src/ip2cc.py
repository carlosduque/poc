#!/usr/local/bin/python -O
from __future__ import generators
from bsddb import btopen
from socket import inet_aton, inet_ntoa
import re

__version__ = '0.2'


is_IP = re.compile('^%s$' % '.'.join([r'((1?\d)?\d|2[0-4]\d|25[0-5])']*4)).match

buggy_inet_aton = inet_aton
def inet_aton(ip_str):
    ip_str = ip_str.strip()
    if ip_str=='255.255.255.255':
        return '\xff\xff\xff\xff'
    else:
        return buggy_inet_aton(ip_str)


class DuplicateError(ValueError): pass


class IPRangeDB:

    def __init__(self, filename, mode='r'):
        self.__db = btopen(filename, mode)

    def close(self):
        self.__db.close()
    
    def __locate(self, ip):
        '''Locate the last record for IP less or equal to ip.'''
        db = self.__db
        try:
            first, record = db.set_location(ip)
        except KeyError:
            try:
                first, record = db.last()
            except KeyError:
                raise KeyError(inet_ntoa(ip))
        else:
            if first!=ip:
                first, record = db.previous()
        assert first<=ip
        return first, record

    def __iter__(self):
        try:
            first, record = self.__db.first()
            yield (inet_ntoa(first), inet_ntoa(record[:4])), \
                    self.unpack(record[4:])
            while 1:
                first, record = self.__db.next()
                yield (inet_ntoa(first), inet_ntoa(record[:4])), \
                        self.unpack(record[4:])
        except KeyError:
            pass
    
    def __getitem__(self, ip_str):
        ip = inet_aton(ip_str)
        first, record = self.__locate(ip)
        last = record[:4]
        assert last>=first
        if ip<=last:
            return self.unpack(record[4:])
        else:
            raise KeyError(ip_str)

    def __len__(self):
        return len(self.__db)

    def add(self, first_str, last_str, info, replace=0):
        first = inet_aton(first_str)
        last = inet_aton(last_str)
        while 1:
            try:
                db_first, record = self.__locate(last)
            except KeyError:
                # DB is empty
                break
            else:
                db_last = record[:4]
                if first<=db_last:
                    if replace:
                        del self.__db[db_first]
                        continue
                    else:
                        raise DuplicateError(
                            'Range %s-%s intersects ' % (first_str, last_str) +
                            'with existing entry %s-%s' % 
                                    (inet_ntoa(db_first), inet_ntoa(db_last)))
                break
        self.__db[first] = last+self.pack(info)

    def pack(self, info):
        return info

    def unpack(self, info):
        return info


from urllib import urlopen
import struct


class CountryByIP(IPRangeDB):

    dbClass = IPRangeDB

    # Change it to the closest mirror. Official are:
    #   ftp://ftp.ripe.net/pub/stats/
    #   ftp://ftp.arin.net/pub/stats/
    #   ftp://ftp.apnic.net/pub/stats/
    #   ftp://ftp.lacnic.net/pub/stats/
    url_template = 'ftp://ftp.ripe.net/pub/stats/%s/delegated-%s-latest'
    sources = {}
    for name in ('arin', 'ripencc', 'apnic', 'lacnic'):
        sources[name] = url_template % (name, name)

    def fetch(self):
        for name, source in self.sources.items():
            fp = urlopen(source)
            for line in iter(fp.readline, ''):
                parts = line.strip().split('|')
                if len(parts)==7 and parts[2]=='ipv4' and \
                        parts[6] in ('allocated', 'assigned') and \
                        name==parts[0]:
                    first = parts[3]
                    first_int = struct.unpack('!I', inet_aton(first))[0]
                    last_int = first_int+int(parts[4])-1
                    last = inet_ntoa(struct.pack('!I', last_int))
                    try:
                        self.add(first, last, parts[1].upper())
                    except ValueError:
                        pass

    
# ISO 3166-1 A2 codes (latest change: Wednesday 10 October 2003)
cc2name = {
    'AD': 'ANDORRA',
    'AE': 'UNITED ARAB EMIRATES',
    'AF': 'AFGHANISTAN',
    'AG': 'ANTIGUA AND BARBUDA',
    'AI': 'ANGUILLA',
    'AL': 'ALBANIA',
    'AM': 'ARMENIA',
    'AN': 'NETHERLANDS ANTILLES',
    'AO': 'ANGOLA',
    'AQ': 'ANTARCTICA',
    'AR': 'ARGENTINA',
    'AS': 'AMERICAN SAMOA',
    'AT': 'AUSTRIA',
    'AU': 'AUSTRALIA',
    'AW': 'ARUBA',
    'AZ': 'AZERBAIJAN',
    'BA': 'BOSNIA AND HERZEGOVINA',
    'BB': 'BARBADOS',
    'BD': 'BANGLADESH',
    'BE': 'BELGIUM',
    'BF': 'BURKINA FASO',
    'BG': 'BULGARIA',
    'BH': 'BAHRAIN',
    'BI': 'BURUNDI',
    'BJ': 'BENIN',
    'BM': 'BERMUDA',
    'BN': 'BRUNEI DARUSSALAM',
    'BO': 'BOLIVIA',
    'BR': 'BRAZIL',
    'BS': 'BAHAMAS',
    'BT': 'BHUTAN',
    'BV': 'BOUVET ISLAND',
    'BW': 'BOTSWANA',
    'BY': 'BELARUS',
    'BZ': 'BELIZE',
    'CA': 'CANADA',
    'CC': 'COCOS (KEELING) ISLANDS',
    'CD': 'CONGO, THE DEMOCRATIC REPUBLIC OF THE',
    'CF': 'CENTRAL AFRICAN REPUBLIC',
    'CG': 'CONGO',
    'CH': 'SWITZERLAND',
    'CI': "COTE D'IVOIRE",
    'CK': 'COOK ISLANDS',
    'CL': 'CHILE',
    'CM': 'CAMEROON',
    'CN': 'CHINA',
    'CO': 'COLOMBIA',
    'CR': 'COSTA RICA',
    'CS': 'SERBIA AND MONTENEGRO',
    'CU': 'CUBA',
    'CV': 'CAPE VERDE',
    'CX': 'CHRISTMAS ISLAND',
    'CY': 'CYPRUS',
    'CZ': 'CZECH REPUBLIC',
    'DE': 'GERMANY',
    'DJ': 'DJIBOUTI',
    'DK': 'DENMARK',
    'DM': 'DOMINICA',
    'DO': 'DOMINICAN REPUBLIC',
    'DZ': 'ALGERIA',
    'EC': 'ECUADOR',
    'EE': 'ESTONIA',
    'EG': 'EGYPT',
    'EH': 'WESTERN SAHARA',
    'ER': 'ERITREA',
    'ES': 'SPAIN',
    'ET': 'ETHIOPIA',
    'FI': 'FINLAND',
    'FJ': 'FIJI',
    'FK': 'FALKLAND ISLANDS (MALVINAS)',
    'FM': 'MICRONESIA, FEDERATED STATES OF',
    'FO': 'FAROE ISLANDS',
    'FR': 'FRANCE',
    'GA': 'GABON',
    'GB': 'UNITED KINGDOM',
    'GD': 'GRENADA',
    'GE': 'GEORGIA',
    'GF': 'FRENCH GUIANA',
    'GH': 'GHANA',
    'GI': 'GIBRALTAR',
    'GL': 'GREENLAND',
    'GM': 'GAMBIA',
    'GN': 'GUINEA',
    'GP': 'GUADELOUPE',
    'GQ': 'EQUATORIAL GUINEA',
    'GR': 'GREECE',
    'GS': 'SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS',
    'GT': 'GUATEMALA',
    'GU': 'GUAM',
    'GW': 'GUINEA-BISSAU',
    'GY': 'GUYANA',
    'HK': 'HONG KONG',
    'HM': 'HEARD ISLAND AND MCDONALD ISLANDS',
    'HN': 'HONDURAS',
    'HR': 'CROATIA',
    'HT': 'HAITI',
    'HU': 'HUNGARY',
    'ID': 'INDONESIA',
    'IE': 'IRELAND',
    'IL': 'ISRAEL',
    'IN': 'INDIA',
    'IO': 'BRITISH INDIAN OCEAN TERRITORY',
    'IQ': 'IRAQ',
    'IR': 'IRAN, ISLAMIC REPUBLIC OF',
    'IS': 'ICELAND',
    'IT': 'ITALY',
    'JM': 'JAMAICA',
    'JO': 'JORDAN',
    'JP': 'JAPAN',
    'KE': 'KENYA',
    'KG': 'KYRGYZSTAN',
    'KH': 'CAMBODIA',
    'KI': 'KIRIBATI',
    'KM': 'COMOROS',
    'KN': 'SAINT KITTS AND NEVIS',
    'KP': "KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF",
    'KR': 'KOREA, REPUBLIC OF',
    'KW': 'KUWAIT',
    'KY': 'CAYMAN ISLANDS',
    'KZ': 'KAZAKHSTAN',
    'LA': "LAO PEOPLE'S DEMOCRATIC REPUBLIC",
    'LB': 'LEBANON',
    'LC': 'SAINT LUCIA',
    'LI': 'LIECHTENSTEIN',
    'LK': 'SRI LANKA',
    'LR': 'LIBERIA',
    'LS': 'LESOTHO',
    'LT': 'LITHUANIA',
    'LU': 'LUXEMBOURG',
    'LV': 'LATVIA',
    'LY': 'LIBYAN ARAB JAMAHIRIYA',
    'MA': 'MOROCCO',
    'MC': 'MONACO',
    'MD': 'MOLDOVA, REPUBLIC OF',
    'MG': 'MADAGASCAR',
    'MH': 'MARSHALL ISLANDS',
    'MK': 'MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF',
    'ML': 'MALI',
    'MM': 'MYANMAR',
    'MN': 'MONGOLIA',
    'MO': 'MACAO',
    'MP': 'NORTHERN MARIANA ISLANDS',
    'MQ': 'MARTINIQUE',
    'MR': 'MAURITANIA',
    'MS': 'MONTSERRAT',
    'MT': 'MALTA',
    'MU': 'MAURITIUS',
    'MV': 'MALDIVES',
    'MW': 'MALAWI',
    'MX': 'MEXICO',
    'MY': 'MALAYSIA',
    'MZ': 'MOZAMBIQUE',
    'NA': 'NAMIBIA',
    'NC': 'NEW CALEDONIA',
    'NE': 'NIGER',
    'NF': 'NORFOLK ISLAND',
    'NG': 'NIGERIA',
    'NI': 'NICARAGUA',
    'NL': 'NETHERLANDS',
    'NO': 'NORWAY',
    'NP': 'NEPAL',
    'NR': 'NAURU',
    'NU': 'NIUE',
    'NZ': 'NEW ZEALAND',
    'OM': 'OMAN',
    'PA': 'PANAMA',
    'PE': 'PERU',
    'PF': 'FRENCH POLYNESIA',
    'PG': 'PAPUA NEW GUINEA',
    'PH': 'PHILIPPINES',
    'PK': 'PAKISTAN',
    'PL': 'POLAND',
    'PM': 'SAINT PIERRE AND MIQUELON',
    'PN': 'PITCAIRN',
    'PR': 'PUERTO RICO',
    'PS': 'PALESTINIAN TERRITORY, OCCUPIED',
    'PT': 'PORTUGAL',
    'PW': 'PALAU',
    'PY': 'PARAGUAY',
    'QA': 'QATAR',
    'RE': 'REUNION',
    'RO': 'ROMANIA',
    'RU': 'RUSSIAN FEDERATION',
    'RW': 'RWANDA',
    'SA': 'SAUDI ARABIA',
    'SB': 'SOLOMON ISLANDS',
    'SC': 'SEYCHELLES',
    'SD': 'SUDAN',
    'SE': 'SWEDEN',
    'SG': 'SINGAPORE',
    'SH': 'SAINT HELENA',
    'SI': 'SLOVENIA',
    'SJ': 'SVALBARD AND JAN MAYEN',
    'SK': 'SLOVAKIA',
    'SL': 'SIERRA LEONE',
    'SM': 'SAN MARINO',
    'SN': 'SENEGAL',
    'SO': 'SOMALIA',
    'SR': 'SURINAME',
    'ST': 'SAO TOME AND PRINCIPE',
    'SV': 'EL SALVADOR',
    'SY': 'SYRIAN ARAB REPUBLIC',
    'SZ': 'SWAZILAND',
    'TC': 'TURKS AND CAICOS ISLANDS',
    'TD': 'CHAD',
    'TF': 'FRENCH SOUTHERN TERRITORIES',
    'TG': 'TOGO',
    'TH': 'THAILAND',
    'TJ': 'TAJIKISTAN',
    'TK': 'TOKELAU',
    'TL': 'TIMOR-LESTE',
    'TM': 'TURKMENISTAN',
    'TN': 'TUNISIA',
    'TO': 'TONGA',
    'TR': 'TURKEY',
    'TT': 'TRINIDAD AND TOBAGO',
    'TV': 'TUVALU',
    'TW': 'TAIWAN, PROVINCE OF CHINA',
    'TZ': 'TANZANIA, UNITED REPUBLIC OF',
    'UA': 'UKRAINE',
    'UG': 'UGANDA',
    'UM': 'UNITED STATES MINOR OUTLYING ISLANDS',
    'US': 'UNITED STATES',
    'UY': 'URUGUAY',
    'UZ': 'UZBEKISTAN',
    'VA': 'HOLY SEE (VATICAN CITY STATE)',
    'VC': 'SAINT VINCENT AND THE GRENADINES',
    'VE': 'VENEZUELA',
    'VG': 'VIRGIN ISLANDS, BRITISH',
    'VI': 'VIRGIN ISLANDS, U.S.',
    'VN': 'VIET NAM',
    'VU': 'VANUATU',
    'WF': 'WALLIS AND FUTUNA',
    'WS': 'SAMOA',
    'YE': 'YEMEN',
    'YT': 'MAYOTTE',
    'ZA': 'SOUTH AFRICA',
    'ZM': 'ZAMBIA',
    'ZW': 'ZIMBABWE'
}

# Additional codes used by registrars
cc2name.update({
    'UK': cc2name['GB'],
    'EU': 'EUROPEAN UNION',
    'AP': 'ASSIGNED PORTABLE',
    'YU': 'FORMER YUGOSLAVIA',
})


if __name__=='__main__':
    import sys, os
    db_file = os.path.splitext(sys.argv[0])[0]+'.db'
    if os.environ.get('REQUEST_URI'):
        import cgi
        form = cgi.FieldStorage()
        try:
            addr = form['addr'].value
        except (KeyError, AttributeError):
            addr = ''
        msg = ''

        if addr:
            if not is_IP(addr):
                msg = '%s is not valid IP address' % cgi.escape(addr)
            else:
                db = CountryByIP(db_file)
                try:
                    cc = db[addr]
                except KeyError:
                    msg = 'Information for %s not found' % cgi.escape(addr)
                else:
                    msg = '%s is located in %s' % (cgi.escape(addr),
                                                   cc2name.get(cc, cc))
        script_name = os.environ['SCRIPT_NAME']
        print '''\
Content-Type: text/html

<html>
<head><title>Country By IP</title></head>
<body>
<h1>Country By IP</h1>
<form action="%(script_name)s">
<input type="text" name="addr" value="%(addr)s">
</form>
%(msg)s
</body>
</html>''' % vars()

    elif len(sys.argv)==2:
        if sys.argv[1]=='-update':
            db = CountryByIP(db_file+'.new', 'n')
            db.fetch()
            os.rename(db_file+'.new', db_file)
        elif sys.argv[1]=='-check':
            db = CountryByIP(db_file)
            print 'There are %s records in database' % len(db)
            missing = {}
            for ip_range, cc in db:
                if cc not in cc2name:
                    missing.setdefault(cc, []).append(ip_range)
            if missing:
                for cc, ip_list in missing.items():
                    print 'Missing %s (%s times, sample: %s-%s)' % \
                            (cc, len(ip_list), ip_list[0][0], ip_list[0][1])
            else:
                print 'Contry codes map is OK'
        else:
            addr = sys.argv[1]
            if is_IP(addr):
                ip = addr_str = addr
            else:
                from socket import gethostbyname, gaierror
                try:
                    ip = gethostbyname(addr)
                except gaierror, exc:
                    sys.exit(exc)
                else:
                    addr_str = '%s (%s)' % (addr, ip)
            db = CountryByIP(db_file)
            try:
                cc = db[ip]
            except KeyError:
                sys.exit('Information for %s not found' % addr)
            else:
                print '%s is located in %s' % (addr_str, cc2name.get(cc, cc))
    else:
        sys.exit('Usage:\n\t%s <address>\n\t%s -update' % \
                                (sys.argv[0], sys.argv[0]))
