var geocoder = new google.maps.Geocoder();
var marker = new google.maps.Marker();

function initialize() {

	mapInit('googleMap');

	searchInit('pac-input');

	google.maps.event.addListener(map, 'click', function(event) {
		marker.setMap(null);
		placeMarker(event.latLng);
	});

	window.setTimeout(function() {
		$(".removable-i").fadeTo(1500, 0).slideUp(500, function(){
			$(this).remove(); 
		});
	}, 5000);
	
	if ((document.getElementById('latitude').value !=  null) && (document.getElementById('longitude').value !=  null)) {
		window.setTimeout(function() {
			placeMarker(new google.maps.LatLng(parseFloat(document.getElementById('latitude').value), parseFloat(document.getElementById('longitude').value)));

		}, 500);
	}
}

google.maps.event.addDomListener(window, 'load', initialize);

function geocodeAddress(geocoder, resultsMap) {
	var address = document.getElementById('addressGeo').value;
	geocoder.geocode({'address': address}, function(results, status) {
		if (status === google.maps.GeocoderStatus.OK) {
			resultsMap.setCenter(results[0].geometry.location);
			marker = new google.maps.Marker({
				map: resultsMap,
				position: results[0].geometry.location
			});
			document.getElementById("latitude").value = results[0].geometry.location.lat();
			document.getElementById("longitude").value = results[0].geometry.location.lng();
		} else {
			document.getElementById("geo-error").innerHTML = 'Error: ' + status;
			window.setTimeout(function() {
				$(".removable-geo").fadeTo(1500, 0).slideUp(500, function(){
					$(this).remove(); 
				});
			}, 5000);
		}
	});
}

function placeMarker(location) {
	marker = new google.maps.Marker({
		position: location,
		map: map,
	});
	map.setCenter(location);
	geocoder.geocode({'location': location}, function(results, status) {
		document.getElementById("addressGeo").value = results[0].formatted_address;
		document.getElementById("latitude").value = results[0].geometry.location.lat();
		document.getElementById("longitude").value = results[0].geometry.location.lng();
	});
}

function check() {
	marker.setMap(null);
	geocodeAddress(geocoder, map);	
}

function fill() {
	var fullAddress = document.getElementById('addressGeo').value.split(',');
	document.getElementById("address.street").value = fullAddress[0].trim();
	document.getElementById("address.building").value = fullAddress[1].trim();
	document.getElementById("address.city").value = fullAddress[2].trim();
	document.getElementById("address.country").value = fullAddress[4].trim();
}

