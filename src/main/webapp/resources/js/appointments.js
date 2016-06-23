/**
 * Created by igortsapyak on 08.06.16.
 */
$(document).ready(function () {

    var principal = $('#principal').text();

    var format = scheduler.date.date_to_str("%H:%i");

    var step = 20;

    scheduler.config.hour_size_px = (60 / step) * 44;

    scheduler.config.time_step = step;


    scheduler.attachEvent("onBeforeDrag",function(){return false;})
    scheduler.attachEvent("onClick",function(){return false;})
    scheduler.config.details_on_dblclick = true;
    scheduler.config.dblclick_create = false;

    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.details_on_dblclick = true;
    scheduler.config.details_on_create = true;
    scheduler.ignore_week = function (date) {
        if (date.getDay() == 6 || date.getDay() == 0)
            return true;
    };

    scheduler.config.first_hour = 7;
    scheduler.config.last_hour = 21;
    scheduler.config.limit_time_select = true;
    scheduler.init('scheduler_here', null, "week");
    scheduler.load('getAppointmentsByPatient?patient='+principal,'json');
    var dp = new dataProcessor("supplyAppointment?id=" + 300 + "&principal="+principal);
    dp.init(scheduler);

});
