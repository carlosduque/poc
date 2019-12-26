$(document).ready(function() {
    $('#switcher').click(function(event) {
        // 'event.target' se convierte en un objeto jQuery y utiliza el método
        // .is() y comprueba que el objeto sea coincidente con el selector definido
        if ($(event.target).is('.button')) {
            $('body').removeClass();
            // 'this' ahora hace referencia al #switcher así que debemos 
            // utilizar event.target para referirnos al boton
            if (event.target.id == 'switcher-narrow') {
                $('body').addClass('narrow');
            }
            else if (event.target.id == 'switcher-large') {
                $('body').addClass('large');
            }
            $('#switcher .button').removeClass('selected');
            $(event.target).addClass('selected');
            //event.stopPropagation(); Ver nota en el handler de visibilidad
        }
    });
    
    $('#switcher').click(function(event) {
        //
        if (!$(event.target).is('.button')) {
            $('#switcher .button').toggleClass('hidden');
        }
    });
    
    $('#switcher-narrow, #switcher-large').click(function() {
        $('#switcher').unbind('click');
    });
    
    $('#switcher .button').hover(
        function() {
            $(this).addClass('hover');
        },
        function() {
            $(this).removeClass('hover');
    });
});