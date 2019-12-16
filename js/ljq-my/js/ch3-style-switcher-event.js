$(document).ready(function() {
    /*
     * Se pasa 'event' como parametro para poder utilizarlo dentro del bloque
     * pero sigue siendo un objeto del DOM y no un objeto jQuery
     */
    $('#switcher').click(function(event) {
        // Sin esta comparación, al hacer click en algún botón también esconde el div
        if (event.target == this) {
            $('#switcher .button').toggleClass('hidden');
        }
    });

    $('#switcher .button').hover(
        function() {
            $(this).addClass('hover');
        },
        function() {
            $(this).removeClass('hover');
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