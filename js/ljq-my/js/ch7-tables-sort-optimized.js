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
                        console.log('|column=' + column + '|a < b=' + ($(a).data('sortKey') < $(b).data('sortKey')) + '|a:sortKey=' + $(a).data('sortKey') + '|b:sortKey=' + $(b).data('sortKey'));
                        if ($(a).data('sortKey') < $(b).data('sortKey')) return -1;
                        if ($(a).data('sortKey') > $(b).data('sortKey')) return 1;
                        return 0;
                    });
                    $.each(rows, function(index, row) {
                        $table.children('tbody').append(row);
                        $(row).removeData('sortKey');
                    });
                    $table.alternateRowColors();
                    
                    //Highlight the column recently sorted
                    $table.find('td').removeClass('sorted').filter(':nth-child(' + (column + 1) + ')').addClass('sorted');
                });
            }
        });
    });
});

