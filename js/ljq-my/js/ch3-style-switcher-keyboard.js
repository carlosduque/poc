$(document).ready(function() {
    // Enable hover effect on the style switcher buttons.
    $('#switcher .button').hover(
        function() {
            $(this).addClass('hover');
        }, 
        function() {
            $(this).removeClass('hover');
        }
    );

    // Allow the style switcher to expand and collapse.
    var toggleStyleSwitcher = function(event) {
        if (!$(event.target).is('.button')) {
            $('#switcher .button').toggleClass('hidden');
        }
    };
    
    $('#switcher').click(toggleStyleSwitcher);
    
    // Simulate a click so we start in a collaped state.
    $('#switcher').click();
    
    // The setBodyClass() function changes the page style.
    // The style switcher state is also updated.
    var setBodyClass = function(className) {
        $('body').removeClass();
        $('body').addClass(className);
        $('#switcher .button').removeClass('selected');
        $('#switcher-' + className).addClass('selected');
        if (className == 'default') {
            $('#switcher').click(toggleStyleSwitcher);
        }
        else {
            $('#switcher').unbind('click', toggleStyleSwitcher);
            $('#switcher .button').removeClass('hidden');
        }
    };
    
    // Invoke setBodyClass() when a button is clicked.
    $('#switcher').click(function(event) {
        if ($(event.target).is('.button')) {
            if (event.target.id == 'switcher-default') {
                setBodyClass('default');
            }
            if (event.target.id == 'switcher-narrow') {
                setBodyClass('narrow');
            }
            else if (event.target.id == 'switcher-large') {
                setBodyClass('large');
            }
        }
    });
    
    // Invoke setBodyClass() when a key is pressed.
    $(document).keyup(function(event) {
        switch (String.fromCharCode(event.keyCode)) {
            case 'D':
                setBodyClass('default');
                break;
            case 'N':
                setBodyClass('narrow');
                break;
            case 'L':
                setBodyClass('large');
                break;
        }
    });
});