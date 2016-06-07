/**
 * @author Oleksandr Mukonin
 */
var messageExtension = $('meta[name="message-filetype"]').attr('content');
var messageUploaded = $('meta[name="message-uploaded"]').attr('content');
var messageSize = $('meta[name="message-filesize"]').attr('content');
var messageDimension = $('meta[name="message-dimension"]').attr('content');
var fileSizeLimit = 204800;
var imageMaxHeight = 400;
var imageMaxWidth = 400;

var isJpg = function(name) {
	return name.match(/jpg$/i)
};

var isPng = function(name) {
	return name.match(/png$/i)
};

function upload(typePath) {
	var filename = $('#file').val().trim();
	var label = filename.replace(/\\/g, '/').replace(/.*\//, '');
	var input = document.getElementById('file');
	var file = input.files[0];
	
	if (!(isJpg(filename) || isPng(filename))) {
		alert(messageExtension);
		return;
	}
	
	if (file.size > fileSizeLimit) {
		alert(messageSize);
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
		alert(messageDimension);
		return;			
	}
	
	document.getElementById('file-name').value = label;
	var file_data = $('#file').prop('files')[0]; 
	var form_data = new FormData(); 
	form_data.append('file', file_data)
	form_data.append('typePath', typePath)
	
	$.ajaxSetup({
		headers: {
			'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
		}
	});

	$.ajax({
		url: 'upload',
		dataType: 'text',
		cache: false,
		contentType: false,
		processData: false,
		data: form_data,
		type: 'post',
		success: function(data) {
			alert(messageUploaded);
			document.getElementById('imagePath').value = data;
		},
		error: function(qXHR, status, err) {
			document.getElementById('file-name').value = "";
			alert(qXHR.responseText);
		}
	})
}