$(document).ready(function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

    $('#viewModal').on('show.bs.modal', function (e) {
        $('.content').addClass('blur');
        var id = $(e.relatedTarget).data('id');
        var value = getMessage('admin.dashboard.users.modal.view.value')
        var field = getMessage('admin.dashboard.users.modal.view.field');

        var table = '<table class="table-fill">' +
            '<thead>' + '<tr>' + '<th class="text-left">' + value +
            '</th>' + '<th class="text-left">' + field + '</th>' + '</tr>' +
            '</thead>' + '<tbody class="table-hover">';

        $.ajax({
            type: 'POST',
            url: 'viewUser/' + id,
            async: false,
            datatype: 'json',
            contentType: "application/json",
            mimeType: "application/json",
            success: function (data) {
                $.each(data, function (key, val) {
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
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }
        });

        table += '</tbody>' + '</table>';
        $('.viewDetails').html(table);

        $('#viewModal').on('hide.bs.modal', function (e) {
            $('.content').removeClass('blur');
        });

        //change status on admin dashboard
        $('.userEnabled').click(function (event) {
            var id = $(this).attr("data-id");
            var status = $(this).val();
            var url = 'changeStatus/' + id + '/';
            if (status == 'Enabled') {
                $.get(url, function () {
                });
                $(this).val('Disabled').removeClass('btn-success').addClass('btn-danger');
            } else {
                $.get(url, function () {
                });
                $(this).val('Enabled').removeClass('btn-danger').addClass('btn-success');
            }
        });
    });
});