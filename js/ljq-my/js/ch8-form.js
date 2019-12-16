$(document).ready(function() {
    //$('legend').each(function(index) {
    //    $(this).replaceWith('<h3>' + $(this).text() + '</h3>');
    //});
    
    $('input.conditional').next('span').andSelf().hide()
        .end().end()
        .each(function() {
            var $thisInput = $(this);
            var $thisFlag = $thisInput.next('span');
            $thisInput.prev('label').find(':checkbox')
            .attr('checked', false)
            .click(function() {
                if (this.checked) { //$('selector').is(':checked');
                    $thisInput.show().addClass('required');
                    $thisFlag.show();
                    $(this).parent('label').addClass('req-label');
                } else {
                    $thisInput.hide().removeClass('required').blur();
                    $thisFlag.hide();
                    $(this).parent('label').removeClass('req-label');
                }
            });
        });
    
    $('legend').wrapInner('<span></span>');
    
    var requiredFlag = ' * ';
    var conditionalFlag = ' ** ';
    var requiredKey = $('input.required:first')
        .next('span').text();
    var conditionalKey = $('input.conditional:first')
        .next('span').text();
    
    requiredKey = requiredFlag +
        requiredKey.replace(/^\((.+)\)$/,'$1');
    
    conditionalKey = conditionalFlag +
        conditionalKey.replace(/^\((.+)\)$/,'$1');
    
    $('<p></p>')
        .addClass('field-keys')
        .append(requiredKey + '<br />')
        .append(conditionalKey)
        .insertBefore('#contact');
    
    $('form :input')
        .filter('.required')
            .next('span').text(requiredFlag).end()
            .prev('label').addClass('req-label').end()
        .end()
        .filter('.conditional')
            .next('span').text(conditionalFlag);
            
    $('form :input').blur(function() {
        $(this).parents('li:first').removeClass('warning').find('span.error-message').remove();
        if ($(this).hasClass('required')) {
            var $listItem = $(this).parents('li:first');
            if (this.value == '') {
                var errorMessage = 'This is a required field';
                if ($(this).hasClass('conditional')) {
                    errorMessage += ', when its related ' + 'checkbox is checked';
                }
                $('<span></span>')
                    .addClass('error-message')
                    .text(errorMessage)
                    .appendTo($listItem);
                $listItem.addClass('warning');
            }
            if (this.id == 'email') {
                var $listItem = $(this).parents('li:first');
                if ($(this).is(':hidden')) {
                    this.value = ''
                }
                if (this.value != '' &&
                    !/.+@.+\.[a-zA-Z]{2,4}$/.test(this.value)) {
                    var errorMessage = 'Please use proper e-mail format'
                                            + ' (e.g. joe@example.com)';
                    $('<span></span>')
                        .addClass('error-message')
                        .text(errorMessage)
                        .appendTo($listItem);
                    $listItem.addClass('warning');
                }
            }
        }
    });
    
    $('form').submit(function() {
        $('#submit-message').remove();
        $(':input.required').trigger('blur');
        var numWarnings = $('.warning', this).length;
        if (numWarnings) {
            var list = [];
            $('.warning label').each(function() {
                list.push($(this).text());
            });
            $('<div></div>').attr({
                'id':'submit-message',
                'class':'warning'
            })
            .append('Please correct errors with the following ' + numWarnings + ' fields:<br/>')
            .append('&bull; ' + list.join('<br />&bull; '))
            .insertBefore('#send');
            return false;
        }
    });

});