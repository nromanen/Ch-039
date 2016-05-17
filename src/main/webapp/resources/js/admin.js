/**
 * Created by andrew on 14.05.16.
 */
/*redirect button*/

$(document).ready(function() {

    //change status on admin dashboard
    $('.userEnabled').click(function(event) {

        var id = $(this).attr("data-id");
        var status = $(this).val();
        var url = '/changeStatus/' + id;
        if(status=='Enabled'){
            $.get(url);
            $(this).val('Disabled').removeClass('btn-success').addClass('btn-danger');
        }else{
            $.get(url);
            $(this).val('Enabled').removeClass('btn-danger').addClass('btn-success');
        }
    });

    //modal window for delete
    $('#deleteModal').on('show.bs.modal', function(e) {
        var Selection = $(e.relatedTarget).data('values').split(",");
        var action = Selection[0];
        var id = Selection[1];
        var email = Selection[2];
       $(this).find('#deleteButton').attr('href', action+id);
        $('.debug-url').html('Are you really want to delete user <strong>' + email +' ?' + '</strong>');
    });

    //modal window for details
    $('#viewModal').on('show.bs.modal', function(e) {
        var id = $(e.relatedTarget).data('id');
        var url = '/user/view/' + id;
        $.get(url, function(data){
            var view = data.split(',');
            var view2= '<tr>' + '<td>#ID: </td>' +'<td>' + view[0] + '</td>' +'</tr>'+
                 +'<tr>' + '<td>@Email: </td>' +'<td>' + view[1] + '</td>' +'</tr>+'
                 +'<tr>' + '<td>Gender: </td>' +'<td>' + view[2] + '</td>' +'</tr>'+
                 +'<tr>' + '<td>First Name: </td>' +'<td>' + view[3] + '</td>' +'</tr>'+
                 +'<tr>' + '<td>Last Name: </td>' +'<td>' + view[4] + '</td>' +'</tr>'+
                 +'<tr>' + '<td>Address: </td>' +'<td>' + view[5] + '</td>' +'</tr>'+
                 +'<tr>' + '<td>Phone: </td>' +'<td>' + view[6] + '</td>' +'</tr>'+
                 +'<tr>' + '<td>Details: </td>' +'<td>' + view[7] + '</td>' +'</tr>';
            $('.viewDetails').html(view2)
            /*
            $('.debug-url').html(' <strong>' + data + '</strong>');*/
        });
        /*$(this).find('#deleteButton').attr('href', action+id);*/
    });

    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    $("#menu-close").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });
    $(function() {
        $('a[href*=#]:not([href=#])').click(function() {
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


