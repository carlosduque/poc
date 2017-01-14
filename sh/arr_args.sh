#!/usr/bin/env bash

SRC=/home/carlos/Temp

function log() {
   msg=$1
   echo -n "´date´ "
   echo $1
}

function foo() {
   src=$1
   log "src is $src"
}

foo $SRC/*.* 