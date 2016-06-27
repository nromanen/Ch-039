/**
 * Created by andrew on 24.06.16.
 */
var loc = location.pathname;
var url = loc.match(/(\/).*(\/)/g);
$(window).load(function () {
    var divModal = document.createElement('div');
    divModal.setAttribute('id', 'floatingModal');
    document.body.appendChild(divModal);
    if(url==null){
        $('#floatingModal').load('/spinner.html');
    }else{
        $('#floatingModal').load(url + '/spinner.html');
    }
});