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
        $('#switcher .button').removeClass('selected');
        //utilizando 'this' (que no es un objeto jQuery, sino, un elemento DOM)
        //que identifica el elemento al que se le ligó el evento 'click'
        $(this).addClass('selected');
    });

    /*
     * A cada botón se le asigna una función para que manipule la clase que aplica al body.
     * Excepto al default ya que se hará con un removeClass para cada .button
    */

    $('#switcher-narrow').bind('click', function(){
        //Primero el removeClass y luego el addClass, de lo contrario, se 
        //aplicaria el removeClass al final quitando TODO, incluso lo recien agregado
        $('body').removeClass().addClass('narrow');
    });
    
    $('#switcher-large').bind('click', function(){
        //Aquí no importa el orden del removeClass('xx') ya que no está removiendo todo
        //sino, una clase específica
        $('body').addClass('large').removeClass('narrow');
    });

});
