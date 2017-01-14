require 'sinatra'
require 'haml'
require 'time'

class App < Sinatra::Base

  get '/' do
    haml :index
  end

  get '/fibonacci/:num' do
    @fib = fibonacci((params[:num]).to_i)
    haml :fibonacci
  end

  get '/factorial/:num' do
    @fact = factorial((params[:num]).to_i)
    haml :factorial
  end

  get '/time/current' do
    @time = Time.now
    haml :current_time
  end

  helpers do
      def fibonacci(n)
          if n <= 2
              1
          else
              fibonacci(n - 1) + fibonacci(n - 2)
          end
      end

      def factorial(n)
          if n <= 1
              puts "1"
              return 1
          else
              puts "#{n} + factorial(#{n} - 1)"
              return n * factorial(n - 1)
          end

      end
  end
end
