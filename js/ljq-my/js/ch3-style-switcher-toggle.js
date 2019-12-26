$(document).ready(function() {
    /* .toggle() Permite definir una funcion para cada momento 'entrada' y 'salida'
     * del elemento.
     */
     $('#switcher h3').toggle(
          function() {
              $('#switcher .button').addClass('hidden');
          }, 
          function() {
              $('#switcher .button').removeClass('hidden');
      });

    $('#switcher .button').click(function() {
        $('body').removeClass();
        if (this.id == 'switcher-narrow') {
            $('body').addClass('narrow');
        } 
        else if (this.id == 'switcher-large') {
            $('body').addClass('large');
        }
        $('#switcher .button').removeClass('selected');
        $(this).addClass('selected');
    });

});

