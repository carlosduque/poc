#!/usr/bin/python

import csv
import re 
import string
import sys
import math

"""
Convierte estados de cuenta de tarjeta de credito de Credomatic a archivos
de Quicken (QIF) que pueden ser importados por GNUCash.

El codigo original es creacion de Baruch Even (http://baruch.ev-en.org/proj/gnucash.html)
"""

__author__ = 'Carlos Duque'
__version__ = '0.1'
__homepage__ = 'http://www.duque-murillo.com'
__date__ = '2009.01.09'

def cleanrow(_row):
    newrow = []
    for cell in _row:
        ''' remove '\xa0' from cell '''
        cell = string.strip(cell, '\xa0')
        newrow.append(cell)
    return newrow

def monthToNumber(_month):
    monthDict = {'ENE':'01', 'FEB':'02', 'MAR':'03', 'ABR':'04', 'MAY':'05', 'JUN':'06', 'JUL':'07', 'AGO':'08', 'SEP':'09', 'OCT':'10', 'NOV':'11', 'DIC':'12'}
    return monthDict[_month[:3]]

def qifdata2str(_data, _year):
    s = ''
    for key in _data.keys():        
        if key == 'D':            
            month = monthToNumber(_data[key])
            day = _data[key][4:]            
            _data[key] = month + '/' + day + '/' + _year            
        s = s + key + _data[key] + '\n'
    return s

class CSV2QIF_Base:
    ZERO_FLOAT = float(0.00)
    validLine = re.compile(r"[A-Z]{3}/[0-9]{2}$")

    def __init__(self, _year, _infile):
        self.year = _year
        self.filename = _infile

    def run(self):
        reader = csv.reader(file(self.filename))
        writer = file(self.basename() + '.qif', 'w')
        writer.write(self.qif_header())
        writer.write('\n')
        for row in reader:
            qifdata = self.row2qif(cleanrow(row))
            if not qifdata: 
                continue
            writer.write(qifdata2str(qifdata, self.year))
            writer.write('^\n')
        writer.close()
        
class Tarjeta(CSV2QIF_Base):
    def basename(self):
        return 'ecTarjeta'
    
    def qif_header(self):
        return '!Type:CCard'
    
    def row2qif(self, _row):        
        #MMM/DD
        if not CSV2QIF_Base.validLine.match(_row[0]): 
            return None # Skip total line
        
        amount = float(_row[2])
        if amount <= CSV2QIF_Base.ZERO_FLOAT: 
            return None # Skip total line if amount is 0.00        
        elif (amount > CSV2QIF_Base.ZERO_FLOAT):
            result = '-' + str(math.fabs(amount))
        else: 
            result = '+' + str(math.fabs(amount))

        return {'D': _row[0], 'T': result, 'P': _row[1], 'N': ''}
    
class Banco(CSV2QIF_Base):
    def basename(self):
        return 'ecBanco'
    
    def qif_header(self):
        return '!Type:Bank'

    def row2qif(self, _row):
        if not len(_row[3]): return None
        if not _row[1] and not _row[2]: return None
        if not _row[4]: return None
        if len(trans) == 0: return None
        return {'D': _row[3], 'T': _row[2], 'P': trans, 'N': _row[4]}

def usage(_progname):
    """
    Displays usage information.
    
    Keyword arguments:
    pname -- program name (i.e. obtained as argv[0])
    
    """
    
    print """python %s [B|T] inputfile.csv 
    Converts a bank or creditcard statement from BAC in csv format to a Quicken file (QIF).
        
    Example:
    python %s [B|T] inputfile.csv
    """ % (_progname, _progname)
# /usage

def main(argv):
    """
    Start the program.
    """
    
    if len(argv) != 3:
        usage(argv[0])
        sys.exit(2)
    
    type = argv[1]
    infile = argv[2]
    year = '2009'
    classes = {'B': Banco, 'T': Tarjeta}
                   
    if infile == "":
        print "Error: Missing Argument: missing list file."
        usage(argv[0])
        sys.exit(3)

    c = classes[type]
    instance = c(year,infile)
    instance.run()
    print 'Terminado'    
# /main

if __name__ == '__main__':
    main(sys.argv)
