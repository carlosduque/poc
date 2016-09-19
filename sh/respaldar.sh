#!/bin/sh

CMD=/usr/bin/rsync

# Destination host machine name
DEST="althalen"

# User that rsync will connect as
# Are you sure that you want to run as root, though?
USER="carlos"

# Directory to copy from on the source machine.
BACKDIR="/mnt/data/"

# Directory to copy to on the destination machine.
DESTDIR="/media/data/backup/"

# Log file
LOGFILE="/mnt/data/tmp/respaldo.log"

# excludes file - Contains wildcard patterns of files to exclude.
# i.e., *~, *.bak, etc.  One "pattern" per line.
# You must create this file.
EXCLUDES="/mnt/data/code/scripts/sh/respaldar.excludes"

# Options.
# --human-readable output numbers in a human-readable format
# --archive archive mode; equals -rlptgoD 
# --delete delete from the receiving side the files which are not on the
#          sending side, only works if the whole dir was sent, not like /dir/*
# --verbose increase verbosity
# --compress compress file data during the transfer
# --update skip files that are newer on the receiver
# --quiet decreases the amount of information given during the transfer
# --exclude-from=FILE read exclude patterns from FILE
# --progress show progress during transfer
# --log-file=FILE log what we're doing to the specidifed FILE
# --whole-file copy files whole (w/o delta-xfer algorithm)
# See man rsync for other options.

OPTS="--archive --compress --delete --human-readable --update --quiet --exclude-from=$EXCLUDES
--log-file=$LOGFILE"

# May be needed if run by cron?
export PATH=$PATH:/bin:/usr/bin:/usr/local/bin:/mnt/data/bin:/mnt/data/code/scripts/sh

# Only run rsync if $DEST responds.
VAR=`ping -s 1 -c 1 $DEST > /dev/null; echo $?`
if [ $VAR -eq 0 ]; then
    echo "`date` :Respaldo iniciado." >> $LOGFILE
    echo $CMD $OPTS $BACKDIR $USER@$DEST:$DESTDIR
    $CMD $OPTS $BACKDIR $USER@$DEST:$DESTDIR
    echo "`date` :Respaldo finalizado con exito." >> $LOGFILE
else
    #    echo "No hay comunicacion con $DEST."
    echo "`date` :No hay comunicacion con $DEST." >> $LOGFILE
fi

