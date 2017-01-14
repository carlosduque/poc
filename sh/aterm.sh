#!/bin/sh
# Aterm
# bg    set background
# fg    set foreground
# sl    number of lines saved in buffer
# vb    turn off the beep
# sb    get rid of tha scrollbar on the left side
# fade  how much colors fade when losing focus-fade 80
# tr    enable transparency
# sh    shade/amount of transparency
# fn    font for normal text  (xlsfonts lista las disponibles)
# fb    font for bold text
# geometry    
# e     execute the command
/usr/bin/aterm -bg black -fg grey -sl 1000 -vb -sb -fade 80 -tr -sh 15 -fn Terminus -fb Terminus -geometry 160x35 -e screen

