#!/usr/bin/python

from optparse import OptionParser
from os.path import normpath,dirname,exists,abspath
import os
import shutil
import sys
import time

"""
Recolecta archivos desde una lista empezando desde la ruta especificada y
los copia a una ruta destino manteniendo su estructura original.
--
Fetches files from a list starting at the specified path and 
copies them to a target directory maintaining the original directory structure.

"""

__author__ = 'Carlos Duque'
__version__ = '3.0'
__homepage__ = 'http://www.duque-murillo.com'
__date__ = '2009.05.28'

def fetch(_fileList, _srcDir, _targetDir):
    
    """
    start = time.time()
    """
    
    handler = openFile(_fileList)
    lines = handler.readlines()
    handler.close()
    
    hit = 0
    for item in lines:
        item = (item).strip()
        target = abspath(os.path.join(_srcDir, item))
        if os.path.isfile(target):
            hit += 1
            newPath = abspath(os.path.join(_targetDir, (item).strip()))                    
            if not exists(dirname(newPath)): 
                os.makedirs(dirname(newPath))
            print "File[%s]: %s \n   >[%s]: %s " % (hit, target, hit, newPath)            
            
            try:
                shutil.copyfile(target, newPath)
            except (IOError, os.error), why:
                print "Can't copy %s to %s: %s" % (file, newPath, str(why))
        else:
            print ">>>> %s does not exist." % item
                                
    
    print "Search Finished: %d files copied..." % hit    
    """
    print "elapsed:", (time.time() - start)
    """      
# /fetch

def openFile(_f):
    """
    Open's a file and returns it's handler.
    
    Keyword arguments:
    _f -- the file containing the list.
    """
    try:
        fsock = open(_f)
    except IOError:
        print "The file doesn't exist"
    return fsock
# /openFile

def main(argv):

    """
    Start the fetching program.
    """
    usage = "usage: python %s -f lista.txt -s /carpeta/origen/ -t /carpeta/destino/" % argv[0]
    parser = OptionParser(usage=usage)
    parser.add_option("-f", "--file", dest="inputFile", action="store", type="string", default="lista.txt", help="List of archives to look for.")
    parser.add_option("-s", "--source", dest="srcDir", action="store", type="string", default="d:/pry/", help="Directory where the search will be done.")
    parser.add_option("-t", "--target", dest="targetDir", action="store", type="string", default="d:/tmp/out/", help="Directory where the files will be copied.")
    (options, args) = parser.parse_args()
    
    fetch(options.inputFile, options.srcDir, options.targetDir)
# /main

if __name__ == '__main__':
    main(sys.argv)
