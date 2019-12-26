$(document).ready(function() {

    $('#switcher').click(function(event) {
        if ($(event.target).is('.button')) {
            $('body').removeClass();
            if (event.target.id == 'switcher-narrow') {
                $('body').addClass('narrow');
            }
            else if (event.target.id == 'switcher-large') {
                $('body').addClass('large');
            }
            $('#switcher .button').removeClass('selected');
            $(event.target).addClass('selected');
        }
    });

    var toggleStyleSwitcher = function(event) {
            if (!$(event.target).is('.button')) {
                $('#switcher .button').toggleClass('hidden');
            }
        };

    $('#switcher').bind('click.collapse', toggleStyleSwitcher);

    /*
     * Para arrancar con el styler colapsado y hacerlo
     * simulando un click en el #switcher.
     * Esto debe ir despues de haber definido lo que se hace
     * con el click.
     */
    $('#switcher').trigger('click');
    // igual que: $('#switcher').click();
    
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