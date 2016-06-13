Raddit::Application.routes.draw do
  devise_for :users
  resources :links
  root "links#index"
end
