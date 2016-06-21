/**
 * @author Oleksandr Mukonin
 * 
 */

var messages;

$.ajaxSetup({
	headers: {
		'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
	}
});	

$.ajax({
	type: "POST",
	url: "getparams/messages",
	datatype: "json",
	contentType: 'application/json',
	mimeType: 'application/json',
	success: function(data) {
		messages = data;
	}
});

function getMessage(code) {
	return messages[code];
}

function getLocale() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)org\.springframework\.web\.servlet\.i18n\.CookieLocaleResolver\.LOCALE\s*\=\s*([^;]*).*$)|^.*$/, "$1");
}