require 'bundler'
Bundler.require

require 'sass/plugin/rack'

require './app'
#require './lib/info'

Sass::Plugin.options[:style] = :compressed

use Sass::Plugin::Rack

run App
