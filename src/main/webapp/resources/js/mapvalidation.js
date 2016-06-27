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
	bounds.northEastLat = lat + 0.002;
	bounds.northEastLon = lng + 0.002;
	bounds.southWestLat = lat - 0.002;
	bounds.southWestLon = lng - 0.002;

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
	var tooltipLocate = getMessage('admin.hospital.poi.table.tooltip.locate');
	var tooltipAdd = getMessage('admin.hospital.poi.table.tooltip.add');
	var csrfName = $('meta[name="csrf-name"]').attr('content');
	var csrfToken = $('meta[name="csrf-token"]').attr('content');
	var table = document.getElementById('table-out');
	for (var i = 0; i < hospitals.length; i++) {
		var hospital1 = hospitals[i];
		var address = addresses[i];
		getHospital(hospital1.latitude, hospital1.longitude);
		var hospital2 = hospital;
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		row.insertCell(0).innerHTML=hospital1.name + '<br>' + address;		
		if (hospital2 == null) {
			var cell = row.insertCell(1);
			cell.setAttribute('class', 'text-center');
			var form = document.createElement('form');
			form.setAttribute('method', 'post');
			var inputToken = document.createElement('input');
			inputToken.setAttribute('type', 'hidden');
			inputToken.setAttribute('name', csrfName);
			inputToken.setAttribute('value', csrfToken);
			var inputData = document.createElement('input');
			inputData.setAttribute('type', 'hidden');
			inputData.setAttribute('id', 'hospjs');
			inputData.setAttribute('name', 'hospjs');
			inputData.setAttribute('value', JSON.stringify(hospital1));
			var button = document.createElement('button');
			button.setAttribute('type', 'submit');
			button.setAttribute('class', 'btn btn-default');
			var span = document.createElement('span');
			span.setAttribute('title', tooltipAdd);
			span.setAttribute('class', 'glyphicon glyphicon-plus');
			form.appendChild(inputToken);
			form.appendChild(inputData);
			button.appendChild(span);
			form.appendChild(button);				
			cell.appendChild(form);
			row.insertCell(2).innerHTML='none'
		} else {
			var cell = row.insertCell(1);
			cell.setAttribute('class', 'text-center');
			var button = document.createElement('button');
			button.setAttribute('type', 'button');
			button.setAttribute('class', 'btn btn-default');
			button.setAttribute('onclick', 'placeMarker(' + hospital2.latitude + ', ' + hospital2.longitude + ')');
			var span = document.createElement('span');
			span.setAttribute('title', tooltipLocate);
			span.setAttribute('class', 'glyphicon glyphicon-map-marker');
			button.appendChild(span);
			cell.appendChild(button);
			row.insertCell(2).innerHTML= hospital2.name + ' <br>' + hospital2.address.street + ', '+ hospital2.address.building + 
			', ' + hospital2.address.city + ', ' + hospital2.address.country;
		}
	}
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

