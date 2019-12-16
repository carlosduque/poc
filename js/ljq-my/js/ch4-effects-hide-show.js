$(document).ready(function() {
    $('p:eq(1)').hide();
    $('a.more').click(function() {
        //$('p:eq(1)').show('slow');
        $('p:eq(1)').fadeIn('slow');
        $(this).hide();
        //para evitar la acción por omisión del link.
        return false;
    });
});
