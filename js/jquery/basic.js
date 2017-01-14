$(function() {
  var name = $('#name');
  var greeting = $('#greeting');

  name.keyup(function() {
    greeting.text('Hola ' + name.val() + '!');
  })
})
