#!/bin/sh
#
#
# Script that changes "Photo album created with <a href="http://www.ornj.net/">Web Album Generator</a>"
# to
# "<a href="http://piotr.eldora.pl">(c)Piotr Zaniewicz</a> - photo album created with <a href="http://www.ornj.net/">Web Album Generator</a>"
# in all files ending with html.



for F in `find ./ -type f -name '*.html' -print`;
do mv $F $F.tmp;
sed 's/Photo album created with <a href=\"http:\/\/www.ornj.net\/\">Web Album Generator<\/a>/<a href=\"http:\/\/piotr.eldora.pl\/\">(c)Piotr Zaniewicz<\/a> - photo album created with <a href=\"http:\/\/www.ornj.net\/\">Web Album Generator<\/a>/g' $F.tmp > $F;
rm $F.tmp;
done

# http://piotr.eldora.pl
