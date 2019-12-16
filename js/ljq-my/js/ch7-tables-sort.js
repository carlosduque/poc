$(document).ready(function() {
    // even/odd rows pero al ordenarlo, pueden quedar pegados ciertos colores
    //$('table.sortable tbody tr:odd').addClass('odd');
    //$('table.sortable tbody tr:even').addClass('even');
    
    var alternateRowColors = function($table) {
        $('tbody tr:odd', $table).removeClass('even').addClass('odd');
        $('tbody tr:even', $table).removeClass('odd').addClass('even');
    };
    
    // sort
    $('table.sortable').each(function() {
        var $table = $(this);
        alternateRowColors($table);
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
                    rows.sort(function(a, b) {
                        var keyA = $(a).children('td').eq(column).text().toUpperCase();
                        var keyB = $(b).children('td').eq(column).text().toUpperCase();
                        console.log('|column=' + column + '|keyA=' + keyA + '|keyB=' + keyB);
                        if (keyA < keyB) return -1;
                        if (keyA > keyB) return 1;
                        return 0;
                    });
                    $.each(rows, function(index, row) {
                        $table.children('tbody').append(row);
                    });
                    alternateRowColors($table);
                });
            }
        });
    });
});

