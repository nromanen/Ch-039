/*
$(document).ready(function(){
	$('#select-to').select2();

	$(".search-data-ajax").select2({
		ajax: {
			url: "https://api.github.com/search/repositories",
			dataType: 'json',
			delay: 250,
			data: function (params) {
				return {
					q: params.term, // search term
					page: params.page
				};
			},
			processResults: function (data, params) {
				// parse the results into the format expected by Select2
				// since we are using custom formatting functions we do not need to
				// alter the remote JSON data, except to indicate that infinite
				// scrolling can be used
				params.page = params.page || 1;

				return {
					results: data.items,
					pagination: {
						more: (params.page * 15) < data.total_count
					}
				};
			},
			cache: true
		},
		escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
		minimumInputLength: 1,
		templateResult: formatRepo, 
		templateSelection: formatRepoSelection 
	});
	function formatRepo (repo) {
		if (repo.loading) return repo.text;

		var markup = "<div class='select2-result-repository  clearfix'>" +
		"<div class='select2-result-repository__avatar'><img " +
		"style='height:75px;width=75px;' class='img-responsive' src='" + repo.owner.avatar_url + "' /></div>" +
		"<div class='select2-result-repository__meta'>" +
		"<div class='select2-result-repository__title'>" + repo.full_name + "</div>";

		if (repo.description) {
			markup += "<div class='select2-result-repository__description'>" + repo.description + "</div>";
		} 

		return markup;
	}
	function formatRepoSelection (repo) {
		return repo.full_name || repo.text;
	}
});*/
