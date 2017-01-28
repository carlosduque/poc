class SessionsController < ApplicationController
  def new
  end

  def create
      user = User.find_by(email: params[:session][:email].downcase)
      if user && user.authenticate(params[:session][:password])
          # og the user in and redirect to the user's show page.
      else
          flash.now[:danger] = 'Invalid email/password combination' # flash.now disappears as soon as there is an additional request
          render 'new'
      end
  end

  def destroy
  end
end
