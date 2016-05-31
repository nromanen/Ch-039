var hospitals = [];
var hospital;
var marker = new google.maps.Marker();
var addresses = [];

function initialize() {

	mapInit('googleMap');

	window.setTimeout(function() {
		marker = new google.maps.Marker({
			position: map.getCenter(),
			map: map
		});
	}, 500);
}

google.maps.event.addDomListener(window, 'load', initialize);

function process() {
	var request = {
			location: map.getCenter(),
			radius: '5000',
			query: 'hospital'
	};
	service = new google.maps.places.PlacesService(map);
	service.textSearch(request, callback);
}

function callback(results, status) {
	if (status == google.maps.places.PlacesServiceStatus.OK) {
		for (var i = 0; i < results.length; i++) {
			var place = results[i];
			addresses.push(place.formatted_address.split(','));
			var hospital = new Object({
				name: place.name,
				latitude: parseFloat(place.geometry.location.lat()),
				longitude:  parseFloat(place.geometry.location.lng()),
			});
			hospitals.push(hospital);
		}
	}
	buildTable();
}

function getHospital(lat, lng) {
	var bounds = new Object();
	bounds.northEastLat = lat + 0.001;
	bounds.northEastLon = lng + 0.001;
	bounds.southWestLat = lat - 0.001;
	bounds.southWestLon = lng - 0.001;

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
		async: false,
		success: function (data) {
			hospital =  data[0];
		}
	})
}

function buildTable() {
	var table = '<table class="table table-striped table-bordered"><thead><tr><th class="col-sm-5">Google</th><th class="col-sm-2">Actions</th><th class="col-sm-5">DB</th></tr></thead><tbody>';
	for (var i = 0; i < hospitals.length; i++) {
		var hospital1 = hospitals[i];
		getHospital(hospital1.latitude, hospital1.longitude);
		var hospital2 = hospital;
		var address = addresses[i];
		table += '<tr><td>' + hospital1.name + '<br>' + address + '</td>';

		if (hospital2 == null) {
			table += '<td class="text-center"><form method="post"><input type="hidden" name="';
			table += $('meta[name="csrf-name"]').attr('content') + '" value="';
			table += $('meta[name="csrf-token"]').attr('content') + '" />';
			table += '<input type="hidden" id="hospjs" name="hospjs" value=\'';
			table += JSON.stringify(hospital1);
			table += '\'><button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span>';
			table += '</button></form></td><td>none</td>';					
		} else {			
			table += '<td class="text-center"><button type="button" class="btn btn-default" onclick="javascript:placeMarker(\'';
			table += hospital2.latitude + '\', \'' + hospital2.longitude + '\');">';
			table += '<span class="glyphicon glyphicon-map-marker"></span></button></td><td>';
			table += hospital2.name + ' <br>';
			table += hospital2.address.street + ', ';	
			table += hospital2.address.building + ', ';	
			table += hospital2.address.city + ', ';
			table += hospital2.address.country + '</td>';
		}
		table += '</tr>';

	}
	table += '</tbody></table>';
	document.getElementById("table-out").innerHTML = table;
}

function placeMarker(lat, lng) {
	marker.setMap(null);
	var loc = new google.maps.LatLng(parseFloat(lat), parseFloat(lng));
	marker = new google.maps.Marker({
		position: loc,
		map: map,
	});
	map.setCenter(loc);
}

