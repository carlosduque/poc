import io.Source

Source.fromFile(args(0)).getLines.foreach{line => println(line)}
println(":: read " + args(0) +" ::")
