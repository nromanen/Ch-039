function initialize() {

	mapInit('googleMap');
	
	searchInit('pac-input');

	google.maps.event.addListener(map, 'idle', getMarkers);
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
	
	document.getElementById('pac-input').focus();
}