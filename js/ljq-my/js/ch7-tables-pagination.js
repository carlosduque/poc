// Plugin para alternateRowColors
jQuery.fn.alternateRowColors = function() {
    $('tbody tr:odd', this).removeClass('even').addClass('odd');
    $('tbody tr:even', this).removeClass('odd').addClass('even');
    return this;
};

$(document).ready(function() {
    $('table.sortable').each(function() {
        var $table = $(this);
        $table.alternateRowColors();
        $('th', $table).each(function(column) {
            var $header = $(this);
            var findSortKey;
            if ($header.is('.sort-alpha')) {
                findSortKey = function($cell) {
                    return $cell.find('.sort-key').text().toUpperCase() + $cell.text().toUpperCase();
                };
            }
            else if ($header.is('.sort-numeric')) {
                findSortKey = function($cell) {
                    var key = $cell.text().replace(/^[^\d.]*/, '');
                    key = parseFloat(key);
                    return isNaN(key) ? 0 : key;
                };
            }
            else if ($header.is('.sort-date')) {
                findSortKey = function($cell) {
                    return Date.parse('1 ' + $cell.text());
                };
            }
            
            if (findSortKey) {
                $header.addClass('clickable').hover(function() {
                    $header.addClass('hover');
                }, function() {
                    $header.removeClass('hover');
                }).click(function() {
                    var sortDirection = 1;
                    if ($header.is('.sorted-asc')) {
                        sortDirection = -1;
                    }
                    //Aunque los objetos jQuery actuan como arrays, no tienen disponibles los metodos
                    // de un array, por eso es necesario el .get() para hacerlo un array de elementos DOM
                    var rows = $table.find('tbody > tr').get();
                    
                    // Sort
                    $.each(rows, function(index, row) {
                        //$(row).data('sortKey', $(row).children('td').eq(column).text().toUpperCase());
                        var $cell = $(row).children('td').eq(column);
                        $(row).data('sortKey', findSortKey($cell));
                    });
                    
                    rows.sort(function(a, b) {
                        console.log('|column=' + column + '|sortDirection=' + sortDirection);
                        if ($(a).data('sortKey') < $(b).data('sortKey')) return -sortDirection;
                        if ($(a).data('sortKey') > $(b).data('sortKey')) return sortDirection;
                        return 0;
                    });

                    $.each(rows, function(index, row) {
                        $table.children('tbody').append(row);
                        $(row).removeData('sortKey');
                    });
                    
                    $table.find('th').removeClass('sorted-asc').removeClass('sorted-desc');
                    if (sortDirection == 1) {
                        $header.addClass('sorted-asc');
                    }
                    else {
                        $header.addClass('sorted-desc');
                    }

                    //Highlight the column recently sorted
                    $table.find('td').removeClass('sorted').filter(':nth-child(' + (column + 1) + ')').addClass('sorted');

                    $table.alternateRowColors();
                    
                    $table.trigger('repaginate');
                });
            }
        });
    });
    
    $('table.paginated').each(function() {
        var currentPage = 0;
        var numPerPage = 7;
        var $table = $(this);
        //var repaginate = function() {
        //        $table.find('tbody tr').hide()
        //                        .slice(currentPage * numPerPage, (currentPage + 1) * numPerPage)
        //                        .show();
        //}
        
        $table.bind('repaginate', function() {
            $table.find('tbody tr').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
        });
        
        $table.trigger('repaginate');
        var numRows = $table.find('tbody tr').length;
        var numPages = Math.ceil(numRows / numPerPage);
        var $pager = $('<div class="pager"></div>');
        for (var page = 0; page < numPages; page++) {
            console.log('|page:' + page + '|currentPage:' + currentPage);
            $('<span class="page-number"></span>').text(page + 1)
                    //Con este codigo, cada vez que page se incremente, tambien afectara
                    //los <span> que ya habian sido creados (.click crea un closure y page esta
                    // fuera del alcance interior)
                    //.click(function() {
                    //    currentPage = page;
                    //    repaginate();
                    //}).appendTo($pager).addClass('clickable');
                    .bind('click', {newPage: page}, function(event) {
                        currentPage = event.data['newPage'];
                        $table.trigger('repaginate');
                        $(this).addClass('active').siblings().removeClass('active');
                    }).appendTo($pager).addClass('clickable');
        }
        $pager.insertBefore($table).find('span.page-number:first').addClass('active');
    });
    
    
});

