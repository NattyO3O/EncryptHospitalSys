// 文件路径：/PatientRegister.js

import React, {useEffect, useState} from 'react';
import { Table, Button, Modal, DatePicker, Select } from 'antd';
import { PlusCircleOutlined } from '@ant-design/icons';
import axios from "axios";
import {setMessage} from "../../../../actions/flash";
import {useDispatch, useSelector} from "react-redux";
import {fetchPatientDetails} from "../../../../api/PatInfo";

const { Option } = Select;

const PatientRegister = () => {
    const [expandedKeys, setExpandedKeys] = useState({});
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedDate, setSelectedDate] = useState(null);
    const [selectedTime, setSelectedTime] = useState(null);
    const [selectedDepartment, setSelectedDepartment] = useState('');
    const [doctors, setDoctors] = useState([]);  // 添加这行来管理医生数据状态
    const dispatch = useDispatch();
    const userId = useSelector(state => state.auth.userId);
    const patientId = useSelector(state => state.patient.pat.patientID);

    useEffect(() => {
        console.log("Current userId: ", userId);
        if (userId) {
            fetchPatientDetails(userId)
                .then(response => {
                    dispatch({ type: 'setPatient', pat: { patientID: response.data.patientID } });

                })
                .catch(error => {
                    const errorMessage = error.response ? error.response.data : error.message;
                    dispatch(setMessage(errorMessage, 'error'));
                });
        } else {
            console.log("UserId is undefined");
        }
    }, [userId, dispatch]);

    const toggleExpand = (key) => {
        setExpandedKeys((prev) => ({ ...prev, [key]: !prev[key] }));
    };

    // 表格数据
    const departments = [
        {
            key: 'internal',
            name: '内科',
            subDepartments: [
                { key: 'cardiology', name: '心脏内科', type: '门诊' },
                { key: 'respiratory', name: '呼吸病', type: '门诊' },
                { key: 'general', name: '普通内科', type: '门诊' },
            ],
        },
        {
            key: 'surgery',
            name: '外科',
            subDepartments: [
                { key: 'dentistry', name: '口腔科', type: '门诊' },
                { key: 'dermatology', name: '皮肤科', type: '门诊' },
                { key: 'ophthalmology', name: '眼科', type: '门诊' },
            ],
        },
        {
            key: 'tcm',
            name: '中医',
            subDepartments: [
                { key: 'tcm-respiratory', name: '中医呼吸科', type: '门诊' },
                { key: 'acupuncture', name: '针灸科', type: '门诊' },
                { key: 'massage', name: '推拿科', type: '门诊' },
            ],
        },
        {
            key: 'pediatrics',
            name: '儿科',
            subDepartments: [
                { key: 'endocrinology', name: '小儿内分泌', type: '门诊' },
                { key: 'pediatric-ophthalmology', name: '儿童眼科', type: '门诊' },
            ],
        },
    ];

    const handleBooking = (subDepartment) => {
        if (subDepartment && subDepartment.name) {  // 检查 subDepartment 对象及其 name 属性是否存在
            setSelectedDepartment(subDepartment.name);
            fetchDoctorsBySubDepartment(subDepartment.name);
            setIsModalVisible(true);
        } else {
            console.error('subDepartment is not defined or missing the name property');
        }
    };

    const fetchDoctorsBySubDepartment = async (subDepartment) => {
        try {
            // 使用 axios 发送 GET 请求
            const response = await axios.get(`https://localhost:8443/api/appointments/doctors/${subDepartment}`);
            if (response.status === 200) {
                const doctorsData = response.data;
                setDoctors(doctorsData);
                console.log("Fetched doctors:", doctorsData);  // 查看返回的医生数据
            } else {
                throw new Error('Failed to fetch doctors');
            }
        } catch (error) {
            console.error("Error fetching doctors:", error);
            setDoctors(undefined);  // 在出错时清空医生列表
        }
    };

    const handleOk = (record) => {
        console.log("患者id"+patientId);
        if (record && patientId) {
            const appointment = {
                DocID: record.docID,
                PatientId: patientId,
                Date: selectedDate,
                Time: selectedTime,
                Outpatient: record.department,
                State: '已预约'
            };

            // 发送预约信息到后端
            axios.post('https://localhost:8443/api/appointments/create', appointment)
                .then(response => {
                    console.log('预约成功:', response.data);
                    setIsModalVisible(false);  // 关闭模态框
                })
                .catch(error => {
                    console.error('预约失败:', error);
                });
        } else {
            console.error('Missing doctor details or patient is not logged in.');
        }
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    return (
        <div style={{ width: '100%', overflowY: 'auto', maxHeight: '600px' }}>
            <Table
                bordered
                columns={[
                    { title: '科室', dataIndex: 'department', align: 'center' },
                    { title: '类型', dataIndex: 'type', align: 'center' },
                    { title: '操作', dataIndex: 'action', align: 'center' }
                ]}
                dataSource={departments.flatMap((dept) => {
                    const mainRow = {
                        key: dept.key,
                        department: (
                            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                                <Button
                                    type="link"
                                    onClick={() => toggleExpand(dept.key)}
                                    icon={<PlusCircleOutlined />}
                                    style={{ marginRight: 8, padding: 0, border: 'none', boxShadow: 'none', outline: 'none' }}
                                />
                                <span>{dept.name}</span>
                            </div>
                        ),
                        type: '科室',
                        action: expandedKeys[dept.key] ? null : null
                    };

                    const subRows = expandedKeys[dept.key]
                        ? dept.subDepartments.map((sub) => ({
                            key: `${dept.key}-${sub.key}`,
                            department: sub.name,
                            type: sub.type,
                            action: <Button type="primary" style={{ backgroundColor: "#3A77A8" }} onClick={() => handleBooking(sub)}>挂号</Button>
                        }))
                        : [];

                    return [mainRow, ...subRows];
                })}
                pagination={false}
                scroll={{ y: 'calc(100vh - 200px)' }}
            />
            <Modal title="预约挂号" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel} width={800}>
                <Table
                    dataSource={doctors}
                    bordered
                    columns={[
                        { title: 'ID', dataIndex: 'docID', key: 'docID', align: 'center', width: '25%' },
                        { title: '医生', dataIndex: 'docName', key: 'docName', align: 'center', width: '25%' },
                        { title: '科室', dataIndex: 'department', key: 'department', align: 'center', width: '25%' },
                        { title: '日期',
                            key: 'date',
                            render: (_, record) => (
                                <DatePicker onChange={(date, dateString) => setSelectedDate(dateString)} />
                            )
                        },
                        { title: '时段',
                            key: 'time',
                            render: (_, record) => (
                                <Select defaultValue="选择时段" style={{ width: 120 }} onChange={setSelectedTime}>
                                    <Option value="上午">上午</Option>
                                    <Option value="下午">下午</Option>
                                </Select>
                            )
                        },
                        { title: '操作',
                            key: 'action',
                            render: (_, record) => (
                                <Button type="primary" onClick={() => handleOk(record)}>提交</Button>
                            )
                        },
                    ]}
                    pagination={false}
                    style={{ width: '100%' }}
                    rowKey="id"
                />
            </Modal>
        </div>
    );
};

export default PatientRegister;
