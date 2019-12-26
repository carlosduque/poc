$(document).ready(function() {
    $('#selected-plays > li').addClass('horizontal');
    console.log($('#selected-plays > li'));
    $('#selected-plays li:not(.horizontal)').addClass('sub-level');
    $('a[href^=mailto:]').addClass('mailto');
    $('a[href$=.pdf]').addClass('pdflink');
    $('a[href^=http][href*=henry]').addClass('henrylink');
});

