#!/usr/bin/python
# -*- coding: utf-8 -*-

from subprocess import call
import logging
import os
import sys
import time

"""
Inicializa el ambiente para un proyecto de SBE.
El usuario puede determinar el directorio donde residira el proyecto y desde donde sera creado, sin embargo,
la mejor forma de hacerlo es definiendo los proyectos con sus origenes y destino dentro de este archivo.
Requerimientos:
    * Se necesita que ya este instalado el subversion, especificamente, que se puedan correr los comandos:
        - svn update
        - svn export
    * Se asume que los proyectos a utilizar son propios de un IDE basado en eclipse, los directorios que son un
    proyecto tienen un archivo llamado '.project' dentro de ellos.

Este programa:
    * Hace un 'svn update' de todos los directorios definidos como 'origen' en CONFIGS
    * Hace un 'svn export' de todos los directorios que son considerados 'proyecto'
    hacia el directorio definido como 'destino' en CONFIGS
"""
# *************************************************
# Configuracion de la aplicacion 
# *************************************************
# En esta variable defina cada uno de los proyectos en los que esta trabajando
# y de los cuales desea generar un ambiente de desarrollo.
# 
# Utilice la estructura como sigue:
# <CODIGO>: [
#               (<DESTINO>, 
#                   [
#                       <ORIGEN 1>,
#                       <ORIGEN 2>,
#                       <ORIGEN n>,
#                   ]
#               ),
#           ],
# Donde:
#       - <CODIGO>  : Identificador del proyecto
#       - <DESTINO> : Directorio donde residira el ambiente de desarrollo
#       - <ORIGEN>  : Uno o varios directorios bajo el control de versiones de subversion y que contiene
#                     proyectos de eclipse.
#
# Ejemplo:
#CONFIGS = {
#            'pry1': [
#                    ('d:/dir/destino1pry1/', 
#                        [
#                            'd:/dir/origen1/',
#                            'd:/dir/origen2/',
#                            'd:/dir/origen3/',
#                        ]
#                    ),
#            ],
#            'pry2': [
#                    ('d:/dir/destino1pry2/', 
#                        [
#                            'd:/dir/origen4/',
#                        ]
#                    ),
#                    ('d:/dir/destino2pry2/', 
#                        [
#                            'd:/dir/origen1/',
#                            'd:/dir/oriten4/',
#                        ]
#                    ),
#            ],
#}

CONFIGS = {
            'hon-01145': [
                    ('d:/proyectos/workspace/hon-01145/', 
                        [
                            'd:/proyectos/src/trnk-sec3/',
                            'd:/proyectos/src/trnk-paghn/',
                            'd:/proyectos/src/trnk-pagmq/',
                        ]
                    ),
            ],
            'hon-01113': [
                    ('d:/proyectos/rsa/brnc-hon01113/', 
                        [
                            'd:/proyectos/src/brnc-HON-01113-sec/',
                        ]
                    ),
                    ('d:/proyectos/workspace/brnc-hon01113/', 
                        [
                            'd:/proyectos/src/brnc-HON-01113-pag/',
                        ]
                    ),
            ],
            'hon-01175': [
                    ('d:/proyectos/rsa/brnc-hon01175/', 
                        [
                            'd:/proyectos/src/trnk-sec3/',
                        ]
                    ),
            ],
}

# *************************************************
# Programa principal
# *************************************************

__author__ = 'Carlos Duque'
__version__ = '0.1'
__homepage__ = 'http://www.duque-murillo.com'
__date__ = '2009.11.26'

