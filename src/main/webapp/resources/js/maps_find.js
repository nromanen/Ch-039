function showModalMap(lat, lng) {	
	
	$("#modalMap").modal();
	
	var latLng = new google.maps.LatLng(lat, lng);
	
	$('#modalMap').on('shown.bs.modal', function() {
	  
	var mapProp = {
			zoom:17,
			mapTypeId:google.maps.MapTypeId.ROADMAP,
		    mapTypeControl:false,
		    streetViewControl:false
	};
	
	map = new google.maps.Map(document.getElementById('googleMap'), mapProp);
	
	map.setCenter(latLng);
	
	marker = new google.maps.Marker({
		position: latLng,
		map: map
	});  	
});	
}

function citySearch(event){
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			cityFromLatLng(position.coords.latitude,position.coords.longitude);
		}, function() {
			errorGeo();
		});
	} else {
		errorGeo();
	}							
}

function cityFromLatLng(lat, lng) {
	var path = $('#path').val();
	$.ajax({
        type: 'GET',
        dataType: "json",
		async: false,
        url: "http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false",
        data: {},
        success: function(data) {
            $.each( data['results'],function(i, val) {
                $.each( val['address_components'],function(i, val) {
                    if (val['types'] == "locality,political") {
                        if (val['long_name']!="") {
							var city = val['long_name'];
							window.location.href = path+"/hospitals?q="+city;
                        }
                    }
                });
            });
        },
        error: function(data){
        	window.location.href = path+"/hospitals?q=Chernivtsi";
        }
        
    }); 
}

function errorGeo() {
	$.ajax({
        type: 'GET',
		url: 'http://ipinfo.io',
        dataType: "jsonp",
		async: false,
		data: {},
        success: function(data) {			
			var city = data.city;
			window.location.href = path+"/hospitals?q="+city;
		}
	});	
}