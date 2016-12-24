#!/usr/bin/python

import sys

with open(sys.argv[1]) as file:
    for line in file:
        print "%s" % (line)

print "read :: %s ::" % (sys.argv[1])
