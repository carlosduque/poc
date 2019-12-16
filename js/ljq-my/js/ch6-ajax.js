$(document).ready(function() {
    $('#letter-a a').click(function() {
        $('#dictionary').hide().load('a.html', function() {
            $(this).fadeIn();
        });
        return false;
    });
    
    $('#letter-b a').click(function() {
        $.getJSON('b.json', function(data) {
            $('#dictionary').empty();
            $.each(data, function(entryIndex, entry) {
                console.log(entryIndex + ": ");
                var html = '<div class="entry">';
                html += '<h3 class="term">' + entry['term'] + '</h3>';
                html += '<div class="part">' + entry['part'] + '</div>';
                html += '<div class="definition">';
                html += entry['definition'];
                if (entry['quote']) {
                    html += '<div class="quote">';
                    $.each(entry['quote'], function(lineIndex, line) {
                        html += '<div class="quote-line">' + line + '</div>';
                    });
                    if (entry['author']) {
                        html += '<div class="quote-author">' + entry['author'] + '</div>';
                    }
                    html += '</div>';
                }
                html += '</div>';
                html += '</div>';
                console.log(html);
                $('#dictionary').append(html);
            });
        });
        return false;
    });
    
    $('#letter-c a').click(function() {
        $.getScript('c.js');
        return false;
    });
    
    $('#letter-d a').click(function() {
        $.get('d.xml', function(data) {
            $('#dictionary').empty();
            // entries with nested quotes with authors
            //$(data).find('entry:has(quote[author])').each(function() {
            $(data).find('entry').each(function() {
                var $entry = $(this);
                var html = '<div class="entry">';
                html += '<h3 class="term">' + $entry.attr('term') + '</h3>';
                html += '<div class="part">' + $entry.attr('part') + '</div>';
                html += '<div class="definition">';
                html += $entry.find('definition').text();
                var $quote = $entry.find('quote');
                if ($quote.length) {
                    html += '<div class="quote">';
                    $quote.find('line').each(function() {
                        html += '<div class="quote-line">' + $(this).text() + '</div>';
                    });
                    if ($quote.attr('author')) {
                        html += '<div class="quote-author">' + $quote.attr('author') + '</div>';
                    }
                    html += '</div>';
                }
                html += '</div>';
                html += '</div>';
                $('#dictionary').append($(html));
            });
        });
        return false;
    });
    
    $('#letter-e a').click(function() {
        //$.post('e.php', {'term': $(this).text()}, function(data) {
        $.get('e.php', {'term': $(this).text()}, function(data) {
            $('#dictionary').html(data);
        });
        
        //.load usa POST por default
        //$('#dictionary').load('e.php', {'term': $(this).text()});
        return false;
    });
    
    $('#letter-f form').submit(function() {
        //$('#dictionray').load('f.php', {'term': $('input[name="term"]').val()});
        //agregar input fields uno por uno no es practico, por lo que mejor se usa .serialize()
        console.log("serialize: " + $(this).serialize());
        $.get('f.php', $(this).serialize(), function(data) {
            $('#dictionary').html(data);
        });
        return false;
    });
    
    $('<div id="loading">Loading...</div>')
        .insertBefore('#dictionary')
        .ajaxStart(function() {
            $(this).show();
        })
        .ajaxStop(function() {
            $(this).hide();
        });
    
    //ligando el 'click' al documento en sí ya que los .terms si no han sido creados en el documento,
    //no puede ligarseles algo al evento 'click' asi que usamos 'event delegation'
    // con .live le pedimos al navegador observar Todos los clicks de la pagina pero solo reaccionar
    // a .term
    $('.term').live('click', function() {
        $(this).siblings('.definition').slideToggle();
    });
    
});

