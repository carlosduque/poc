$(document).ready(function() {
    /*
     * .toggleClass() Permite acortar la función de toggle cuando solo es
     * de poner o quitar una clase.
     *    $('#switcher h3') hubiera limitado el clic a la palabra 'style switcher'
     * Pero como se dejo en el #switcher aun hacer clic en un boton esconderia todos los botones
     * Para evitar esto, se pasa como parametro el 'event' quien sabe por medio de .target
     * qué elemento recibió el evento primero ó, en los botones:
     *    $('#switcher .button').bind('click', function() {
     * se podría usar: 
     *    event.stopPropagation();
     * Para evitar que algún otro objeto DOM responda al evento.
     */
      $('#switcher h3').click(function() {
           $('#switcher .button').toggleClass('hidden');
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
