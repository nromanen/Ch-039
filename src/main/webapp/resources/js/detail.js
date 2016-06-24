
function submitForm(){

    var str = $("#detailForm").serialize();
    $.ajax({
        type:"post",
        data:str,
        url:"/save/detail",
        async: false,
        success: function(response){
            $('#myModal').html(response);
            $('#birthDate').datepicker({
                format: 'yyyy-mm-dd',
                endDate: "0d"
            });

            $('#phone').bfhphone({
                format: '+38 (ddd) ddd-dddd'

            });
        }
    });
};

function showPage(){
    $.ajax({ type: 'GET', url: '/user/detail', success: function(response) {
        $('#myModal').html(response);

    }})};

function showProfile(userId){
    $.ajax({ type: 'GET', url: '/user/profile?userId='+userId, success: function(response) {
        $('#profileModal').html(response);
    }})};

function editProfile(){
    $.ajax({ type: 'GET', url: '/user/detail?edit=true', success: function(response){
        $('#myModal').html(response);

        $('#birthDate').datepicker({
            format: 'yyyy-mm-dd',
            endDate: "0d"
        });

        $('#phone').bfhphone({
            format: '+38 (ddd) ddd-dddd'
        });

    }})

};