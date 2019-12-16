$(document).ready(function() {
    /*
     * Se utiliza el id de 'this' para determinar el objeto que recibió el click
     * y asignarle la clase correspondiente
     */

    $('#switcher .button').click(function() {
        //Sin un nombre de metodo como parametro, quita TODAS las clases del elemento
        $('body').removeClass();
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
