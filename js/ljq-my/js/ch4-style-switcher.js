$(document).ready(function() {
    //El nombre de la variable se deja con $, ya que es válido y sirve para identificar que sea
    //un objeto jQuery
    var $speech = $('div.speech');
    var defaultSize = $speech.css('fontSize');
    $('#switcher button').click(function() {
        //parseFloat revisa una cadena de izq a der, hasta que encuentra un caracter
        //no numerico, la cadena resultante es convertida en un número.
        //y el '10' indica la base del número a que se convirtió.
        var num = parseFloat($speech.css('fontSize'), 10);
        console.log('this.id:' + this.id)
        switch (this.id) {
            case 'switcher-large':
                num *= 1.4;
                break;
            case 'switcher-small':
                num /= 1.4;
                break;
            default:
                num = parseFloat(defaultSize, 10);
        }
        $speech.css('fontSize', num + 'px');
    });
});
