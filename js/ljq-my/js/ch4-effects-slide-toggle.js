$(document).ready(function() {
    var $firstPara = $('p:eq(1)');
    $firstPara.hide();
    $('a.more').click(function() {
        $firstPara.slideToggle('slow');
        var $link = $(this);
        if ($link.text() == "read more") {
            $link.text('read less');
        } else {
            $link.text('read more');
        }
        //para evitar la acción por omisión del link.
        return false;
    });
});
