require 'bundler'
Bundler.require

require 'sass/plugin/rack'

require './app'
#require './lib/info'

Sass::Plugin.options[:style] = :compressed
Sass::Plugin.options[:css_location] = './public/stylesheets'

use Sass::Plugin::Rack

run App
