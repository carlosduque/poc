$(document).ready(function() {
    var $authorCells = $('table.striped td:nth-child(3)');

    var $tooltip = $('<div id="tooltip"></div>').appendTo('body');

    var positionTooltip = function(event) {
        var tPosX = event.pageX;
        var tPosY = event.pageY + 20;
        $tooltip.css({top: tPosY, left: tPosX});
     };

     var showTooltip = function(event) {
        var authorName = $(this).text();
        var action = 'Highlight';
        if ($(this).parent().is('.highlight')) {
            action = 'Unhighlight';
        }
        $tooltip.text(action + ' all articles by ' + authorName).show();
        positionTooltip(event);
     };

    var hideTooltip = function() {
        $tooltip.hide();
     };
    
    $authorCells
        .addClass('clickable')
        .hover(showTooltip, hideTooltip)
        .mousemove(positionTooltip)
        .click(function(event) {
            var authorName = $(this).text();
            $authorCells.each(function(index) {
                if (authorName == $(this).text()) {
                    $(this).parent().toggleClass('highlight');
                }
                else {
                    $(this).parent().removeClass('highlight');
                }
            });
            //By using .call() we invoke the function as if it were running from
            //within the scope of the click handler of the cell with the author's name.
            //This is needed so 'this' refers to the correct object (this table cell)
            showTooltip.call(this, event);
        });
     
    $('table.striped tbody').each(function() {
        $(this).find('tr:not(:has(th))').addClass('even')
            .filter(function(index) {
                return (index % 6) < 3;
            }).removeClass('even').addClass('odd');
    });
});

