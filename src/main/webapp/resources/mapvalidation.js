var initialLocation;
var chernivci = new google.maps.LatLng(48.2872634, 25.938834);
var hospitals = [];
var hospital;
var marker = new google.maps.Marker();
var map;

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
			var hospital = new Object({
				name: place.name,
				latitude: parseFloat(place.geometry.location.lat()),
				longitude:  parseFloat(place.geometry.location.lng()),
				address: place.formatted_address
			});
			hospitals.push(hospital);
		}
	}
	buildTable();
}

function getHospital(lat, lng) {
	var bounds = new Object();
	bounds.northEastLat = lat + 0.002;
	bounds.northEastLon = lng + 0.002;
	bounds.southWestLat = lat - 0.002;
	bounds.southWestLon = lng - 0.002;
	
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
	var table = '';
	for (var i = 0; i < hospitals.length; i++) {
		var hospital1 = hospitals[i];
		getHospital(hospital1.latitude, hospital1.longitude);
		var hospital2 = hospital;
		table += '<tr><td>';
		table += '<p>' + hospital1.name + '</p>';
		table += '<p>' + hospital1.address + '</p>';		
		table += '</td>';

		if (hospital2 == null) {
			table += '<td class="text-center">';			
			table += '<form method="post">';
			table += '<input type="hidden" name="'
			table += $('meta[name="csrf-name"]').attr('content');
			table += '" value="';
			table += $('meta[name="csrf-token"]').attr('content');
			table += '" />';
			table += '<input type="hidden" id="hospjs" name="hospjs" value=\'';
			table += JSON.stringify(hospital1);
			table += '\'>';
			table += '<button type="submit" class="btn btn-default">';
			table += '<span class="glyphicon glyphicon-plus"></span>';
			table += '</button></form>';		
			table += '</td>';
			table += '<td>';
			table += 'none';
			table += '</td>';					
		} else {			
			table += '<td class="text-center">';
			table += '<button type="button" class="btn btn-default" onclick="javascript:placeMarker(\'';
			table += hospital2.latitude + '\', \'' + hospital2.longitude + '\');">';
			table += '<span class="glyphicon glyphicon-map-marker"></span>';
			table += '</button>';
			
			table += '</td>';

			table += '<td>';
			table += '<p>' + hospital2.name + ' </p>';
			table += '<p>' + hospital2.address + ' </p>';		
			table += '</td>';
		}
		table += '</tr>';
		
	}
	document.getElementById("table-out").innerHTML = '';
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

