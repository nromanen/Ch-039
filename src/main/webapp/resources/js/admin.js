/**
 * Created by andrew on 14.05.16.
 */

$(document).ready(function() {
    $(function(){
        $('table td:first-child').each(function (i) {
            $(this).html(i+1);
        });
    });

    //modal window for delete
    $('#deleteModal').on('show.bs.modal', function(e) {
        var Selection = $(e.relatedTarget).data('values').split(",");
        var actionPrefix = Selection[0];
        var id = Selection[1];
        var email = Selection[2];
        var status = Selection[3];
        $(this).find('#deleteButton').attr('href', actionPrefix+id+status);
        $('.debug-url').html('Are you really want to delete user <strong>' + email +' ?' + '</strong>');
    });

    //modal window for details
    $('#viewModal').on('show.bs.modal', function(e) {
        var id = $(e.relatedTarget).data('id');
        var url = 'users/view/' + id;
        var table="";
        $.get(url, function(data){
            $.each(data, function(key, val){
                    if(key=="enabled") {
                        if(val){
                            table +='<tr>' + '<td>' + key + '</td>' +
                                '<td>' + '<input data-id=' + data.id + ' value=' + '"Enabled"' +
                                ' type="button"' + ' class="btn btn-sm btn-success userEnabled"' + ' />' +
                                '</td>'+ '</tr>';
                        }else{
                            table +='<tr>' + '<td>' + key + '</td>' +
                                '<td>' + '<input data-id=' + data.id + ' value=' + '"Disabled"' +
                                ' type="button"' + ' class="btn btn-sm btn-danger userEnabled"' + ' />' +
                                '</td>'+ '</tr>';
                        }
                    }

                    if((key!="userDetails")){
                        table +='<tr>' + '<td>' + key +": "+ '</td>' +
                            '<td>' + val + '</td>'+ '</tr>';
                    }

                    if(key=="userDetails"){
                        $.each(data.userDetails, function(key, val){
                            table +='<tr>' + '<td>' + key + ": " + '</td>' +
                                '<td>' +  val + '</td>'+ '</tr>';
                        });
                    }
                }
            );
            $('.viewDetails').html(table)

            //change status on admin dashboard
            $('.userEnabled').click(function(event) {
                var id = $(this).attr("data-id");
                var status = $(this).val();
                var url = 'users/changeStatus/' + id;
                if(status=='Enabled'){
                    $.get(url);
                    $(this).val('Disabled').removeClass('btn-success').addClass('btn-danger');
                }else{
                    $.get(url);
                    $(this).val('Enabled').removeClass('btn-danger').addClass('btn-success');
                }
            });
        });
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


    //table sorter
    $(function(){
        $("#allUsers").tablesorter(
            {headers: { 0: { sorter: false},
                5: {sorter: false}}}
        );
    });


        //select search





});



