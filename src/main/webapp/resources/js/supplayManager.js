/**
 * Created by igortsapyak on 06.06.16.
 */



function clearManager(hospitalId) {

    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

    $.ajax({
        async: false,
        type: "POST",
        url: "deleteManager?hospitalId=" + hospitalId,
        data: hospitalId,
        datatype: "json",
        contentType: 'application/json',
        mimeType: 'application/json'
    });

        location.reload();

}

function applyManager(userId, hospitalId) {

    var ids = new Object();
    ids.hospitalId = hospitalId;
    ids.userId = userId;


    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

    $.ajax({
        type: "POST",
        url: "applyManager",
        data: JSON.stringify(ids),
        datatype: "json",
        contentType: 'application/json',
        mimeType: 'application/json'
    });

}