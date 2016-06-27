/**
 * @author Oleksandr Mukonin
 */

var parameters;

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
		showModal(getMessage('global.modal.info'), getMessage('upload.image.filetype'), 'alert-warning');
		return;
	}

	if (file.size > fileSizeLimit) {
		showModal(getMessage('global.modal.info'), getMessage('upload.image.filesize'), 'alert-warning');
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
		showModal(getMessage('global.modal.info'), getMessage('upload.image.dimension'), 'alert-warning');
		return;			
	}

	var file_data = $('#file').prop('files')[0]; 
	var form_data = new FormData();
	form_data.append('file', file_data)
	form_data.append('type', type);

	$.ajax({
		url: 'upload',
		dataType: 'text',
		cache: false,
		contentType: false,
		processData: false,
		data: form_data,
		type: 'post',
		success: function(data) {
			showModal(getMessage('global.modal.info'), getMessage('upload.image.uploaded'));
			document.getElementById('imagePath').value = data;
			$('#image-uploaded').attr('src', jsContextPath + 'images/' + type + '/' + data);
		},
		error: function(qXHR, status, err) {
			showModal(getMessage('global.modal.info'), qXHR.responseText, 'alert-warning');
		}
	})
}

$(window).load(function() {
	var divModal = document.createElement('div');
	divModal.setAttribute('id', 'div-modal');
	document.body.appendChild(divModal);
	$('#div-modal').load(jsContextPath + 'modalupload.html');
});

function showModal(headerText, bodyText, type) {
	type = (typeof type === 'undefined') ? 'alert-success' : type;
	document.getElementById('divAlert').className = 'alert ' + type;
	document.getElementById('js-modal-header').innerText = headerText;
	document.getElementById('js-modal-body').innerText = bodyText;
	$('#modalAlert').modal('show');
	window.setTimeout(function() {
		document.getElementById('modalOK').click();
	}, 5000);
}