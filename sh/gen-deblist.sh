#!/bin/bash
dpkg --get-selections "*" > /media/data/tmp/mis_paquetes.txt

# dpkg --set-selections < /media/data/tmp/mis_paquetes.txt
# apg-get -u dselect-upgrade

