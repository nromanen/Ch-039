var initialLocation;
var chernivci = new google.maps.LatLng(48.2872634, 25.938834);
var markers = [];

function initialize() {

	//initialLocation = chernivci;

	var mapProp = {
			zoom:14,
			mapTypeId:google.maps.MapTypeId.ROADMAP
	};

	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

	google.maps.event.addListener(map, 'idle', getMarkers);
	
	var input = document.getElementById('pac-input');
	var searchBox = new google.maps.places.SearchBox(input);
	searchBox.addListener('places_changed', function() {
		var places = searchBox.getPlaces();
		if (places.length == 0) {
			return;
		}
		map.setCenter(places[0].geometry.location);
	});

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
	
	
}

google.maps.event.addDomListener(window, 'load', initialize);

function getMarkers() {
	var bounds = new Object();
	bounds.northEastLat = map.getBounds().getNorthEast().lat();
	bounds.northEastLon = map.getBounds().getNorthEast().lng();
	bounds.southWestLat = map.getBounds().getSouthWest().lat();
	bounds.southWestLon = map.getBounds().getSouthWest().lng();
	
	$.ajaxSetup({
		headers: {
			'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
		}
	});
	
	$.ajax({
		type: "POST",
		url: "getmarkers",
		data: JSON.stringify(bounds),
		datatype: "json",
		contentType: 'application/json',
		mimeType: 'application/json',
		success: function(data) {
			deleteMarkers();
			$.each(data, function (key, value) {
				var id = value.id;
				var lat = parseFloat(value.latitude);
				var lng = parseFloat(value.longitude);
				var name = value.name;
				var description = value.description;
				var address = value.address;
				var location = new google.maps.LatLng(lat, lng);
				var marker = new google.maps.Marker({
					position: location,
					title: name
				});
				google.maps.event.addListener(marker, 'click', function() {
					window.location = "./hospital/" + id;
				});
				markers.push(marker);
			});
			showMarkers();
		}
	})
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