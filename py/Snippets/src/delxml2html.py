#!/usr/bin/python
# -*- coding: iso-8859-1 -*-
import datetime, time
import xml.dom.minidom
# 
# delxml2html.py  version 1.0.0
# del.icio.us XML export to HTML converter.
#
# This program takes the XML export of your bookmarks
# and creates an HTML page from it.
#
# Instructions:
# 
#   1) Go to http://del.icio.us/api/posts/all
#   2) Enter your del.icio.us login and password
#   3) Save the page as all.xml
#   4) Run this program
#   5) You have your bookmarks in favs.html
#
# Requirements:
#     - a del.icio.us account.
#     - Python 2.4 or later.
#
# License: This program is public domain.
#
# Author: Sébastien SAUVAGE (webmaster of http://sebsauvage.net)
#
print 'Reading all.xml and writing favs.html...'
document = xml.dom.minidom.parse('all.xml')
attrv = document.getElementsByTagName('posts')[0].attributes['update'].value
export_date = attrv.replace('T',' ').replace('Z','')[:10]
posts = {}
# Get all posts, put them in a dictionnary (key = date/time of post) 
for post in document.getElementsByTagName('post'):
    timep = post.attributes['time'].value.replace('T',' ').replace('Z','')
    attributes = {'time':timep}
    for attributename in ('href','description','extended','tag'):
        attributes[attributename] = u""
        try:
            attributes[attributename] = post.attributes[attributename].value
        except KeyError:
            pass  # Value not found. Nevermind.
    # Strip ridiculously long page titles:
    attributes['description'] = attributes['description'][:150]
    posts[timep] = attributes
    
# Take the list of posts (chronological order) and build HTML
htmlbody = u""
for timep in reversed(sorted(posts.keys())):
    htmlbody += (u'<a href="%(href)s"><b>%(description)s</b></a> - <small>'
                +u'%(href)s</small><br><dd>%(extended)s (Tags: %(tag)s)</dd>'
                +u'<br><br>\n') % posts[timep]

htmlout=u'''<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css"><!--body{
font-family:"Trebuchet MS",Verdana,Arial,Helvetica,sans-serif;font-size:10pt;}
--></style> <title>Bookmarks %s</title></head><body><h1>Bookmarks %s</h1><body>
%s
</body>
</html>
''' % (export_date,export_date,htmlbody)

file = open('favs.html','w+b')
file.write(htmlout.encode('UTF-8'))
file.close()

print "Done."
