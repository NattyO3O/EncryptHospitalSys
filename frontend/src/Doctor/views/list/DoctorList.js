import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Modal, Button, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './doctortable.css'

// DoctorForm 组件：用于输入新医生信息
const DoctorForm = ({ show, handleClose, saveDoctor, doctorData }) => {
    const [doctor, setDoctor] = useState({
        docName: '', department: '', title: '', email: '', phoneNumber: '', profile: ''
    });

    useEffect(() => {
        console.log("Editable Doctor Data:", doctorData); // 查看接收到的数据
        if (doctorData) {
            setDoctor(doctorData);  // 使用从 DoctorList 传递的数据更新表单
        } else {
            // 如果没有 doctorData（即添加新医生），重置表单
            setDoctor({ docName: '', department: '', title: '', email: '', phoneNumber: '', profile: '' });
        }
    }, [doctorData]);


    const handleSubmit = (e) => {
        e.preventDefault();
        saveDoctor(doctor);
        handleClose();
    };

    const handleChange = (e) => {
        setDoctor({ ...doctor, [e.target.name]: e.target.value });
    };

    const fieldLabels = {
        docName: '姓名',
        department: '科室',
        title: '职位',
        email: '邮箱',
        phoneNumber: '电话',
        profile: '简介'
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>添加医生信息</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    {Object.entries(fieldLabels).map(([key, label]) => (
                        <Form.Group key={key}>
                            <Form.Label>{label}</Form.Label>
                            <Form.Control
                                type={key === 'email' ? 'email' : 'text'}
                                name={key}
                                value={doctor[key]}
                                onChange={handleChange}
                                required={key !== 'title' && key !== 'profile'}  // 非必填字段
                            />
                        </Form.Group>
                    ))}
                    <Button variant="primary" type="submit" style={{marginTop:'5px'}}>
                        提交
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

// DoctorTable 组件：显示医生信息列表，并提供编辑与删除功能
const DoctorTable = ({ doctors, editDoctor, deleteDoctor }) => {
    return (
        <table className="table-custom">
            <thead>
            <tr>
                <th>姓名</th>
                <th>科室</th>
                <th>职位</th>
                <th>邮箱</th>
                <th>电话</th>
                <th>简介</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            {doctors.map(doctor => (
                <tr key={doctor.docId}>
                    <td>{doctor.docName}</td>
                    <td>{doctor.department}</td>
                    <td>{doctor.title}</td>
                    <td>{doctor.email}</td>
                    <td>{doctor.phoneNumber}</td>
                    <td>{doctor.profile}</td>
                    <td>
                        <Button onClick={() => editDoctor(doctor)} className="button-custom">编辑</Button>
                        <Button onClick={() => deleteDoctor(doctor.docId)} className="button-custom">删除</Button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};



// 主组件 DoctorList
const DoctorList = () => {
    const [doctors, setDoctors] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [editableDoctor, setEditableDoctor] = useState(null);

    useEffect(() => {
        fetchDoctors();
    }, []);

    const fetchDoctors = async () => {
        const response = await axios.get('http://localhost:8080/api/doctors');
        setDoctors(response.data);
    };

    const addDoctor = async (doctor) => {
        const response = await axios.post('http://localhost:8080/api/doctors', doctor);
        setDoctors([...doctors, response.data]); // 添加新医生到列表
    };

    const handleEditDoctor = (doctor) => {
        console.log("Editing doctor:", doctor);  // 确保这里能看到完整的医生对象
        setEditableDoctor(doctor);
        setShowModal(true);
    };


    const saveDoctor = async (doctor) => {
        if (doctor.docId) {
            // 更新现有医生信息
            try {
                const response = await axios.put(`http://localhost:8080/api/doctors/${doctor.docId}`, doctor);
                setDoctors(doctors.map(d => d.docId === doctor.docId ? { ...response.data } : d)); // 更新医生数据
            } catch (error) {
                console.error('Failed to update doctor:', error);
            }
        } else {
            // 添加新医生
            try {
                const response = await axios.post('http://localhost:8080/api/doctors', doctor);
                setDoctors([...doctors, response.data]); // 添加新医生到列表
            } catch (error) {
                console.error('Failed to add doctor:', error);
            }
        }
        setShowModal(false); // 关闭模态框
    };

    const deleteDoctor = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/doctors/${id}`);
            setDoctors(doctors.filter(doctor => doctor.docId !== id)); // 更新状态，移除已删除的医生
        } catch (error) {
            console.error('Failed to delete doctor:', error);
        }
    };

    return (
        <div className="container mt-5">
            <Button onClick={() => { setEditableDoctor(null); setShowModal(true); }} style={{ marginBottom: '20px',backgroundColor:'#3A77A8',borderColor:'#3A77A8',fontSize:'15px' }}>添加</Button>
            <DoctorForm show={showModal} handleClose={() => setShowModal(false)} saveDoctor={saveDoctor} doctorData={editableDoctor} />
            <DoctorTable doctors={doctors} editDoctor={handleEditDoctor} deleteDoctor={deleteDoctor} />
        </div>
    );
};

export default DoctorList;
