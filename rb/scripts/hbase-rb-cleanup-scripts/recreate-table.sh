#!/bin/sh

HBASE_CMD_BASE=/usr/bin

echo "Recreating the table on HBase"
$HBASE_CMD_BASE/hbase shell recycle.rb

