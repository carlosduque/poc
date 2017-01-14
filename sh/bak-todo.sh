#!/bin/bash

if [ -z "$1" ]; then 
   echo usage: $0 "PUT|GET"
   exit
fi

# May be needed if run by cron?
export PATH=$PATH:/bin:/usr/bin:/usr/local/bin:/media/data/bin:/media/data/code/scripts/sh
CMD=/usr/bin/curl

DEST="ftp.duque-murillo.com"
DIR="/home/carlos/bin/todo/"
FILE="todo.txt"
USER="util@duque-murillo.com"
PSWD="abra00cadabra"
# --append
OPTSPUT="--progress-bar --upload-file $DIR$FILE --user $USER:$PSWD"
OPTSGET="--progress-bar --output $DIR$FILE --user $USER:$PSWD"

case "$1" in 
    "PUT"   )  VAR=`$CMD $OPTSPUT ftp://$DEST/ > /dev/null; echo $?`;;
    "GET"   )  VAR=`$CMD $OPTSGET ftp://$DEST/$FILE > /dev/null; echo $?`;;
    *       )  VAR="UNKNOWN";;
esac

if [ $VAR -eq 0 ]; then
    echo "`date` :El archivo $FILE se copio con exito"
else
    echo "`date` :No hay comunicacion con $DEST"
fi

