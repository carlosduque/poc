require 'digest/sha1'

raise "I need a number" unless ARGV[0]

number = ARGV[0].to_i
filename = ARGV[1] || "out.txt"

myfile = File.open(filename, "w")
names = %w{aleny bella carlos daniel eda francisco gisselle humberto iris jaime karla luis melissa natalia oscar pablo q rafael silvia teresa ulises vicente wilfredo xavier yuri zacarias}
lastnames = %w{alvarez barahona carrasco diaz esquivel fernandez gomez hernandez iriarte jimenez koenig lagos martinez nolasco ochoa palacios quezada ramirez sanchez toledo ulloa velasquez williams xanco yanez zavala}

puts "Creating names and writing them to #{filename}"
load_id = Time.new.usec
number.times do |num|
  timestamp = Time.new.strftime("%F %T")
  name = names[rand(names.length)]
  lastname = lastnames[rand(lastnames.length)]
  hash = Digest::SHA1.hexdigest name+lastname+timestamp
  myfile.write("#{load_id}|#{num}|#{name}|#{lastname}|#{timestamp}|#{hash}\n")
  #puts "#{num}|#{names[rand(names.length)]}|#{lastnames[rand(lastnames.length)]}"
end

myfile.close
