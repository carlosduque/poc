#!/usr/bin/env python
# encoding: utf-8
"""
findcache.py
Created by Matt Mayes on 2008-02-18.
Finds the Firefox cache directory when given a home directory
"""

import sys
import os

searchFile = '_CACHE_MAP_'
cacheFolder = None

try:
    home = sys.argv[1]
except IndexError,e:
    print "Please enter your home directory: (no trailing slash) "
    home = raw_input()

for root, dirs, files in os.walk(home):
    for x in files:
        if x == searchFile:
            cacheFolder = root
            print cacheFolder

if cacheFolder == None: print "Cache folder not found under that directory."