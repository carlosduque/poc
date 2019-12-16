$(document).ready(function() {
    // ****** Stripes
    //$('table.striped tbody').each(function() {
    //    $(this).find('tr:not(:has(th))').addClass('even')
    //        .filter(function(index) {
    //            return (index % 6) < 3;
    //        }).removeClass('even').addClass('odd');
    //});
    
    $('table.striped').bind('stripe', function() {
        $('tbody', this).each(function() {
        $(this).find('tr:visible:not(:has(th))')
            .removeClass('odd').addClass('even')
            .filter(function(index) {
                return (index % 6) < 3;
            }).removeClass('even').addClass('odd');
        });
    }).trigger('stripe');
});

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
});

$(document).ready(function() {
    // ****** Collapse
    var collapseIcon = '../img/bullet_toggle_minus.png';
    var collapseText = 'Collapse this section';
    var expandIcon = '../img/bullet_toggle_plus.png';
    var expandText = 'Expand this section';
    $('table.collapsible tbody').each(function() {
        var $section = $(this);
        $('<img />').attr('src', collapseIcon).attr('alt', collapseText)
            .prependTo($section.find('th'))
            .addClass('clickable')
            .click(function() {
                if ($section.is('.collapsed')) {
                    $section.removeClass('collapsed')
                        .find('tr:not(:has(th)):not(.filtered)').fadeIn('normal');
                    $(this).attr('src', collapseIcon).attr('alt', collapseText);
                }
                else {
                    $section.addClass('collapsed')
                        .find('tr:not(:has(th))').fadeOut('normal', function() {
                                $(this).css('display', 'none');
                            });
                    $(this).attr('src', expandIcon).attr('alt', expandText);
                }
                $section.parent().trigger('stripe');
            });
    });
});

$(document).ready(function() {
    // ****** Filter
    $('table.filterable').each(function() {
        var $table = $(this);
        $table.find('th').each(function(column) {
            if ($(this).is('.filter-column')) {
                var $filters = $('<div class="filters"></div>');
                $('<h3></h3>')
                    .text('Filter by ' + $(this).text() + ':')
                    .appendTo($filters);

                //all
                $('<div class="filter">all</div>').click(function() {
                    $table.find('tbody tr').removeClass('filtered');
                    $(this).addClass('active').siblings().removeClass('active');
                    $table.trigger('stripe');
                }).addClass('clickable active').appendTo($filters);

                var keywords = {};
                $table.find('td:nth-child(' + (column + 1) + ')')
                    .each(function() {
                        keywords[$(this).text()] = $(this).text();
                    });
                $.each(keywords, function(index, keyword) {
                    $('<div class="filter"></div>').text(keyword)
                        .bind('click', {key: keyword}, function(event) {
                            $('tr:not(:has(th))', $table).each(function() {
                                var value = $('td', this).eq(column).text();
                                if (value == event.data['key']) {
                                    $(this).removeClass('filtered');
                                }
                                else {
                                    $(this).addClass('filtered');
                                }
                            });
                            $(this).addClass('active').siblings().removeClass('active');
                            $table.trigger('stripe');
                        }).addClass('clickable').appendTo($filters);
                });
                $filters.insertBefore($table);
            }
        });
    });
});