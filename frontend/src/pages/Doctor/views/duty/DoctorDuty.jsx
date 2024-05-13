import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';

const localizer = momentLocalizer(moment);

const DoctorDuty = () => {
    const [events, setEvents] = useState([]);
    const dispatch = useDispatch();
    const userId = useSelector(state => state.auth.userId);

    useEffect(() => {
        console.log("Current userId: ", userId);

        const fetchDoctorDetails = async (userId) => {
            try {
                const response = await axios.get(`https://localhost:8443/api/doctor/details/${userId}`);
                dispatch({ type: 'setDoc', doc: { docId: response.data.docID } });
                return response.data.docID;  // 返回医生ID供后续使用
            } catch (error) {
                console.error('Error fetching doctor details:', error);  // 使用console.error替代setMessage
                return null;  // 在错误情况下返回null
            }
        };

        const fetchEvents = async (docId) => {
            try {
                const res = await axios.get(`https://localhost:8443/api/duty/duties/${docId}`);
                const fetchedEvents = res.data.map(appointment => [
                    {
                        title: `上午预约人数: ${appointment.morningCount}`,
                        start: new Date(appointment.date),
                        end: new Date(appointment.date),
                        allDay: true
                    },
                    {
                        title: `下午预约人数: ${appointment.afternoonCount}`,
                        start: new Date(appointment.date),
                        end: new Date(appointment.date),
                        allDay: true
                    }
                ]).flat();
                setEvents(fetchedEvents);
            } catch (error) {
                console.error('Error fetching events:', error);  // 使用console.error替代setMessage
            }
        };

        if (userId) {
            fetchDoctorDetails(userId).then(docId => {
                if (docId) {
                    fetchEvents(docId);
                }
            });
        } else {
            console.log("UserId is undefined");
        }
    }, [userId, dispatch]);

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
};

export default DoctorDuty;
