/**
 * 
 */
function sendPageConfig(event){
		var itemPerPage = $('#pref-perpage option:selected').text();
		if(itemPerPage === "" || itemPerPage== null ){
			itemPerPage = "10"
		}	
		var sort = $("#pref-orderby").val();
		var url = $('#path').val()+'/hospital/page/config';
		$.get(url,{
			'itemPerPage':itemPerPage,
			'type':sort},
			function(data){
				window.location.reload();
			});
		
	}