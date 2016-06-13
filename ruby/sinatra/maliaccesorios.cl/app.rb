require 'sinatra'
require 'haml'

class App < Sinatra::Base

  get '/' do
    haml :inicio
  end

  get '/acerca_de' do
    haml :acerca_de
  end

  get '/contacto' do
    haml :contacto
  end

  get '/cintillos' do
    haml :cintillos
  end

  get '/colet' do
    haml :colet
  end

  get '/bebe' do
    haml :bebe
  end

  get '/disenios' do
    haml :disenios
  end

  get '/solidos' do
    haml :solidos
  end

  get '/ballet' do
    haml :ballet
  end

  get '/colegiales' do
    haml :colegiales
  end

  get '/princesas' do
    haml :princesas
  end

  get '/estacionales' do
    haml :estacionales
  end

  get '/fiestas_patrias' do
    haml :fiestas_patrias
  end

  get '/navidad' do
    haml :navidad
  end

  get '/pascua' do
    haml :pascua
  end

  get '/san_valentin' do
    haml :san_valentin
  end

end
