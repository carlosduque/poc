$(document).ready(function() {
    var $search = $('#search').addClass('overlabel');
    var $searchInput = $search.find('input');
    var $searchLabel = $search.find('label');

    if ($searchInput.val()){
        $searchLabel.hide();
    }

    $searchInput
        .focus(function(){
            $searchLabel.hide();
        })
        .blur(function(){
            if (this.value == '') {
                $searchLabel.show();
            }
        });

    $searchLabel.click(function() {
        $searchInput.trigger('focus');
    });
    
    //Autocomplete
    var $autocomplete = $('<ul class="autocomplete"></ul>')
        .hide()
        .insertAfter('#search-text');
        var selectedItem = null;

    var setSelectedItem = function(item) {
        selectedItem = item;
        if (selectedItem === null) {
            $autocomplete.hide();
            return;
        }
        if (selectedItem < 0) {
            selectedItem = 0;
        }
        if (selectedItem >= $autocomplete.find('li').length){
            selectedItem = $autocomplete.find('li').length - 1;
        }
        $autocomplete.find('li').removeClass('selected')
            .eq(selectedItem).addClass('selected');
        $autocomplete.show();
    };
    
    var populateSearchField = function() {
        $('#search-text').val($autocomplete.find('li').eq(selectedItem).text());
        setSelectedItem(null);
    };
    
    $('#search-text').attr('autocomplete', 'off').keyup(function(event) {
        if (event.keyCode > 40 || event.keyCode == 8) {
            $.ajax({
                'url':'autocomplete.php',
                'data': {'search-text': $('#search-text').val()},
                'dataType':'json',
                'type':'GET',
                'success':function(data) {
                    if (data.length) {
                        $autocomplete.empty();
                        $.each(data, function(index, term) {
                            $('<li></li>').text(term).appendTo($autocomplete)
                                .mouseover(function() {
                                    setSelectedItem(index);
                                })
                                .click(populateSearchField);
                        });
                        setSelectedItem(0);
                        //$autocomplete.show();
                    }
                    else {
                        setSelectedItem(null);
                    }
                }
            });
        }
        else if (event.keyCode == 38 && selectedItem !== null) {
            //User pressed up arrow
            setSelectedItem(selectedItem - 1);
            event.preventDefault();
        }
        else if (event.keyCode == 40 && selectedItem !== null) {
            //User pressed down arrow
            setSelectedItem(selectedItem + 1);
            event.preventDefault();
        }
        else if (event.keyCode == 27 && selectedItem !== null) {
            //User pressed escape key
            populateSearchField();
            event.preventDefault();
        }        
    }).keypress(function(event) {
        if (event.keyCode == 13 && selectedItem !== null) {
            //User pressed enter
            populateSearchField();
            event.preventDefault();
        }
    }).blur(function(event) {
        setTimeout(function() {
            setSelectedItem(null);
        }, 250);
    });

});
