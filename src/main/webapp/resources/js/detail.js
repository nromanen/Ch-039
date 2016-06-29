
function submitForm(){
    if (getLocale() == 'ua') {
        $.extend( $.validator.messages, {
            required: 'Це поле необхідно заповнити.',
            maxlength: $.validator.format( 'Будь ласка, введіть не більше {0} символів.' )
        });
    }



    $.validator.addMethod("regex", function(value, element, regexpr) {
        return regexpr.test(value);});
    if (getLocale() == 'ua'){
        var phone = 'Не валідний формта. Приклад: +38 (095) 435-7132';
        var birthDate= 'Не валідний формат. Приклад: 1993-07-21';
        var gender= 'Не валідний формат. Має бути MALE або FEMALE';
        var firstName = 'Не валідний формат. Приклад: Solomon';
        var lastName = 'Не валідний формат. Приклад: Kane';
    }else{
        var phone = 'Not valid. +38 (095) 435-7132';
        var birthDate= 'Not valid. Example: 1993-07-21';
        var gender= 'Not valid. Mast be MALE or FEMALE';
        var firstName = 'Not valid. Example: Solomon';
        var lastName = 'Not valid. Example: Kane';
    }

    $('#detailForm').validate({
        rules: {
            firstName: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 36
            },
            lastName: {
                required: true,
                regex: /^[A-Z][a-z]+$/,
                maxlength: 36
            },
            birthDate: {
                required: true,
                regex: /[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])/
            },
            gender: {
                required: true,
                regex: /^MAN|WOMAN/
            },
            address: {
                required: true
            },
            phone: {
                required: true,
                regex: /^\+38 \(\d{3}\) \d{3}-\d{4}$/
            }
        },

        messages:{
            firstName:{
                regex: firstName
            },

            lastName:{
                regex: lastName
            },

            birthDate: {
                regex: birthDate
            },
            gender: {
                regex: gender
            },
            phone: {
                regex: phone
            }
        }
    });

if($('#detailForm').valid()){
    var str = $("#detailForm").serialize();
    $.ajax({

        type:"post",
        data:str,
        url:jsContextPath +"save/detail",
        async: false,
        success: function(response){
            $('#myModal').html(response);
            $('#birthDate').datepicker({
                format: 'yyyy-mm-dd',
                endDate: "0d"
            });

            $('#phone').bfhphone({
                format: '+38 (ddd) ddd-dddd'

            });
        }
    });
}
};

function real(){
    alert('YOU ARE THE BEST DEVELOPER EVER! With best regards Ch-039');
};

function showPage(){
    $.ajax({ type: 'GET', url: jsContextPath +'user/detail', contentType: 'application/json' , success: function(response) {
        $('#myModal').html(response);

    }})};

function showProfile(userId){
    $.ajax({ type: 'GET', url: jsContextPath +'user/profile?userId='+userId, success: function(response) {
        $('#profileModal').html(response);
    }})};

function editProfile(){
    $.ajax({ type: 'GET', url: jsContextPath +'user/detail?edit=true',contentType: 'application/json', success: function(response){
        $('#myModal').html(response);

        $('#birthDate').datepicker({
            format: 'yyyy-mm-dd',
            endDate: "0d"
        });

        $('#phone').bfhphone({
            format: '+38 (ddd) ddd-dddd'
        });

        if (document.getElementById('imagePath').value) {
            $('#image-uploaded').attr('src', jsContextPath + 'images/avatar/' + document.getElementById('imagePath').value);
        }

        var divModal = document.createElement('div');
        divModal.setAttribute('id', 'div-modal');
        document.body.appendChild(divModal);
        $('#div-modal').load(jsContextPath + 'modalupload.html');

    }})};







