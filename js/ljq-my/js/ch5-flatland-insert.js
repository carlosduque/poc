$(document).ready(function() {
    $('div.chapter a[href*=wikipedia]').each(function(index) {
        var $thisLink = $(this);
        $thisLink.attr({
            'rel':'external',
            'id': 'wikilink-' + index,
            'title': 'learn more about ' + $thisLink.text() + ' at Wikipedia'
        });
    });
    
    //alternativa: $('div.chapter p').after('<a href="#top">back to top</a>');    
    $('<a href="#top">back to top</a>').insertAfter('div.chapter p:gt(2)');  //:gt(2) para que solo se agregen los links después del 3er parrafo
    $('<a id="top" name="top"></a>').prependTo('body');
    
});

