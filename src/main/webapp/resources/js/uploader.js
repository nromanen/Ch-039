/**
 * @author Oleksandr Mukonin
 */

var parameters;

var pathname = window.location.pathname;
var patharr = pathname.split('/');
var addition = patharr[1];
if (addition != 'HospitalSeeker') {
	addition = '';
} else {
	addition = '/' + addition;
}

$.ajaxSetup({
	headers: {
		'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
	}
});

$.ajax({
	type: "POST",
	url: "getparams/uploadparams",
	datatype: "json",
	contentType: 'application/json',
	mimeType: 'application/json',
	success: function(data) {
		parameters = data;
	}
});

function getProperty(code) {
	return parameters[code];
}

function isImage(name, type) {
	var pattern = new RegExp(getProperty(type + '.file.name.js.pattern'), getProperty(type + '.file.name.js.pattern.param'));
	return name.match(pattern);
};

function upload(type) {
	var fileSizeLimit = parseInt(getProperty(type + '.file.max.size'));
	var imageMaxHeight = parseInt(getProperty(type + '.image.max.height'));
	var imageMaxWidth = parseInt(getProperty(type + '.image.max.width'));
	var filename = $('#file').val().trim();
	if (filename == null || filename == "") {return};
	var label = filename.replace(/\\/g, '/').replace(/.*\//, '');
	var input = document.getElementById('file');
	var file = input.files[0];

	if (!(isImage(filename, type))) {
		showMessage(getMessage('upload.image.filetype'), 'alert-warning');
		return;
	}

	if (file.size > fileSizeLimit) {
		showMessage(getMessage('upload.image.filesize'), 'alert-warning');
		return;		
	}

	var img = new Image();
	var dimensionError = false;
	img.src = window.URL.createObjectURL(file);
	img.onload = function() {
		var width = img.naturalWidth;
		var height = img.naturalHeight;
		window.URL.revokeObjectURL( img.src );
		if( width > imageMaxWidth || height > imageMaxHeight ) {
			dimensionError = true;
		}
	}

	if (dimensionError) {
		showMessage(getMessage('upload.image.dimension'), 'alert-warning');
		return;			
	}

	var file_data = $('#file').prop('files')[0]; 
	var form_data = new FormData();
	form_data.append('file', file_data)
	form_data.append('type', type);
	console.log(file_data);
	console.log(type);
	console.log(label);

	$.ajax({
		url: 'upload',
		dataType: 'text',
		cache: false,
		contentType: false,
		processData: false,
		data: form_data,
		type: 'post',
		success: function(data) {
			showMessage(getMessage('upload.image.uploaded'));
			document.getElementById('imagePath').value = data;
			$('#image-uploaded').attr('src', addition + '/images/' + type + '/' + data);
		},
		error: function(qXHR, status, err) {
			showMessage(qXHR.responseText, 'alert-warning');
		}
	})
}

function showMessage(message, type) {
	type = (typeof type === 'undefined') ? 'alert-success' : type;
	var divModal = document.createElement('div');
	divModal.setAttribute('id', 'modalAlert');
	divModal.setAttribute('class', 'modal fade');
	divModal.setAttribute('role', 'dialog');
	var divDialog = document.createElement('div');
	divDialog.setAttribute('class', 'modal-dialog');
	var divContent = document.createElement('div');
	divContent.setAttribute('class', 'modal-content');
	var divHeader = document.createElement('div');
	divHeader.setAttribute('class', 'modal-header');
	var divBody = document.createElement('div');
	divBody.setAttribute('class', 'modal-body');
	var divFooter = document.createElement('div');
	divFooter.setAttribute('class', 'modal-footer');
	var divAlert = document.createElement('div');
	divAlert.setAttribute('id', 'divAlert');
	divAlert.setAttribute('class', 'alert alert-success');
	var pHeader = document.createElement('p');
	pHeader.setAttribute('id', 'modalHeaderText');
	var pBody = document.createElement('p');
	pBody.setAttribute('id', 'modalBodyText');
	var button1 = document.createElement('button');
	button1.setAttribute('id', 'modalOK');
	button1.setAttribute('type', 'button');
	button1.setAttribute('class', 'btn btn-default');
	button1.setAttribute('data-dismiss', 'modal');	
	var buttonText = document.createTextNode('OK');
	button1.appendChild(buttonText);
	divFooter.appendChild(button1);
	divAlert.appendChild(pBody);
	divBody.appendChild(divAlert);
	divHeader.appendChild(pHeader);
	divContent.appendChild(divHeader);
	divContent.appendChild(divBody);
	divContent.appendChild(divFooter);
	divDialog.appendChild(divContent);
	divModal.appendChild(divDialog);
	document.body.appendChild(divModal);
	document.getElementById('divAlert').className = 'alert ' + type;
	document.getElementById('modalBodyText').innerText = message;
	document.getElementById('modalHeaderText').innerText = getMessage('global.modal.info');	
	$('#modalAlert').modal();	
	window.setTimeout(function() {
		document.getElementById('modalOK').click();
	}, 5000);
}