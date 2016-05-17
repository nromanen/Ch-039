var initialLocation;
var chernivci = new google.maps.LatLng(48.2872634, 25.938834);
var map;
var geocoder = new google.maps.Geocoder();
var marker = new google.maps.Marker();
	
function initialize() {
	
	//initialLocation = chernivci;
	
	var mapProp = {
		zoom:14,
		mapTypeId:google.maps.MapTypeId.ROADMAP
	};
	
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
			map.setCenter(initialLocation);
		}, function() {
			handleNoGeolocation();
		});
	} else {
		handleNoGeolocation();
	}
	
	function handleNoGeolocation() {
		$.get("http://ipinfo.io", function (response) {
			var latlngStr = response.loc.split(",",2);
			var lat = parseFloat(latlngStr[0]);
			var lng = parseFloat(latlngStr[1]);
			initialLocation = new google.maps.LatLng(lat, lng);
			map.setCenter(initialLocation);
		}, "jsonp");
	}
	
	window.setTimeout(function() {
	    $(".alert").fadeTo(1500, 0).slideUp(500, function(){
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







