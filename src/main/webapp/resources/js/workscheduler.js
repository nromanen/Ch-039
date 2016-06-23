
$(document).ready(function () {
    var principal = $('#principal').text();
    var begin;
    var end;
    var dayOfAppointment;
    var schedulerConfig;

    $.ajax({
        type: "GET",
        async: false,
        url: "getWorkSchedulerByPrincipal?doctor=" + principal,
        datatype: "json",
        contentType: "application/json",
        mimeType: "application/json",
        success: function (data) {
            schedulerConfig=data[data.length-1];
            data.splice(data.length-1,1);
            data.forEach(function (item, i) {
                var workDay = data[i].start_date.substring(0, 10);
                var hourOne = data[i].start_date.substring(11, 13);
                var hourLast = data[i].end_date.substring(11, 13);
                if(new Date().getDate()<=new Date(workDay).getDate()) {
                    scheduler.blockTime(new Date(workDay), [0, hourOne * 60, hourLast * 60,
                    24 * 60]);
                console.log(i + ": " + item.start_date + " " + item.end_date)
            }
            });
        }
    });

    var dayOfWeek = new Date().getDay()-1;
    var day = new Date().getDate() - 1;
    var month = new Date().getMonth();
    var year = new Date().getFullYear();
    var hour = new Date().getHours() + 1;
    var step = schedulerConfig.appSize.substring(0,2);
    var format = scheduler.date.date_to_str("%H:%i");
    scheduler.config.hour_size_px = (60 / step) * 44;
    scheduler.config.time_step = step;

    while (day >= 1) {
        scheduler.blockTime(new Date(year, month, day), "fullday");
        day--;
    }
    
    while (dayOfWeek >= 1){
        scheduler.blockTime(new Date(year, month, day), "fullday");
        dayOfWeek--;
        day--;
    }
    scheduler.blockTime(new Date(), [0 * 60, hour * 60]);
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.details_on_dblclick = true;
    scheduler.config.details_on_create = true;
    switch (schedulerConfig.weekSize) {
        case '5' :
        {
            scheduler.ignore_week = function (date) {
                if (date.getDay() == 6 || date.getDay() == 0)
                    return true;
            };
        }break;
        case '6':{
            scheduler.ignore_week = function (date) {
                if (date.getDay() == 6)
                    return true;
            };
        }break;

    }
    
    scheduler.attachEvent("onBeforeDrag",function(){return false;})
    scheduler.attachEvent("onClick",function(){return false;})
    scheduler.config.details_on_dblclick = true;
    scheduler.config.dblclick_create = false;
    scheduler.config.first_hour = schedulerConfig.dayStart;
    scheduler.config.last_hour = schedulerConfig.dayEnd;
    scheduler.config.limit_time_select = true;
    scheduler.init('scheduler_here', null, "week");
    scheduler.load('getAppointmentsByDoctor?doctor='+principal,'json');
    var dp = new dataProcessor("supplyAppointment?id=" + 300 + "&principal="+principal);
    dp.init(scheduler);
});
