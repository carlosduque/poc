require 'net/http'
require 'uri'
require 'json'

qry = ARGV[0]
raise ArgumentError, "No query was given" unless not qry.nil?
core = "thing1"
#url = "http://localhost:8983/solr/#{core}/select/"  # POST
url = "http://localhost:8983/solr/#{core}/select/"
params = "?wt=json&q=#{qry}"
params_enc = URI.escape(params)
puts "======================================================"
puts "*  Query: #{qry}"
puts "*     To: #{url}"
puts "* Params: #{params}"
puts "*    enc: #{params_enc}"
puts "======================================================"
puts
#response = Net::HTTP.post_form(URI.parse(url), {"wt" => "json", "q" => "#{qry}"}) # POST
response = Net::HTTP.get_response(URI.parse(url+params_enc))

# Pretty-print the json response
puts JSON.pretty_generate(JSON.load(response.body))

