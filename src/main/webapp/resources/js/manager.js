
var did = document.getElementById("did").textContent;
var workScheduler;
var appSize;
var dayStart;
var dayEnd;
var weekSize;
$.ajax({
    type: "GET",
    async: false,
    url: "getWorkScheduler?id=" + did,
    datatype: "json",
    contentType: "application/json",
    mimeType: "application/json",
    success: function (data) {
        appSize = data[data.length - 1].appSize;
        weekSize = data[data.length - 1].weekSize;
        dayStart = data[data.length - 1].dayStart;
        dayEnd = data[data.length - 1].dayEnd;
        data.splice(data.length - 1, 1);
        workScheduler = data;

    }
});

$("#workWeekSize option").each(function (item, i) {
    if (weekSize.substring(0, 1) == i.value) {
        $("#workWeekSize").val(i.value);
    }

});

$("#workDayBeginAt option").each(function (item, i) {
    if (dayStart == i.value) {
        $("#workDayBeginAt").val(i.value);
    }

});

$("#workDayEndAt option").each(function (item, i) {
    if (dayEnd == i.value) {
        $("#workDayEndAt").val(i.value);
    }

});

$("#appointmentTime option").each(function (item, i) {
    if (appSize.substring(0, 2) == i.value.substring(0, 2)) {
        $("#appointmentTime").val(i.value);
    }

});

scheduler.config.time_step = 60;
scheduler.config.xml_date = "%Y-%m-%d %H:%i";
scheduler.config.limit_time_select = true;
scheduler.init('scheduler_here', null, "week");
scheduler.parse(workScheduler, "json");
function save() {

    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

    var workScheduler = JSON.parse(scheduler.toJSON());

    var workSchedulerConfig = new Object();
    workSchedulerConfig.weekSize = $("#workWeekSize").val();
    workSchedulerConfig.dayStart = $("#workDayBeginAt").val();
    workSchedulerConfig.dayEnd = $("#workDayEndAt").val();
    workSchedulerConfig.appSize = $("#appointmentTime").val();
    workScheduler.push(workSchedulerConfig);

    $.ajax({
        type: "POST",
        async: false,
        url: "supplyWorkScheduler?doctorId=" + did,
        data: JSON.stringify(workScheduler),
        datatype: "json",
        contentType: 'application/json',
        mimeType: 'application/json'
    })
}