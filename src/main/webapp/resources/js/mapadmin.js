var geocoder = new google.maps.Geocoder();
var marker = new google.maps.Marker();

var pathname = window.location.pathname;
var patharr = pathname.split('/');
var addition = patharr[1];
if (addition != 'HospitalSeeker') {
	addition = '';
} else {
	addition = '/' + addition;
}

if (getLocale() == 'ua') {
	$.extend( $.validator.messages, {
		required: 'Це поле необхідно заповнити.',
		remote: 'Будь ласка, введіть правильне значення.',
		email: 'Будь ласка, введіть коректну адресу електронної пошти.',
		url: 'Будь ласка, введіть коректний URL.',
		date: 'Будь ласка, введіть коректну дату.',
		dateISO: 'Будь ласка, введіть коректну дату у форматі ISO.',
		number: 'Будь ласка, введіть число.',
		digits: 'Вводите потрібно лише цифри.',
		creditcard: 'Будь ласка, введіть правильний номер кредитної карти.',
		equalTo: 'Будь ласка, введіть таке ж значення ще раз.',
		extension: 'Будь ласка, виберіть файл з правильним розширенням.',
		maxlength: $.validator.format( 'Будь ласка, введіть не більше {0} символів.' ),
		minlength: $.validator.format( 'Будь ласка, введіть не менше {0} символів.' ),
		rangelength: $.validator.format( 'Будь ласка, введіть значення довжиною від {0} до {1} символів.' ),
		range: $.validator.format( 'Будь ласка, введіть число від {0} до {1}.' ),
		max: $.validator.format( 'Будь ласка, введіть число, менше або рівно {0}.' ),
		min: $.validator.format( 'Будь ласка, введіть число, більше або рівно {0}.' )
	} );
}

function initialize() {

	mapInit('googleMap');

	searchInit('pac-input');

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
		$('#image1').attr('src', addition + '/images/hospital/' + document.getElementById('imagePath').value);
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