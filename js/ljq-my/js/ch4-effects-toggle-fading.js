$(document).ready(function() {
    var $firstPara = $('p:eq(1)');
    $firstPara.hide();
    $('a.more').click(function() {
        if ($firstPara.is(':hidden')) {
            $firstPara.fadeIn('slow');
            $(this).text('read less');
        } else {
            $firstPara.fadeOut('slow');
            $(this).text('read more');
        }
        //para evitar la acci�n por omisi�n del link.
        return false;
    });
});