class SVNDir:
    """
    Clase que representa un directorio donde es posible trabajar con subversion.
    """
    # Definicion de comandos
    CMD_SVN_UPDATE="svn update --quiet --non-interactive --force"
    CMD_SVN_EXPORT="svn export --quiet --non-interactive --force"
    # Definicion de archivos
    SVN_PROJECT_FILE=".project"
    SVN_DATA_FOLDER=".svn"
    # Definicion de mensajes
    MSG_UPDATING_DIR = "Actualizando %s ..."
    MSG_EXPORTING_DIR = "Exportando %s --> %s"
    # Definicion de caracteres
    BLANK = " "
    DOUBLE_QUOTE = "\""
    NEXT_LINE = "\n"
    # Definicion de errores
    ERR_CODE_SVN_UPDATE = 9100
    ERR_CODE_SVN_EXPORT = 9200
    ERR_PGM_EXECUTION_FAILED = "Fallo la ejecucion "
    ERR_PGM_PROCESSING_DIR = "Error %i procesando la ruta %s "

    def __init__(self, _path):
        self.path = _path

    def update(self):
        """
        Actualiza el directorio ejecutando un 'svn update'
        """
        try:
            #log.info(SVNDir.MSG_UPDATING_DIR % (self.path))
            print(SVNDir.MSG_UPDATING_DIR % (self.path))
            retcode = call(SVNDir.CMD_SVN_UPDATE + SVNDir.BLANK + self.path, shell=False)
            return(SVNDir.ERR_CODE_SVN_UPDATE + retcode)
        except OSError, e:
            print >>sys.stderr, SVNDir.ERR_PGM_EXECUTION_FAILED, e
            #log.error(SVNDir.ERR_PGM_EXECUTION_FAILED + SVNDir.BLANK + e)
    # /update

    def getProjectDirs(self):
        """
        Busca desde la ruta definida y en direccion hacia abajo (interna) los directorios que son proyectos.
        Para que un directorio sea considerado 'proyecto' debe existir un archivo .project
        """
        prjDirs = []
        try:
            for root, dirs, files in os.walk(self.path):
                # no pase por los .svn subdirs
                if SVNDir.SVN_DATA_FOLDER in dirs:
                    dirs.remove(SVNDir.SVN_DATA_FOLDER) 
                for f in files:
                    if f == SVNDir.SVN_PROJECT_FILE:
                        current = os.path.abspath(root)
                        svnDir = SVNDir(current)
                        prjDirs.append(svnDir)
        except Exception, e: 
            raise ScriptError(str(e))
        return(prjDirs)
    # /getProjectDirs

    def export(self, _targetDir):
        """
        Exporta los directorios de proyectos al directorio especificado por _targetDir.
        """
        try:
            head, tail = os.path.split(self.path)
            print SVNDir.MSG_EXPORTING_DIR % (self.path, _targetDir + tail)
            retcode = call(SVNDir.CMD_SVN_EXPORT + SVNDir.BLANK + SVNDir.DOUBLE_QUOTE + self.path + SVNDir.DOUBLE_QUOTE + SVNDir.BLANK + SVNDir.DOUBLE_QUOTE + _targetDir + tail + SVNDir.DOUBLE_QUOTE, shell=False)
            return(SVNDir.ERR_CODE_SVN_EXPORT + retcode)
        except OSError, e:
            print >>sys.stderr, SVNDir.ERR_PGM_EXECUTION_FAILED, e
            #log.error(SVNDir.ERR_PGM_EXECUTION_FAILED + SVNDir.BLANK + e)
    # /export
# /SVNDir

class ColorFormatter(logging.Formatter):
    """
    Clase para darle colores a los distintos niveles de logging.
    """
    def color(self, level=None):
        codes = {
            None:       (0,   0),
            'DEBUG':    (0,   2), # gris
            'INFO':     (0,   0), # normal
            'WARNING':  (1,  34), # azul
            'ERROR':    (1,  31), # rojo
            'CRITICAL': (1, 101), # negro, fondo rojo
            }
        return (chr(27)+'[%d;%dm') % codes[level]
    # /color

    def format(self, record):
        retval = logging.Formatter.format(self, record)
        return self.color(record.levelname) + retval + self.color()
    # /format
# /ColorFormatter

class ScriptError(Exception):
    pass
# /ScriptError

def _makeDir(_dir):
    """
    Crea el directorio _dir.
    """
    # Crea el directorio destino si este no existe
    try:
        # se usa normpath para asegurar que los separadores son uniformes
        targetDir = os.path.normpath(_dir)
        fixpath = (targetDir[-1:] != os.sep) and (lambda s: s + os.sep) or (lambda s: s)
        targetDir = fixpath(targetDir)
        if os.path.isdir(targetDir):
            pass
        else:
            os.makedirs(targetDir)
            #log.info("Se creo con exito el directorio: '%s'" % (targetDir))
            print("Se creo con exito el directorio: '%s'" % (targetDir))
    except OSError, e:
        print >>sys.stderr, e
        #log.error(e)
        sys.exit(0)
# /_makeDir

