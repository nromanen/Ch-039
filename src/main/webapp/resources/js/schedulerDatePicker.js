/**
 * Created by tsapy on 23.06.2016.
 */
    function show_minical() {
        if (scheduler.isCalendarVisible())
            scheduler.destroyCalendar();
        else
            scheduler
                .renderCalendar({
                    position: "dhx_minical_icon",
                    date: scheduler._date,
                    navigation: true,
                    handler: function (date, calendar) {
                        scheduler
                            .setCurrentView(date);
                        scheduler
                            .destroyCalendar()
                    }
                });
    }
