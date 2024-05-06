import React from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';

const localizer = momentLocalizer(moment); // 使用 moment 作为本地化工具

const DoctorDuty = () => {
    const events = [
        {
            title: 'Morning Shift',
            start: moment().toDate(),
            end: moment().add(2, 'hours').toDate(),
            allDay: false
        },
        {
            title: 'Afternoon Shift',
            start: moment().add(3, 'hours').toDate(),
            end: moment().add(5, 'hours').toDate(),
            allDay: false
        }
    ];

    return (
        <div style={{ height: '500px' }}>
            <Calendar
                localizer={localizer}
                events={events}
                startAccessor="start"
                endAccessor="end"
                style={{ height: 500 }}
            />
        </div>
    );
}

export default DoctorDuty;
