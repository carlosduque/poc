$(document).ready(function() {
    $('#my-data th a').click(function() {
        $('#my-data tbody').load($(this).attr('href'));
        return false;
    });
});

