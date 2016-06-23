var geocoder = new google.maps.Geocoder();
var marker = new google.maps.Marker();
	
function initialize() {

	mapInit('googleMap');
	
	searchInit('pac-input');
	
	window.setTimeout(function() {
	    $(".alert-timeout").fadeTo(1500, 0).slideUp(500, function(){
	        $(this).remove(); 
	    });
	}, 5000);
}

google.maps.event.addDomListener(window, 'load', initialize);

function placeMarker(lat, lng) {
	marker.setMap(null);
	loc = new google.maps.LatLng(parseFloat(lat), parseFloat(lng));
	marker = new google.maps.Marker({
		position: loc,
		map: map,
	});
	map.setCenter(loc);
}







