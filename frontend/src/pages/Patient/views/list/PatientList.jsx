import React, {useEffect} from 'react';
import { Form, Input, Button, Select } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import {setMessage} from "../../../../actions/flash";
import {fetchPatientDetails, updatePatientDetails} from "../../../../api/PatInfo";

const { Option } = Select;

const PatientList = () => {
    const [form] = Form.useForm();
    const dispatch = useDispatch();
    const userId = useSelector(state => state.auth.userId);

    useEffect(() => {
        console.log("Current userId: ", userId);
        if (userId) {
            fetchPatientDetails(userId)
                .then(response => {
                    form.setFieldsValue(response.data);
                    dispatch({ type: 'setPat', pat: { patId: response.data.patId } });
                })
                .catch(error => {
                    const errorMessage = error.response ? error.response.data : error.message;
                    dispatch(setMessage(errorMessage, 'error'));
                });
        } else {
            console.log("UserId is undefined");
        }
    }, [userId, dispatch, form]);

    const handleSubmit = (values) => {
        const updatedPatient ={values, userId};
        updatePatientDetails(updatedPatient)
            .then(() => {
                dispatch(setMessage( "个人信息修改成功", 'success'));
            })
            .catch(() => {
                dispatch(setMessage( "个人信息修改失败", 'error'));
            });
    };

    return (
        <div style={{ padding: '2px' }}>
            <Form form={form} layout="vertical" onFinish={handleSubmit}>
                <Form.Item
                    label="姓名"
                    name="patientName"
                    rules={[{ required: true, message: '请输入姓名' }]}
                >
                    <Input placeholder="请输入姓名" />
                </Form.Item>
                <Form.Item
                    label="性别"
                    name="sex"
                    rules={[{ required: true, message: '请选择性别' }]}
                >
                    <Select placeholder="请选择性别">
                        <Option value="Male">男</Option>
                        <Option value="Female">女</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    label="年龄"
                    name="age"
                    rules={[{ required: true, message: '请输入年龄' }]}
                >
                    <Input placeholder="请输入年龄" />
                </Form.Item>
                <Form.Item
                    label="电话"
                    name="phoneNumber"
                    rules={[{ required: true, message: '请输入电话号码' }]}
                >
                    <Input placeholder="请输入电话号码" />
                </Form.Item>
                <Form.Item
                    label="身份证"
                    name="nationalID"
                    rules={[{ required: true, message: '请输入身份证号码' }]}
                >
                    <Input placeholder="请输入身份证号码" />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" style={{backgroundColor:"#3A77A8"}}>
                        提交
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default PatientList;