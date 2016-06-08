$(document).ready(function() {
    var mass = document.getElementById('mass');

    $('#form').validate({
        rules:{
            complaint:{
                required:true,
                minlength:5
            },
            result:{
                required:true,
                minlength:5
            },
            prescription:{
                required:true,
                minlength:5
            }
        },
        messages:{
            complaint:{
                required: mass.textContent,
                minlength:mass.textContent
            },
            result:{
                required:mass.textContent,
                minlength:mass.textContent
            },
            prescription:{
                required:mass.textContent,
                minlength:mass.textContent
            }
        }
    });
});