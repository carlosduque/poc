#!/bin/sh
for F in `find ./ -type f -name '*.html' -print`;
do mv $F $F.tmp;
sed 's/FOO/BAR/g' $F.tmp > $F;
rm $F.tmp;
done;
