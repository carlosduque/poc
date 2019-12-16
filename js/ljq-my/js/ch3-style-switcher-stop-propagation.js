$(document).ready(function() {

    $('#switcher .button').click(function(event) {
        $('body').removeClass();
        if (this.id == 'switcher-narrow') {
            $('body').addClass('narrow');
        } 
        else if (this.id == 'switcher-large') {
            $('body').addClass('large');
        }
        $('#switcher .button').removeClass('selected');
        $(this).addClass('selected');
        // Detiene el 'bubbling' que permitiria que el click tambien subiera 
        // desde el .button hasta el #switcher
        event.stopPropagation();
    });

    $('#switcher').click(function(event) {
        // La siguiente línea ya no se necesita para evitar que los botones escondan el 'div'
        // ya que, en la función anterior se detuvo la propagacion del click hacia el 'div'
        //if (event.target == this) {
            $('#switcher .button').toggleClass('hidden');
        //}
    });

    $('#switcher .button').hover(
        function() {
            $(this).addClass('hover');
        },
        function() {
            $(this).removeClass('hover');
    });
});