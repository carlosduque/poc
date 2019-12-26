$(document).ready(function() {
    $('div.chapter p:eq(0)').clone().insertBefore('div.chapter');    
    
    $('span.pull-quote').each(function(index) {
        var $parentParagraph = $(this).parent('p');
        $parentParagraph.css('position', 'relative');
        var $clonedCopy = $(this).clone();
        $clonedCopy
            .addClass('pulled')
            .find('span.drop')
                .html('&hellip;')
            .end()
            .prependTo($parentParagraph)
            .wrap('<div class="pulled-wrapper"></div>');
        var clonedText = $clonedCopy.text();
        $clonedCopy.html(clonedText);
    });
});

