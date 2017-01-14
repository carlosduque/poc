#!/usr/bin/env python

# find_north.py
#
# find all buses that are traveling northbound of
# Dave's office

office_lat = 41.98062
daves_longitude = -87.668452

from xml.etree.ElementTree import parse
doc = parse('rt22.xml')

for bus in doc.findall('bus'):
    lat = float(bus.findtext('lat'))
    if lat > office_lat:
        busid = bus.findtext('id')
        direction = bus.findtext('d')
        if direction.startswith('North'):
            print busid, lat
