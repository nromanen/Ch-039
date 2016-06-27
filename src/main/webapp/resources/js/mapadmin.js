var geocoder = new google.maps.Geocoder();
var marker = new google.maps.Marker();

function initialize() {

	mapInit('googleMap');

	google.maps.event.addListener(map, 'click', function(event) {
		marker.setMap(null);
		placeMarker(event.latLng);
	});

	window.setTimeout(function() {
		$('.removable-i').fadeTo(1500, 0).slideUp(500, function(){
			$(this).remove(); 
		});
	}, 5000);

	window.setTimeout(function() {
		resetAddress();
	}, 500);

	$('#form-hospital').validate({
		rules: {
			'address.country': {
				required: true
			},
			'address.city': {
				required: true
			},
			'address.building': {
				maxlength: 5
			},
			latitude: {
				required: true
			},
			longitude: {
				required: true
			},
			name: {
				required: true,
				minlength: 5,
				maxlength: 50
			}
		}
	});
	
	if (document.getElementById('imagePath').value) {
		$('#image-uploaded').attr('src', jsContextPath + 'images/hospital/' + document.getElementById('imagePath').value);
	}
}

function resetAddress() {
	marker.setMap(null);
	if ((document.getElementById('latitude').value !=  '') && (document.getElementById('longitude').value !=  '')) {
		placeMarker(new google.maps.LatLng(parseFloat(document.getElementById('latitude').value), parseFloat(document.getElementById('longitude').value)));	
	}
}

google.maps.event.addDomListener(window, 'load', initialize);

function geocodeAddress(geocoder, resultsMap) {
	var address = document.getElementById('addressGeo').value;
	geocoder.geocode({'address': address}, function(results, status) {
		if (status === google.maps.GeocoderStatus.OK) {
			resultsMap.setCenter(results[0].geometry.location);
			marker.setMap(null);
			marker = new google.maps.Marker({
				map: resultsMap,
				position: results[0].geometry.location
			});
			document.getElementById('latitude').value = results[0].geometry.location.lat();
			document.getElementById('longitude').value = results[0].geometry.location.lng();
			$('#div-latlng :input').valid();
		} else {
			document.getElementById('geo-error').innerHTML = 'Error: ' + status;
			window.setTimeout(function() {
				$('.removable-geo').fadeTo(1500, 0).slideUp(500, function(){
					$(this).remove(); 
				});
			}, 5000);
		}
	});
}

function placeMarker(location) {
	google.maps.event.trigger(map, 'resize');
	marker.setMap(null);
	marker = new google.maps.Marker({
		position: location,
		map: map,
	});
	map.setCenter(location);
	geocoder.geocode({'location': location}, function(results, status) {
		document.getElementById('addressGeo').value = results[0].formatted_address;
		document.getElementById('latitude').value = results[0].geometry.location.lat();
		document.getElementById('longitude').value = results[0].geometry.location.lng();
		$('#div-latlng :input').valid();
	});
}

function check() {
	geocodeAddress(geocoder, map);
}

function fill() {
	var fullAddress = document.getElementById('addressGeo').value.split(',');
	document.getElementById('address.street').value = fullAddress[0].trim();
	document.getElementById('address.building').value = fullAddress[1].trim();
	document.getElementById('address.city').value = fullAddress[2].trim();
	document.getElementById('address.country').value = fullAddress[4].trim();
	$('#div-countrycity :input').valid();
}