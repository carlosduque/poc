require 'net/http'
require 'uri'
require 'json'

core = "thing1"
url = URI.parse("http://localhost:8983/solr/#{core}/update/json?commit=true")
http = Net::HTTP.new(url.host, url.port)
params = {"Content-type" => 'application/json'}
puts "======================================================"
puts " Loading data..."
puts "======================================================"
puts

maxdate = Time.new
mindate = Time.utc(maxdate.year, maxdate.month, 1, 0, 0, 0)
date_range = mindate...maxdate

500.times do |i|
  date_gen = Random.new.rand(date_range)
  json_str = "{'add': {'doc': {'record.key': '#{i}', 'inquiry_dt_dt_gi':'#{date_gen}'}}}"
  puts json_str
  
  response, body = http.post(url.path, json_str, params)
  
  puts response
end


