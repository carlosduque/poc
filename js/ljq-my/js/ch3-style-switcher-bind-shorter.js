$(document).ready(function() {
    /*
     * A cada botón le asignamos una 2da función para que se marque como seleccionado.
     * Esta función debe ir antes de las demás debido al .removeClass() que 
     * quitaría cualquier otra clase agregada por las otras funciones.
     * Debido a que el orden de las funciones se respeta, aquí no hace ningún daño.
     */

    $('#switcher .button').bind('click', function() {
        //Sin un nombre de metodo como parametro, quita TODAS las clases del elemento
        $('body').removeClass();
        //utilizando 'this' (que no es un objeto jQuery, sino, un elemento DOM) para obtener el id.
        if (this.id == 'switcher-narrow') {
            $('body').addClass('narrow');
        } 
        else if (this.id == 'switcher-large') {
            $('body').addClass('large');
        }
        $('#switcher .button').removeClass('selected');
        $(this).addClass('selected');
    });

});
