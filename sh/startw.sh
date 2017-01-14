#!/bin/bash
wpa_supplicant -Bw -Dwext -iwlan0 -c/etc/wpa_supplicant.conf
iwconfig wlan0 essid duquecnxw
dhclient wlan0
