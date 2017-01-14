#!/bin/bash

for i in *.doc 
do
  if [ -f $i ]; then
   dest=`echo "$i" | sed -e 's/doc/txt/'`
   antiword "$i" > "$dest"
  fi
done;
