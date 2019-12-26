#!/usr/bin/python
# -*- coding: utf-8 -*-

from optparse import OptionParser
from subprocess import call
import os
import sys
import time

def f(_td):
    """
    Inicializar el constructor de proyectos.
    """

    # Crea el directorio destino si este no existe
    # se usa normpath para asegurar que los separadores son uniformes
    print "_td:" + _td
    targetDir = os.path.normpath(_td)
    print "targetDir: " + targetDir
    #path = (targetDir[-1:] != os.sep) and (lambda s: s + os.sep) or (lambda s: s)
    fixpath = (targetDir[-1:] != os.sep) and (lambda s: s + os.sep) or (lambda s: s)
    print fixpath(targetDir)
    #if (targetDir[-1:] != os.sep):
    #    print "(lambda path: path + os.sep)(targetDir)"
    #    print (lambda path: path + os.sep)(targetDir)
    #else:
    #    print "(lambda path: path)"
    #    print (lambda path: path)

if __name__ == '__main__':
    f(sys.argv)
