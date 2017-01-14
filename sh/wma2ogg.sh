#!/bin/bash
# Dump wma to mp3


#set -e
inputfile=$1
#shift
 if [ -f $inputfile ]; then
   rm -f "$inputfile.wav"
   mkfifo "$inputfile.wav"

   # pasarle la data a la fifo
   mplayer -vo null -vc dummy -af resample=44100 -ao pcm:waveheader:file="$inputfile.wav" "$inputfile" &
   
   # especificar el archivo de salida y halar data de la fifo
   dest=`echo "$inputfile" | sed -e 's/wma$/ogg/'`
   oggenc -R 44100 -C 2 -o "$dest" "$inputfile.wav" >/dev/null
   
   # eliminar fifo
   rm -f "$inputfile.wav"
 fi

