#!/usr/bin/python

import csv
import string
import sys
from math import fabs
from re import compile

"""
Convierte estados de cuenta de Credomatic a archivos
de Quicken (QIF) que pueden ser importados por GNUCash.

Este codigo se basa en el trabajo original de Baruch Even (http://baruch.ev-en.org/proj/gnucash.html)
"""

__author__ = 'Carlos Duque'
__version__ = '0.1'
__homepage__ = 'http://www.duque-murillo.com'
__date__ = '2009.01.09'

def cleanrow(_row):
    newrow = []
    for cell in _row:
        ''' quitar '\xa0' de celda '''
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

def data2str(_data, _year):
    s = ''
    month = monthToNumber(_data['Date'])
    day = _data['Date'][4:]            
    _data['Date'] = _year + month + day     
    s = s + _data['Date'] + ',' + _data['Payee'] + ',' + _data['Total'] + ',' + _data['TotalUSD']
    return s

class CSV2QIF_Base:
    ZERO_FLOAT = float(0.00)
    validLine = compile(r"[A-Z]{3}/[0-9]{2}$")

    def __init__(self, _year, _infile):
        self.year = _year
        self.filename = _infile

    def toQIF(self):
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
        
    def toCSV(self):
        reader = csv.reader(file(self.filename))
        writer = file(self.basename() + '_recortado.csv', 'w')        
        for row in reader:
            validData = self.row2valid(cleanrow(row))
            if not validData: 
                continue
            writer.write(data2str(validData, self.year))
            writer.write('\n')
        writer.close()
        
class Tarjeta(CSV2QIF_Base):
    def basename(self):        
        return self.filename[:-4]
    
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
            result = '-' + str(fabs(amount))
        else: 
            result = '+' + str(fabs(amount))

        return {'D': _row[0], 'T': result, 'P': _row[1], 'N': ''}
    
    def row2valid(self, _row):        
        #MMM/DD
        if not CSV2QIF_Base.validLine.match(_row[0]): 
            return None # Skip total line
        else: 
            return {'Date': _row[0], 'Total': _row[2], 'Payee': _row[1], 'TotalUSD': _row[3]}
    
class Banco(CSV2QIF_Base):
    def basename(self):
        return self.filename[:-4]
    
    def qif_header(self):
        return 'No ha sido implementado.'

    def row2qif(self, _row):
        print _row
        return 'No ha sido implementado.'
    
    def row2valid(self, _row):
        print 'No ha sido implementado.'

def usage(_progname):
    """
    Despliega la informacion de uso.
    
    Argumentos clave:
    _progname -- nombre del programa (i.e. obtenido como argv[0])
    
    """
    
    print """python %s [B|T] [qif|csv] archivo.csv
    B    Estado de cuenta de banco
    T    Estado de cuenta de tarjeta de credito
     
    Convierte un estado de cuenta de banco o de tarjeta de credito en formato CSV del BAC 
    a formato de Quicken (QIF).
            
    Ejemplo:
    python %s [B|T] [qif|csv] archivo.csv    
    """ % (_progname, _progname)
# /usage

def main(argv):
    """
    Inicio del programa.
    """
    
    if len(argv) != 4:
        usage(argv[0])
        sys.exit(2)
    
    accountStateType = argv[1]    
    outputType = argv[2]
    inputFile = argv[3]
    year = '2009'
    classes = {'B': Banco, 'T': Tarjeta}
                   
    if inputFile == "" or outputType == "":
        print "Error: Falta argumento."
        usage(argv[0])
        sys.exit(3)

    c = classes[accountStateType]
    instance = c(year,inputFile)
    if outputType == "qif":
        instance.toQIF()
        print 'Archivo convertido a qif'
    elif outputType == "csv":
        instance.toCSV()
        print 'Archivo csv limpio'
    else:
        print 'Error: No se definio el tipo de salida'        
# /main

if __name__ == '__main__':
    main(sys.argv)
