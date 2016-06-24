$(document).ready(function () {

    $.validator.addMethod("patternPassword", function (value, element) {
        return this.optional(element) || /^(?=.*\d)(?=.*[a-z])(?=.*[a-zA-Z]).{6,20}$/i.test(value);
    });


    $.validator.addMethod("whitespace", function (value, element) {
        return this.optional(element) || /^\S+$/i.test(value);
    });

    $.validator.addMethod("patternEmail", function (value, element) {
        return this.optional(element) || /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i.test(value);
    });

    $("#registerNewUser").validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
                maxlength: 20,
                patternPassword: "Y"
            },
            confirmPassword: {
                required: true,
                minlength: 6,
                equalTo: "#password"
            },
            email: {
                required: true,
                email: true,
                whitespace: "Y",
                patternEmail: "Y",
            }
        },
        messages: {
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                maxlength: "Your password should be less than 20 characters long",
                patternPassword: "Letters in upper and lower case, numbers, symbols",
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

});