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
    
    //Para mover los footnotes (elementos DOM) justo antes del #footer
    //$('span.footnote').insertBefore('#footer');
    
    //***************************************
    //******** Otra versión de footnotes ****
    //***************************************
    
    //setup a container for the footnotes
    $('<ol id="notes"></ol>').insertAfter('div.chapter');
    
    //Mark, number and link the context
    $('span.footnote').each(function(index) {
        $(this)
            .before(
                ['<a href="#foot-note-',
                   index+1,
                   '" id="context-',
                   index+1,
                   '" class="context">',
                   '<sup>' + (index+1) + '</sup>',
                   '</a>'
                ].join('')
            )
            //Agregar al elemento #notes
            .appendTo('#notes')
            //el link de nombre 'context' que lleve al parrafo correspondiente
            .append('&nbsp;(<a href="#context-' + (index+1) + '">context</a>)')
            // envolverlo en elementos li
            .wrap('<li id="foot-note-' + (index+1) + '"></li>');
    });
    
});

