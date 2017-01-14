#!/bin/sh
# ssh          comando ssh
#  -f          fork to the background
#  -N          run No command
#  -L          local >>   forward-this-port : to-this-server : at-this-remoteport  user@host

ssh -f -N -L 12345:althalen:2222 carlos@althalen
