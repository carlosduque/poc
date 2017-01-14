#!/usr/bin/env ruby

require 'nokogiri'
require 'rest-client'

def extract_greens(text)
    seals = text.split(",")
    greens = 
end

def process(url = "http://www.uoct.cl/restriccion-vehicular/", plates_to_check = [5])
    begin
        puts "[==>] 'GET' #{url}"
        res = RestClient.get url
        if res.code != 200
            puts "[<==] Received: #{res.code}"
        else
            html = Nokogiri::HTML(res.to_str)
            alert_text = []
            html.css("span").css(".preemergencia").each do |node|
               alert_text << node.inner_html
            end
            alert_text = alert_text[0]
            date = alert_text.split(":")[0]
            all_restricted = extract_greens(alert_text.split(":")[1])
            puts all_restricted 
        end
    rescue => e
        puts "[!!] Couldn't connect:#{e.response}"
    end
end

if __FILE__ == $0
    restricted = []
    if ARGV.length > 2
        #plates_to_check.map(&:to_i)
        plates_to_check = ARGV[1..-1].map { |x| x.to_i }
        restricted = process(ARGV[0], plates_to_check)
    else
        restricted = process()
    end

    puts "[*] From your list, these are restricted: #{restricted}"
end
