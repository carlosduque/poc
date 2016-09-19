#!/bin/bash
#Create an address book from every message in your sent mail folder
grep "^To: " mail/sent-mail | grep "@" | \
sed 's,.* <*\([^ ]*@[^ >]*\).*,\1,’ | \
sort | uniq -i | sed ’s/,.*$//’ > correspondents.txt
