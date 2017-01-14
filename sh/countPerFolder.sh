#!/bin/sh
# find			busqueda de archivos
#	-maxdepth 2	que baje hasta dos directorios
#	-mindepth 2	que empiece en el 2 directorio
#	-type d		que busque solo directorios
# xargs			llena un buffer con los archivos listados para procesarlos despues con otro comando
#	-n1		al pasar del buffer que envie los parametros uno por uno
#	-i		para poder utilizar {} en la posicion del siguiente comando donde necesito el parametro
# find
#	{}		aqui cae cada parametro pasado por xargs
#	-name '*.jpg'	que busque solo los archivos .jpg
#	-printf '%h\n'	que solo imprima los directorios y fin de linea, no los nombres de los archivos ya que si los incluyera, no tendria efecto el 'uniq'
# uniq			para que elimine los duplicados
#	-c		imprima el conteo de duplicados   (Tipo Resumen)
# > salida.txt		envie la salida del comando al archivo salida.txt
find . -maxdepth 2 -mindepth 2 -type d | xargs -n1 -i find '{}' -name '*.jpg' -printf '%h\n' | uniq -c > salida.txt
