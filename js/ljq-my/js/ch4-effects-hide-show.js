$(document).ready(function() {
    $('p:eq(1)').hide();
    $('a.more').click(function() {
        //$('p:eq(1)').show('slow');
        $('p:eq(1)').fadeIn('slow');
        $(this).hide();
        //para evitar la acci�n por omisi�n del link.
        return false;
    });
});
