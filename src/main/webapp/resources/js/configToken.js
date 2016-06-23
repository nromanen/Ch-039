/**
 * Created by andrew on 23.06.16.
 */

$(document).ready(function () {

    $("#configTokenForm").validate({
        rules: {
            verificationToken: {
                required: true,
                number: true,
                max: 168,
                min: 24
            },
            resetPasswordToken: {
                required: true,
                number: true,
                max: 168,
                min: 24
            },
            rememberMeToken: {
                required: true,
                number: true,
                max: 168,
                min: 24
            }
        },
       /* messages: {
            verificationToken: {
                required: "Token cannot be empty",
                number: "The value must only contain digits.",
                max: "Maximum token duration is 168 hours",
                min: "Token should be at least 24 hours",
            },
            confirmPassword: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                equalTo: "Please enter the same password as above"
            },
            email: {
                required: "Please provide a email",
                email: "Please enter a valid email address",
                whitespace: "Email can't contain white spaces",
                patternEmail: "Please enter a valid email address"
            },

        },*/
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

});