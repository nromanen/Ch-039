
$(document).ready(function () {
    var loc = location.pathname;
    var url = loc.match(/(\/).*(\/).*(\/)/g);

    var alertError = 'alert-warning';
    var alertSuccess = 'alert-success';

    $(window).load(function () {
        var divModal = document.createElement('div');
        divModal.setAttribute('id', 'div-modal');
        document.body.appendChild(divModal);
        if (url == null) {
            $('#div-modal').load('/alert.html');
        } else {
            $('#div-modal').load(url + '/alert.html');
        }
    });


    function showModal(headerText, bodyText, type, additionalText) {
        additionalText = (typeof type === 'undefined') ? "" : additionalText;
        document.getElementById('divAlert').className = 'alert ' + type;
        document.getElementById('js-modal-header').innerText = headerText;
        document.getElementById('js-modal-body').innerText = additionalText + bodyText;
        $('#modalAlert').modal('show');
        window.setTimeout(function () {
            document.getElementById('modalOK').click();
        }, 5000);
    }


    if ($('#messageSuccess').length) {
        alert("SUCCESS");
        showModal(getMessage('global.modal.info'),
            getMessage('admin.dashboard.message.success.edit'),
            alertSuccess, $('#messageSuccess').val());
    }

    if ($('#messageError').length) {
        alert("EROR");
        showModal(getMessage('global.modal.info'),
            getMessage('admin.dashboard.message.error.edit'),
            alertError);
    }

});




