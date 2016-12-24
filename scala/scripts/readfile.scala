#!/usr/bin/env scala

import io.Source

Source.fromFile(args(0)).getLines.foreach{line => println(line)}
println(s":: read ${args(0)} ::")