def process(_dirs, _target):
    """
    Recibe una lista de objetos SVNDir y realiza las siguientes acciones para cada uno de ellos:
    * Hace un 'svn update' de la ruta definida en el objeto 
    * Busca en esa ruta, todos los directorios identificados como proyecto.
    * Para cada proyecto encontrado, se realiza un 'svn export' hacia el targetDir especificado

    Si existen errores, se devuelve el codigo de de error y la ruta que se estaba procesando en ese momento.
    """
    errlist = []
    for dir in _dirs:
        # Actualiza el directorio
        retcode = dir.update()
        if retcode != SVNDir.ERR_CODE_SVN_UPDATE: 
            errlist.append((retcode, dir.path))
        # Exportar los directorios que son proyectos al targetDir
        for projectDir in dir.getProjectDirs():
            retcode = projectDir.export(_target)
            if retcode != SVNDir.ERR_CODE_SVN_EXPORT:
                errlist.append((retcode, projectDir.path))

    return(errlist)
# /process

def main(argv):
    """
    Ejecuta el programa si es llamado desde la linea de comandos.
    """
    # Para medir el tiempo de ejecucion
    start = time.time()

    # Importacion tardia, por si este modulo se vuelve una libreria
    from optparse import OptionParser

    usage = "%prog --code hon-01113 \
            \n   %prog --source C:/dir/origen/ --target D:/dir/destino/ \
            \n   %prog --list-config "

    parser = OptionParser(usage=usage, version="%prog 2.0")
    parser.add_option("-s", "--source", dest="sourceDir", action="store", type="string", metavar="SRCDIR", help="Directorio donde se encuentran los fuentes descargados de subversion.")
    parser.add_option("-t", "--target", dest="targetDir", action="store", type="string", metavar="DSTDIR", default="C:/temp/", help="Directorio donde sera creado el proyecto. [Por omision: %default]")
    parser.add_option("-c", "--code", dest="projectCode", action="store", type="string", help="Crea el proyecto segun el codigo de la configuracion especificada, estas son definidas por el usuario en la variable interna 'CONFIGS' que puede editar manualmente modificando este programa con un editor de texto.")
    parser.add_option("-l", "--list-config", dest="listConfigs", action="store_true", default=False, help="Listar los codigos de proyectos disponibles.\n Estas configuraciones pueden ser definidas internamente modificando este programa.")
    (options, args) = parser.parse_args()

    log = logging.getLogger()
    # Establecer ColorFormatter para loguear a colores segun el nivel
    #console.setFormatter(ColorFormatter('   s[(name)s]: %(message)s'))
    console = logging.StreamHandler()
    console.setLevel(logging.DEBUG)
    formatter = logging.Formatter("%(asctime)s - %(name)s - %(levelname)s - %(message)s")
    console.setFormatter(formatter)
    log.addHandler(console)
    
    # Recolecta los errores lanzados al interactuar con el sistema de archivos
    errors = []

    if options.listConfigs:
        print("Lista de codigos disponibles:")
        print(SVNDir.NEXT_LINE.join([config for config in CONFIGS.iterkeys()]))
    elif options.projectCode:
        print("Procesando el codigo: %s, los otros argumentos seran ignorados." % (options.projectCode))
        if CONFIGS.has_key(options.projectCode):
            definitions = CONFIGS[options.projectCode]
            for definition in definitions:
                targetDir, sources = definition
                _makeDir(targetDir)
                svnDirs = []
                for sourceDir in sources: 
                    # Crea un SVNDir para cada sourceDir de la definicion y lo agrega a una lista
                    svnDir = SVNDir(sourceDir)
                    svnDirs.append(svnDir)

                errors = process(svnDirs, targetDir)
        else:
		    parser.error("El codigo de proyecto '%s' no existe" % (options.projectCode))
    else:
        if options.sourceDir:
            print "Procesando SRCDIR: %s DSTDIR: %s" % (options.sourceDir, options.targetDir)
            _makeDir(options.targetDir)
            svnDirs = []
            svnDir = SVNDir(options.sourceDir)
            svnDirs.append(svnDir)
            errors = process(svnDirs, options.targetDir)
        else:
            parser.error("Debe especificar SRCDIR.")

    # informar si hubieron errores
    if len(errors) > 0:
        #print SVNDir.NEXT_LINE.join([SVNDir.ERR_PGM_PROCESSING_DIR % (retcode, path) for retcode, path in errors])
        log.error(SVNDir.NEXT_LINE.join([SVNDir.ERR_PGM_PROCESSING_DIR % (retcode, path) for retcode, path in errors]))

    # Imprimir el tiempo de ejecucion
    log.info("Tiempo de ejecucion(seg): ", (time.time() - start)) 

# /main

if __name__ == '__main__':
    main(sys.argv)

