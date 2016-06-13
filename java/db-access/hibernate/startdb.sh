#!/usr/bin/env bash

java -cp lib/compile/hsqldb-2.3.2.jar org.hsqldb.Server -database.0 file:db/demodb -dbname.0 demodb

