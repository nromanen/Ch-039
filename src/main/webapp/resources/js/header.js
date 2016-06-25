$(function() {
	$(
			'a[href="#toggle-search"], .navbar-bootsnipp .bootsnipp-search .input-group-btn > .btn[type="reset"]')
			.on(
					'click',
					function(event) {
						event.preventDefault();
						$(
								'.navbar-bootsnipp .bootsnipp-search .input-group > input')
								.val('');
						$('.navbar-bootsnipp .bootsnipp-search').toggleClass(
								'open');
						$('a[href="#toggle-search"]').closest('li')
								.toggleClass('active');

						if ($('.navbar-bootsnipp .bootsnipp-search').hasClass(
								'open')) {
							/*
							 * I think .focus dosen't like css animations, set
							 * timeout to make sure input gets focus
							 */
							setTimeout(
									function() {
										$(
												'.navbar-bootsnipp .bootsnipp-search .form-control')
												.focus();
									}, 100);
						}
					});

	$(document).on(
			'keyup',
			function(event) {
				if (event.which == 27
						&& $('.navbar-bootsnipp .bootsnipp-search').hasClass(
								'open')) {
					$('a[href="#toggle-search"]').trigger('click');
				}
			});

});

$(document).ready(function(e) {
	$('.search-panel .dropdown-menu').find('a').click(function(e) {
		e.preventDefault();
		var param = $(this).attr("href").replace("#", "");
		var concept = $(this).text();
		$('.search-panel span#search_concept').text(concept);
		$('.input-group #search_param').val(param);
	});

});

function changeLang(event) {
	var url = window.location.toString();
	var lang = $(event.target).data("lang");
	if (url.indexOf("#") != -1) {
		var langIndex = url.indexOf("#");
		url = url.substring(0, langIndex);
	}
	var langRegex = /(\?|&)lang=/;

	if (url.indexOf("?lang=") != -1 || url.indexOf("&lang=") != -1) {
		var langIndex = url.search(langRegex);
		url = url.substring(0, langIndex);
	}
	if (url.indexOf("?") > -1) {
		url += "&lang=" + lang;
	} else {
		url += "?lang=" + lang;
	}
	window.location.replace(url);
}