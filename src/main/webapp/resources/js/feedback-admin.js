/**
 *
 */

$(document).ready(function(){
  var params = {
    rules : {
      criteria: {
        required : true
      },
      query:{
        required:true,
        minlength:5,
        maxlength:50
      }
    },
    messages : {
      query : $('#queryErrorMessage').text()
    },
    tooltip_options : {
      query : {
        placement : 'top',
        html : true
      }
    },
  };
  $("#filterForm").validate(params);

});
function deleteWithModal(event){
  var target = event.target;
  var path = $('#path').val();
  var id = $(target).data("id");
  $('#deleteButton').attr('href',path+"/admin/feedbacks/delete/"+id);
  $('#m_delete').modal('show');
}
