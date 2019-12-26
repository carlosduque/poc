#! /usr/bin/python

import sys
import os
#get list of goodfiles and strip them
goodfiles = open(sys.argv[1], 'r').readlines()
for i in range(len(goodfiles)):
	goodfiles[i] = goodfiles[i].strip()

#get list of all files in dir and strip them
allfiles = os.listdir(os.getcwd())
for i in range(len(allfiles)):
	allfiles[i] = allfiles[i].strip()

for file in allfiles:
	if file == sys.argv[0] or sys.argv[1]:
		continue
	else:
		if goodfiles.count(file) != 1:
			os.remove(file)


