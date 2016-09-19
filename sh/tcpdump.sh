#!/bin/sh
# tcpdump    el comando
#  -X        Hex y Ascii
#  -S        secuenciar numeros
#  -n        no traduzca ips a nombre
#  -i iface  interface
#  port n    puerto (in/out)
#  and src w.x.y.z direccion a rastrear

tcpdump -X -S -n -i eth0 port 2222 and src 192.168.1.18
