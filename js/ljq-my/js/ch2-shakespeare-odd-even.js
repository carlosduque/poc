$(document).ready(function() {
    /*
     * Si hubieran dos tablas, la primera linea de cada una
     * podria tener colores diferentes (dependiendo de la ultima
     * linea de la tabla anterior) si se usara:
     *  $('tr:odd').addClass('alt');
    */
    $('tr:nth-child(even)').addClass('alt');
    $('td:contains(Henry)').addClass('highlight');
});

