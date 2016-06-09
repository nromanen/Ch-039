$(document).ready(function() {
    var mass = document.getElementById('mass');

    $('#form').validate({
        rules:{
            complaint:{
                required:true,
                minlength:5,
                maxlength: 254
            },
            result:{
                required:true,
                minlength:5,
                maxlength: 254
            },
            prescription:{
                required:true,
                minlength:5,
                maxlength: 254
            }
        },
        messages:{
            complaint:{
                required: mass.textContent,
                minlength:mass.textContent,
                maxlength: "max size = 254"
            },
            result:{
                required:mass.textContent,
                minlength:mass.textContent,
                maxlength: "max size = 254"
            },
            prescription:{
                required:mass.textContent,
                minlength:mass.textContent,
                maxlength: "max size = 254"
            }
        }
    });
});