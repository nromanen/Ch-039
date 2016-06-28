$(document).ready(function () {

    //localization message
    var invalidEmail = getMessage('login.message.error.invalidEmail');
    var inactivated = getMessage('login.message.error.inactivated');
    var banned = getMessage('login.message.error.banned');
    var tokenCreated = getMessage('login.message.error.tokenCreated');
    var successMessage = getMessage('login.message.error.successMessage');
    var errorMessage = getMessage('login.message.error.errorMessage');

    //valid login form
    $("#loginForm").validate({
        rules: {
            password: {
                required: true
            },
            email: {
                required: true,
                email: true
            }
        },
        errorElement: "i",
        errorPlacement: function (error, element) {
            // Add the `help-block` class to the error element
            error.addClass("help-block");

            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents(".form-group").addClass("has-feedback");

            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.parent("label"));
            } else {
                error.insertAfter(element);
            }

            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if (!element.next("span")[0]) {
                $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
            }
        },
        success: function (label, element) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if (!$(element).next("span")[0]) {
                $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).parents(".form-group").addClass("has-error").removeClass("has-success");
            $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents(".form-group").addClass("has-success").removeClass("has-error");
            $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
        }
    });

    //add and remove background blur
    $('#forgotPassword').on('show.bs.modal', function () {
        $('.content').addClass('blur');
    });

    $('#forgotPassword').on('hide.bs.modal', function () {
        $('.content').removeClass('blur');
    });

    function blockGeneralModal() {
        $('#forgotPassword').modal('hide');
        $('#floatingCirclesG').modal('show');

    }

    function unblockGeneralModal() {
        $('#forgotPassword').modal('show');
        $('#floatingCirclesG').modal('hide');
    }


    //modal recover password
    $('#recoverButton').click(function (event) {
        $("#resetModal").validate({
            rules: {
                recoverEmail: {
                    required: true,
                    email: true
                }
            },
            errorElement: "i",
            errorPlacement: function (error, element) {
                // Add the `help-block` class to the error element
                error.addClass("help-block");

                // Add `has-feedback` class to the parent div.form-group
                // in order to add icons to inputs
                element.parents(".form-group").addClass("has-feedback");

                if (element.prop("type") === "checkbox") {
                    error.insertAfter(element.parent("label"));
                } else {
                    error.insertAfter(element);
                }

                // Add the span element, if doesn't exists, and apply the icon classes to it.
                if (!element.next("span")[0]) {
                    $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
                }
            },
            success: function (label, element) {
                // Add the span element, if doesn't exists, and apply the icon classes to it.
                if (!$(element).next("span")[0]) {
                    $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
                }
            },
            highlight: function (element, errorClass, validClass) {
                $(element).parents(".form-group").addClass("has-error").removeClass("has-success");
                $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).parents(".form-group").addClass("has-success").removeClass("has-error");
                $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
            }
        });

        function resolveCallDatabase(data) {
            switch (data) {
                case 'invalidEmail':
                {
                    $('#errorReset').removeAttr("hidden").html(invalidEmail);
                    break;
                }
                case 'inactivated':
                {
                    $('#errorReset').removeAttr("hidden").html(inactivated);
                    break;
                }
                case 'banned':
                {
                    $('#errorReset').removeAttr("hidden").html(banned);
                    break;
                }
                case 'tokenCreated':
                {
                    $('#errorReset').removeAttr("hidden").html(tokenCreated);
                    break;
                }
                case 'success' :
                {
                    $('.recoverContent').attr("hidden", true);
                    $('#successMessage').removeAttr("hidden").html(successMessage + $inputEmail);
                    document.querySelector("#recoverButton").style.display = "none";
                    break;
                }
                default :
                {
                    $('.recoverContent').attr("hidden", true);
                    $('#errorMessage').removeAttr("hidden").html(errorMessage);
                }
            }
        }

        if ($("#resetModal").valid()) {
            var $inputEmail = $('#recoverEmail').val();
            var url = 'resetPassword?email=' + $inputEmail;
            $.ajax({
                url: url,
                dataType: 'text',
                async: true,
                start: blockGeneralModal(),
                success: function (data) {
                    window.setTimeout(function () {
                        unblockGeneralModal();
                    }, 500);
                    resolveCallDatabase(data);
                }
            });
        }
    });

    $('#recoverEmail').focus(function () {
        $('.text-danger').attr("hidden", true);
    });
});