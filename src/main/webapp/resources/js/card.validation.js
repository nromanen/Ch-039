$(document).ready(function() {
    var massMin = document.getElementById('massMin');
    var massMax = document.getElementById('massMax');

    $('#form').validate({
        rules:{
            complaint:{
                required:true,
                minlength:5,
                maxlength: 1000
            },
            result:{
                required:true,
                minlength:5,
                maxlength: 1000
            },
            prescription:{
                required:true,
                minlength:5,
                maxlength: 1000
            }
        },
        messages:{
            complaint:{
                required: massMin.textContent,
                minlength:massMin.textContent,
                maxlength: massMax.textContent
            },
            result:{
                required: massMin.textContent,
                minlength:massMin.textContent,
                maxlength: massMax.textContent
            },
            prescription:{
                required: massMin.textContent,
                minlength:massMin.textContent,
                maxlength: massMax.textContent
            }
        }
    });
});