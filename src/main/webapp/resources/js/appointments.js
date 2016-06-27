/**
 * Created by igortsapyak on 08.06.16.
 */

//TODO: refactor to normal view
$(document).ready(function () {
    var principal = $('#principal').text();
    var format = scheduler.date.date_to_str("%H:%i");

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
    var tex_local_from = getMessage('workscheduler.modal.appointment.time.from');
    var tex_local_to = getMessage('workscheduler.modal.appointment.time.to');
    $('#myModal').modal('show');
    ev = scheduler.getEvent(id);
    scheduler.startLightbox(id, html("myModal"));
    $('#date').text(new Date(ev.start_date).toLocaleDateString() + ' ' + tex_local_from + ' '+
        new Date(ev.start_date).toLocaleTimeString().replace(':00', '') + ' ' + tex_local_to + ' ' +
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
    var principal = $('#principal').text();
    var succesMassegeStart = getMessage('myappointments.modal.appointment.cancel.success.begin');
    var succesMassegeEnd = getMessage('myappointments.modal.appointment.cancel.success.end');
    cancelAppointment();
    startModal();
    var reason = $('#cancelReason').val();
    sendMassage(reason, ev.id, principal);
    setTimeout(delete_event, 10000);
    $('#cancelMassageText').text(succesMassegeStart +' ' + ev.text.split('-')[0] + ' ' + succesMassegeEnd);
}
            
