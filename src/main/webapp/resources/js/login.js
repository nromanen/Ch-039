$(function () {
    $('.button-checkbox').each(function () {
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };

        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });

        $checkbox.on('change', function () {
            updateDisplay();
        });

        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');
            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }

        function init() {
            updateDisplay();
            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
            }
        }

        init();
    });
});

$(document).ready(function () {

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
        messages: {
            password: {
                required: "Please provide a password"
            },
            email: {
                required: "Please provide a email",
                email: "Please enter a valid email address"
            },
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


   /* $('#forgotPassword').modal({
        backdrop: 'static',
        keyboard: true,
        show: false
    });


    $('#floatingCirclesG').modal({
        backdrop: 'static',
        keyboard: false,
        show: false
    });*/


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
        var $inputEmail = $('#recoverEmail').val();
        var url = '/resetPassword?email=' + $inputEmail;
        $.ajax({
            url: url,
            dataType: 'text',
            async: true,
            start: blockGeneralModal(),
            success: function (data) {
                unblockGeneralModal();
                switch (data) {
                        case 'invalidEmail':
                    {
                        $('#errorReset').removeAttr("hidden").html("User with this email doesn't exists.");

                        break;
                    }
                    case 'inactivated':
                    {
                        $('#errorReset').removeAttr("hidden").html("User with this email inactivated.");
                        break;
                    }
                    case 'banned':
                    {
                        $('#errorReset').removeAttr("hidden").html("User with this email banned.");
                        break;
                    }
                    case 'tokenCreated':
                    {
                        $('#errorReset').removeAttr("hidden").html("User with this email already request change password.Check email.");
                        break;
                    }
                    case 'success' :
                    {
                        $('.recoverContent').attr("hidden", true);
                        $('#successMessage').removeAttr("hidden").html('Information about recover your password was sended on ' + $inputEmail);
                        document.querySelector("#recoverButton").style.display = "none";
                        break;
                    }
                    default :
                    {
                        $('.recoverContent').attr("hidden", true);
                        $('#successMessage').removeAttr("hidden").html('Something went wrong. Please try again!');
                    }
                }
            }
        });
    });

    //valid recover email
    $("#resetModal").validate({
        rules: {
            recoverEmail: {
                required: true,
                email: true
            }
        },
        messages: {
            recoverEmail: {
                required: "Please provide a email",
                email: "Please enter a valid email address"
            }
        },
        errorElement: "i",
        errorPlacement: function (error, element) {
            // Add the `help-block` class to the error element
            error.addClass("help-block");

            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents(".input-group").addClass("has-feedback");

            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.parent("label"));
            } else {
                error.insertAfter(element);
            }
        },

        success: function (label, element) {
        },
        highlight: function (element, errorClass, validClass) {
            $(element).parents(".input-group").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents(".input-group").addClass("has-success").removeClass("has-error");

        }
    });
});