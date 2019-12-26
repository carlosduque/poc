$(document).ready(function() {
    /*
     * A cada bot�n le asignamos una 2da funci�n para que se marque como seleccionado.
     * Esta funci�n debe ir antes de las dem�s debido al .removeClass() que 
     * quitar�a cualquier otra clase agregada por las otras funciones.
     * Debido a que el orden de las funciones se respeta, aqu� no hace ning�n da�o.
     */

    $('#switcher .button').bind('click', function() {
        //Sin un nombre de metodo como parametro, quita TODAS las clases del elemento
        $('body').removeClass();
        $('#switcher .button').removeClass('selected');
        //utilizando 'this' (que no es un objeto jQuery, sino, un elemento DOM)
        //que identifica el elemento al que se le lig� el evento 'click'
        $(this).addClass('selected');
    });

    /*
     * A cada bot�n se le asigna una funci�n para que manipule la clase que aplica al body.
     * Excepto al default ya que se har� con un removeClass para cada .button
    */

    $('#switcher-narrow').bind('click', function(){
        //Primero el removeClass y luego el addClass, de lo contrario, se 
        //aplicaria el removeClass al final quitando TODO, incluso lo recien agregado
        $('body').removeClass().addClass('narrow');
    });
    
    $('#switcher-large').bind('click', function(){
        //Aqu� no importa el orden del removeClass('xx') ya que no est� removiendo todo
        //sino, una clase espec�fica
        $('body').addClass('large').removeClass('narrow');
    });

});
