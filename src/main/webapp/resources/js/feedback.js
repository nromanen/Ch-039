$(document).ready(function (){
	$.ajaxSetup({
		headers: {
			'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
		}
	});
	$("#feedback").popover({
		"show":500,hide:"100"
	});
 
});


function focusToFeedback(event){
	document.getElementById("feedback").focus();
	document.getElementById("feedback").scrollIntoView();

}

function sendFeedback(event){
	var userEmail = $("#currentUser").text();
	 
	var message = document.getElementById("feedback").value;
	var date = new Date();
	var doctorId = $("#doctorID").text();
	if(message==""){
		$("#feedback").popover('show');
		setTimeout(function(){
			$("#feedback").popover('hide');
		},1000);
	} else  $.get("/HospitalSeeker/doctor/feedback",{"message":message,
		'userEmail':userEmail,
		'doctorId':doctorId}, function(data){
			window.location.reload();
		});
}
