//modal window for details
$(document).ready(function () {
$('#viewModal').on('show.bs.modal', function (e) {
    $('.content').addClass('blur');
    var id = $(e.relatedTarget).data('id');
    var url = '/admin/users/view/' + id + '/';
    console.log(url);
    var table = '<table class="table-fill">' + '<tbody class="table-hover">' +
        '<thead>' + '<tr>' + '<th class="text-left">' + 'Field' +
        '</th>' + '<th class="text-left">' + 'Value' + '</th>' + '</tr>' +
        '</thead>';
    $.ajax({
        type: 'GET',
        url: url,
        async: false,
        success: function (data) {
            $.each(data, function (key, val) {
                console.log(data);
                if (key == "enabled") {
                    if (val) {
                        table += '<tr>' + '<td class="text-left">' + key + '</td>' +
                            '<td class="text-left">' + '<input data-id=' + data.id + ' value=' + '"Enabled"' +
                            ' type="button"' + ' class="btn btn-sm btn-success userEnabled"' + ' />' +
                            '</td>' + '</tr>';
                    } else {
                        table += '<tr>' + '<td class="text-left">' + key + '</td>' +
                            '<td class="text-left">' + '<input data-id=' + data.id + ' value=' + '"Disabled"' +
                            ' type="button"' + ' class="btn btn-sm btn-danger userEnabled"' + ' />' +
                            '</td>' + '</tr>';
                    }
                }
                if (key == "userDetails") {
                    $.each(data.userDetails, function (key, val) {
                        table += '<tr>' + '<td class="text-left">' + key + '</td>' +
                            '<td class="text-left">' + val + '</td>' + '</tr>';
                    });
                } else {
                    table += '<tr>' + '<td class="text-left">' + key + '</td>' +
                        '<td class="text-left">' + val + '</td>' + '</tr>';
                }
            });
        }
    });
    table += '</tbody>' + '</table>'
    $('.viewDetails').html(table);

    $('#viewModal').on('hide.bs.modal', function (e) {
        $('.content').removeClass('blur');
    });

    //change status on admin dashboard
    $('.userEnabled').click(function (event) {
        var id = $(this).attr("data-id");
        var status = $(this).val();
        var url = '/admin/users/changeStatus/' + id + '/';
        if (status == 'Enabled') {
            $.get(url);
            $(this).val('Disabled').removeClass('btn-success').addClass('btn-danger');
        } else {
            $.get(url);
            $(this).val('Enabled').removeClass('btn-danger').addClass('btn-success');
        }
    });
});
});