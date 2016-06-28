$(document).keyup(function (e) {
    if (e.keyCode == 27) { // escape key maps to keycode `27`
        $('#myModal').modal('hide');
        $('#profileModal').modal('hide');
        $('#editUserModal').modal('hide');
        location.reload();
    }

    if (e.keyCode == 13) { // escape key maps to keycode `27`
        $('#submitChanges').trigger('click');
    }

});
