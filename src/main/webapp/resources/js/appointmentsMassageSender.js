
function sendMassage(reason, eventId, principal) {
    
    var massageDto = new Object();
    massageDto.eventId = eventId;
    massageDto.principalMassage = reason;
    massageDto.principal = principal;
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
    $.ajax({
        type: "POST",
        async: false,
        url: "/sendMassage",
        data: JSON.stringify(massageDto),
        datatype: "json",
        contentType: 'application/json',
        mimeType: 'application/json'
    })
}
