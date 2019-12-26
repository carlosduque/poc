$(document).ready(function() {
    // Plugin para alternateRowColors
    jQuery.fn.alternateRowColors = function() {
        $('tbody tr:odd', this).removeClass('even').addClass('odd');
        $('tbody tr:even', this).removeClass('odd').addClass('even');
        return this;
    };
    
    // sort
    $('table.sortable').each(function() {
        var $table = $(this);
        $table.alternateRowColors();
        $('th', $table).each(function(column) {
            var $header = $(this);

            if ($header.is('.sort-alpha')) {
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
                        row.sortKey = $(row).children('td').eq(column).text().toUpperCase();
                    });
                    
                    rows.sort(function(a, b) {
                        console.log('|column=' + column + '|a.sortKey=' + a.sortKey + '|b.sortKey=' + b.sortKey);
                        if (a.sortKey < b.sortKey) return -1;
                        if (a.sortKey > b.sortKey) return 1;
                        return 0;
                    });
                    $.each(rows, function(index, row) {
                        $table.children('tbody').append(row);
                        row.sortKey = null;
                    });
                    $table.alternateRowColors();
                });
            }
        });
    });
});

