require 'net/http'
require 'uri'
require 'json'

core = "thing1"
url = URI.parse("http://localhost:8983/solr/#{core}/update/json?commit=true")
http = Net::HTTP.new(url.host, url.port)
request = Net::HTTP::Post.new(url.request_uri)
request.content_type = "application/json"
#params = {"Content-type" => 'application/json'}

puts "======================================================"
puts " Deleting data..."
puts "======================================================"
puts

payload = {
  "delete" => {"query" => "*:*"},
}
puts payload.to_json

request.body = payload.to_json

response = http.request(request)

puts response.code
puts "----------------------------------"
puts response.body

