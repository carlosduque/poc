# HBase uses jruby for it's shell so this is a 
# Ruby script to drop and create the datasource table
# that should be fed to hbase' shell.

if $0 == __FILE__
  puts
  puts "Usage: $HBASE_HOME/bin/hbase shell #{$0}"
  puts "OR just use the bash script: recreate-table.sh"
  puts
else
  print "** Running a major compact on the table 'datasource': "
  major_compact 'datasource'

  print "** Disabling it: "
  disable 'datasource'

  print "** Dropping it: "
  drop 'datasource'

  print "** Creating the table again... "
  create 'datasource', {NAME => 'record'}

  puts "Done !!!"
  exit
end

