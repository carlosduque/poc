#!/bin/bash
# Dump wma to mp3

for i in *.wma
do
 if [ -f $i ]; then
   rm -f "$i.wav"
   mkfifo "$i.wav"
   mplayer -vo null -vc dummy -af resample=44100 -ao pcm:waveheader:file="$i.wav" "$i" &
   dest=`echo "$i" | sed -e 's/wma$/mp3/'`
   lame -h -b 192 "$i.wav" "$dest"
   rm -f "$i.wav"
 fi
done

