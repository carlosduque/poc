$(document).ready(function() {
    /*
     * A cada link externo, agregarle un ícono que indica que es externo
     */
    $('a').filter(function() {
       return this.hostname && this.hostname != location.hostname;
    }).addClass('external');
    /*
     * Si un parrafo tiene la palabra 'bibendum', el siguiente debe remarcarse.
     */
    $('p:contains(bibendum)').next().addClass('remark-next');
    $('p:contains(Vivamus)').prev().andSelf().addClass('remark-prev');
});