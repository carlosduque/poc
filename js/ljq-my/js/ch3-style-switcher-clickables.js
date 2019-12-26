$(document).ready(function() {
    /*
     * Haciendo que un elemento se ilumine indicando interacción
     */
    $('#switcher .button').hover(
        function() {
            $(this).addClass('hover');
        },
        function() {
            $(this).removeClass('hover');
    });

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

