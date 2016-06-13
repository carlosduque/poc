require 'sequel'

ENV["RACK_ENV"] ||= "development"

DB = Sequel.connect "sqlite://db/#{ENV["RACK_ENV"]}.sqlite3"

DB.create_table :info do
  primary_key :id
  String :description
end
