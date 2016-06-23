/**
 * Created by igortsapyak on 08.06.16.
 */
$(document).ready(function () {
    var principal = $('#principal').text();
    var format = scheduler.date.date_to_str("%H:%i");
    var step = 20;
    scheduler.config.hour_size_px = (60 / step) * 44;
    scheduler.config.time_step = step;
    scheduler.attachEvent("onBeforeDrag", function () {
        return false;
    });
    scheduler.attachEvent("onClick", function () {
        return false;
    });
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
    scheduler.load('getAppointmentsByPatient?patient=' + principal, 'json');
    var dp = new dataProcessor("supplyAppointment?id=" + 300 + "&principal=" + principal);
    dp.init(scheduler);
});

var html = function (id) {
    return document.getElementById(id);
};

var ev;

scheduler.showLightbox = function (id) {
    $('#myModal').modal('show');
    ev = scheduler.getEvent(id);
    scheduler.startLightbox(id, html("myModal"));
    $('#date').text(new Date(ev.start_date).toLocaleDateString() + ' from '
        + new Date(ev.start_date).toLocaleTimeString().replace(':00', '') + ' to ' +
        new Date(ev.end_date).toLocaleTimeString().replace(':00', ''));
    html("description").value = ev.text;
    var nameAndDescription = ev.text.split("-");
    $('#patientName').text(nameAndDescription[0]);
    $('#theReasonForVisit').text(nameAndDescription[1]);
    $('#cancelAppointmentHeader').text('Cancel appointment of ' + ' ' + nameAndDescription[0]);

};

function onCancelAppointment() {
    $('#cancelAppointmentModal').modal('hide');
}

function cancelAppointment() {
    changeModalCont()
}

function changeModalCont() {
    setTimeout(closeFirst, 50);
    setTimeout(openSecond, 600);
}

function closeFirst() {
    $('#modalBodyFirst').slideToggle('slow');
    $('#modalHeaderWithDate').slideToggle('slow');
    $('#modalHeaderForCancel').slideToggle('slow');
}

function openSecond() {
    $('#modalBodySecond').slideToggle('slow');
    $('#okBtn').slideToggle(10);
    $('#batOne').slideToggle('slow');
    $('#canBtn').slideToggle('slow');
}

function startModal() {
    setTimeout(changeModalContentFirstStep, 500);
    setTimeout(changeModalContentSecondStep, 4000);
}

function changeModalContentFirstStep() {
    $('#modalBodySecond').attr('hidden', 'hidden');
    $('#modalBodyFirst').slideToggle(500);
    $('#modal-header').slideToggle(500);
    $('#modal-footer').slideToggle(500);
    $('#modalBodySuccess').slideToggle(500);
}

function changeModalContentSecondStep() {
    $('#myModal').modal('hide');
    changeModalContentFirstStep();
}

$(document).keyup(function (e) {
    if (e.keyCode == 27) { // escape key maps to keycode `27`
        close_form();
    }
});

function save_form() {
    blockAppointmensAdd();
    var ev = scheduler.getEvent(scheduler.getState().lightbox_id);
    scheduler.endLightbox(true, html("my_form"));
}

function close_form() {
    if ($("#modalHeaderForCancel").is(':visible')) {
        cancelAppointment()
    }
    scheduler.endLightbox(false, html("my_form"));
}

function delete_event() {
    var event_id = scheduler.getState().lightbox_id;
    scheduler.endLightbox(false, html("cancelAppointmentModal"));
    scheduler.deleteEvent(event_id);
}

function blockAppointmensAdd() {
    scheduler.config.readonly = true;
}

function dismissMyModal(event) {
    if ($(event.target).is('#myModal')) {
        close_form();
    }
}

function onCancelAppointment() {
    cancelAppointment();
    startModal()
    var reason = $('#cancelReason').val();
    delete_event();
    console.log(reason);
    $('#cancelMassageText').text('Appointment with doctor ' + ev.text.split('-')[0] + ' was canceled successfully!');
}
            
