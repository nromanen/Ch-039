var initialLocation;
var chernivci = new google.maps.LatLng(48.2872634, 25.938834);
var markers = [];
var map;

function mapInit(mapId) {

	var mapProp = {
			zoom:14,
			mapTypeId:google.maps.MapTypeId.ROADMAP
	};

	map = new google.maps.Map(document.getElementById(mapId), mapProp);

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

function searchInit(searchId) {

	var input = document.getElementById(searchId);

	var searchBox = new google.maps.places.SearchBox(input);

	searchBox.addListener('places_changed', function() {
		var places = searchBox.getPlaces();
		if (places.length == 0) {
			return;
		}
		map.setCenter(places[0].geometry.location);
	});	

}

function setMapOnAll(map) {
	
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(map);
	}
}

function clearMarkers() {
	
	setMapOnAll(null);
}

function showMarkers() {
	
	setMapOnAll(map);
}

function deleteMarkers() {
	
	clearMarkers();
	markers = [];
}