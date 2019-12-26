#!/usr/bin/python

import getopt
import os
import shutil
import sys
import pdb
from os.path import normpath,dirname,exists,abspath

"""
Recolecta archivos desde una lista empezando desde la ruta especificada y
los copia a una ruta destino manteniendo su estructura original.
--
Fetches files from a list starting at the specified path and 
copies them to a target directory maintaining the original directory structure.

"""

__author__ = 'Carlos Duque'
__version__ = '0.1'
__homepage__ = 'http://www.duque-murillo.com'
__date__ = '2008/11/13'

def fetch(_infile, _targetDir, _sourcePath):
    
    fileHandler = openFile(_infile)
    fileList = fileHandler.readlines()
    fileHandler.close()
    
    list = []
    hit = 0
    for listedFile in fileList:  
        for root, dirs, files in os.walk(_sourcePath):
            for file in files:
                if (listedFile).strip() == file:
                    pdb.set_trace()
                    hit += 1                    
                    fullPathFile = abspath(os.path.join(root, file))
                    newPath = fullPathFile
                    newPath = newPath.replace(_sourcePath,_targetDir)                    
                    if not exists(newPath): 
                        os.makedirs(dirname(newPath))                   
                    
                    try:                        
                        shutil.copyfile(fullPathFile, newPath)
                        print "Copy OK: %s  -->  %s " % (fullPathFile, newPath)                        
                    except (IOError, os.error), why:
                        print "Can't copy %s to %s: %s" % (file, newPath, str(why))                    
    
    print "Search Finished: %d files copied..." % hit
     
# /fetch

def openFile(_f):
    """
    Open's a file and returns it's handler.
    
    Keyword arguments:
    f -- the file containing the list.
    """
    try:
        fsock = open(_f)
    except IOError:
        print "The file doesn't exist"
    return fsock
# /openFile

def usage(_pname):
    """
    Displays usage information.
    
    Keyword arguments:
    pname -- program name (i.e. obtained as argv[0])
    
    """
    
    print """python %s [-hp] -f infile -o outdir/ -p srcpath/
    Fetches files from a list starting at the specified path and 
    copies them to a target directory maintaining the original directory structure.
    
    Options:
        -h,--help\tDisplays this message.
        -f,--file\tInput file.        
        -o,--outdir\tSpecify the output directory.
        -p,--path\tStart fetching files from this path.        
        
    Example:
    python %s [-hp] -f ~/lista.txt -o ~/output/directory/ -p /source/path/
        """ % (_pname, _pname)
# /usage
def main(argv):

    """
    Start the fetching program.
    """
    try:
        opts, args = getopt.getopt(
            argv[1:], "hf:o:p:", ["help", "file", "outdir", "path"])
    except getopt.GetoptError, err:
        print str(err)
        usage(argv[0])
        sys.exit(2)
    
    for opt, arg in opts:
        if opt in ("-h", "--help"):
            usage(argv[0])
            sys.exit()
        elif opt in ("-f", "--file"):
            infile = "".join(arg)
        elif opt in ("-o", "--outdir"):
            outdir = "".join(arg)
            if not(outdir.endswith(os.sep)):
                   outdir += os.sep
        elif opt in ("-p", "--path"):
            path = "".join(arg)
            if not(path.endswith(os.sep)):
                   path += os.sep
                   
    if infile == "":
        print "Error: Missing Argument: missing list file."
        usage(argv[0])
        sys.exit(3)
    
    if outdir == "":
        # Use the current directory
        outdir = os.getcwd()
    
    if path == "":
        # Use the current directory
        path = os.getcwd()

    fetch(infile, outdir, path)
# /main

if __name__ == '__main__':
    main(sys.argv)
