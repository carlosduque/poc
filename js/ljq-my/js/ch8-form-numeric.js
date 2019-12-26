$(document).ready(function() {

    var stripe = function() {
        $('#cart tbody tr').removeClass('alt').filter(':visible:odd').addClass('alt');
    };
    stripe();
    
    $('td.quantity input').keypress(function(event) {
        if (event.which && (event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
    });
    
    $('td.quantity input').change(function() {
        var totalQuantity = 0;
        var totalCost = 0;
      
        $('#cart tbody tr').each(function() {
            var price = parseFloat($('td.price', this).text().replace(/^[^\d.]*/, ''));
            price = isNaN(price) ? 0 : price;
            var quantity = parseInt($('td.quantity input', this).val());
            var cost = quantity * price;
            $('td.cost', this).text('$' + cost.toFixed(2));
            totalQuantity += quantity;
            totalCost += cost;
        });
        $('tr.shipping td.quantity').text(String(totalQuantity));
        var shippingRate = parseFloat($('tr.shipping td.price').text().replace(/^[^\d.]*/, ''));
        var shipping = totalQuantity * shippingRate;
        $('tr.shipping td.cost').text('$' + shipping.toFixed(2));
        totalCost += shipping;
        
        $('tr.subtotal td.cost').text('$' + totalCost.toFixed(2));
        
        var taxRate = parseFloat($('tr.tax td.price').text()) / 100;
        var tax = Math.ceil(totalCost * taxRate * 100) / 100;
        $('tr.tax td.cost').text('$' + tax.toFixed(2));
        totalCost += tax;
        
        $('tr.total td.cost').text('$' + totalCost.toFixed(2));
    });
    
    $('#recalculate').hide();
    
    $('<th>&nbsp;</th>').insertAfter('#cart thead th:nth-child(2)');
    $('#cart tbody tr').each(function(){
        $deleteButton = $('<img />').attr({
            'width': '16',
            'height': '16',
            'src': '../img/cross.png',
            'alt': 'remove from cart',
            'title': 'remove from cart',
            'class': 'clickable'
        }).click(function() {
            $(this).parents('tr').find('td.quantity input').val(0).trigger('change').end().hide();
            stripe();
        })
        $('<td></td>').insertAfter($('td:nth-child(2)', this)).append($deleteButton);
    });
    $('<td>&nbsp;</td>').insertAfter('#cart tfoot td:nth-child(2)');
});

$(document).ready(function() {
    var editShipping = function() {
        $.get('shipping.php', function(data) {
            $('#shipping-name').remove();
            $(data).hide().appendTo('#shipping').slideDown();
            $('#shipping form').submit(saveShipping);
        });
        return false;
    };
    
    var saveShipping = function() {
        var postData = $(this).serialize();
        $.post('shipping.php', postData, function(data) {
            $('#shipping form').remove();
            $(data).appendTo('#shipping');
            $('#shipping-name').click(editShipping);
        });
            return false;
    };
    $('#shipping-name').click(editShipping);
});