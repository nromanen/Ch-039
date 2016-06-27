$(window).load(function(){
    if (document.getElementById('imagePath').value) {
        $('#image-uploaded').attr('src', addition + '/images/avatar/' + document.getElementById('imagePath').value);
    }

});

