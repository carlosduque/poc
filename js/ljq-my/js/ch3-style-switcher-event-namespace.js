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
    
    var toggleStyleSwitcher = function(event) {
            if (!$(event.target).is('.button')) {
                $('#switcher .button').toggleClass('hidden');
            }
        };

    // click.collapse: asignandole un nombre al evento click
    // toggleStyleSwitcher ya que si usaramos toggleStyleSwitcher() esto causaria
    // que la funcion sea llamada y no referenciada
    $('#switcher').bind('click.collapse', toggleStyleSwitcher);
    
    $('#switcher-narrow, #switcher-large').click(function() {
        $('#switcher').unbind('click.collapse');
    });
    
    $('#switcher-default').click(function() {
        $('#switcher').bind('click.collapse', toggleStyleSwitcher);
    });

    $('#switcher .button').hover(
        function() {
            $(this).addClass('hover');
        },
        function() {
            $(this).removeClass('hover');
    });
    
});