$(document).ready(function() {
    $('div.chapter a[href*=wikipedia]').each(function(index) {
        var $thisLink = $(this);
        $thisLink.attr({
            'rel':'external',
            'id': 'wikilink-' + index,
            'title': 'learn more about ' + $thisLink.text() + ' at Wikipedia'
        });
    });
});

