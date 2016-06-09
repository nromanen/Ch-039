/**
 * Created by andrew on 14.05.16.
 */

$(document).ready(function () {
    //back to the top
    $('section').prepend('<a href="#top" class="back-to-top">Back to Top</a>');
    $(window).scroll(function () {
        if ($(window).scrollTop() > 0) {
            $('a.back-to-top').fadeIn('slow');
        } else {
            $('a.back-to-top').fadeOut('slow');
        }
    });

    $('a.back-to-top').click(function (event) {
        event.preventDefault();
        $('html, body').animate({
            scrollTop: 0
        }, "slow");
    });

    //select item per page
    var $select = $('#userPerPage');
    $select.change(function () {
        var quantityUsers = this.options[this.selectedIndex].value;
        var setUrl = '/admin/users/setItemsPerPage/' + quantityUsers;
        $.get(setUrl, function (data) {
            window.location.reload();
        });
    });

    //select searchBy
    var $searchBy = $('#searchBy');
    var $searchInput = $('#search');
    var searchByStorage = sessionStorage.getItem('searchBy');
    var inputSearchStorage = sessionStorage.getItem('inputSearch');
    var inputSearchValueStorage = sessionStorage.getItem('inputSearchValue');

    //save input values in search input
    $('#searchButton').click(function () {
        sessionStorage.setItem('inputSearchValue', $searchInput.val());
    });
    $searchInput.val(inputSearchValueStorage);

    // dynamic change DTO filed  select - input
    if (searchByStorage) {
        $searchBy.val(searchByStorage).attr('selected', 'selected');
        $searchInput.attr('name', inputSearchStorage);
    }

    $searchBy.change(function () {
        var searchField = this.options[this.selectedIndex];
        var searchValue = searchField.value;
        var searchText = searchField.text;
        var selectedValue = $searchBy.find(':selected').val();
        $searchInput.attr("name", searchValue).attr("placeholder", searchText);
        sessionStorage.setItem('searchBy', selectedValue);
        sessionStorage.setItem('inputSearch', selectedValue);
    });

    //reset all search field
    $('#clearButton').click(function (event) {
        var pageSize = 10;
        var url = '/admin/users/setItemsPerPage/' + pageSize;
        $.get(url, function (data) {
        });
        sessionStorage.clear();
        $('#pref-roleby').val('');
        $searchInput.val('');
        window.location.reload();
    });

    //alert windows (success or error after editing user)
    $('.messageAfterEdit').delay(5000).fadeOut("slow");

    //modal window for delete
    $('#deleteModal').on('show.bs.modal', function (e) {
        $('.content').addClass('blur');
        var Selection = $(e.relatedTarget).data('values').split(",");
        var actionPrefix = Selection[0];
        var id = Selection[1];
        var email = Selection[2];
        var status = Selection[3];
        $(this).find('#deleteButton').attr('href', actionPrefix + id + status);
        $('.debug-url').html('Are you really want to delete user <strong>' + email + ' ?' + '</strong>');
    });

    $('#deleteModal').on('hide.bs.modal', function (e) {
        $('.content').removeClass('blur');
    });

    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    $("#menu-close").click(function (e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    $(function () {
        $('a[href*=#]:not([href=#])').click(function () {
            if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') || location.hostname == this.hostname) {
                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                if (target.length) {
                    $('html,body').animate({
                        scrollTop: target.offset().top
                    }, 1000);
                    return false;
                }
            }
        });
    });
});
