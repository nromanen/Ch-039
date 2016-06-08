$(document).ready(function (){

	$("#feedback").popover({
		"show":500,hide:"100"
	});

});


function focusToFeedback(event){
	document.getElementById("feedback").focus();
	document.getElementById("feedback").scrollIntoView();

}

function sendFeedback(event){
	var path = $("#path").val();
	var userEmail = $("#currentUser").text();
	var message = document.getElementById("feedback").value;
	var doctorId = $("#doctorID").text();
	$.ajaxSetup({
		headers: {
			'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
		}
	});

	var sendData = {"message":message,
			'userEmail':userEmail,
			'doctorId':doctorId}; 
	if(message==""){
		$("#feedback").popover('show');
		setTimeout(function(){
			$("#feedback").popover('hide');
		},1000);
		
	} else $.ajax({
		type:'POST',
		url: path + "/doctor/feedback",
		data: JSON.stringify(sendData),
		datatype:"json",
		contentType:"application/json",
		mimeType:"application/json",
		async:true,
		success:function(result){
			window.location.reload();
		},
		error:function(err){
			alert("Error");
		}
	});
}
