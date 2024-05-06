import React, { useEffect } from 'react';
import { Form, Input, Select} from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { fetchDoctorDetails, updateDoctorDetails } from '../../../../api/DocInfo';
import {setMessage} from "../../../../actions/flash";

const { Option } = Select;

const DoctorList = () => {
    const [form] = Form.useForm();
    const dispatch = useDispatch();
    const userId = useSelector(state => state.auth.userId);

    useEffect(() => {
        console.log("Current userId: ", userId);
        if (userId) {
            fetchDoctorDetails(userId)
                .then(response => {
                    form.setFieldsValue(response.data);
                    dispatch({ type: 'setDoc', doc: { docId: response.data.docID } });
                })
                .catch(error => {
                    const errorMessage = error.response ? error.response.data : error.message;
                    dispatch(setMessage(errorMessage, 'error'));
                });
        } else {
            console.log("UserId is undefined");
        }
    }, [userId, dispatch]);

    const handleSubmit = (values) => {
        const updatedDoctor = { values, userId};
        updateDoctorDetails(updatedDoctor)
            .then(() => {
                dispatch(setMessage( "个人信息修改成功", 'success'));
            })
            .catch(() => {
                dispatch(setMessage( "个人信息修改失败", 'error'));
            });
    };

    return (
        <div style={{ padding: '2px' }}>
            <Form form={form} layout="vertical" onFinish={handleSubmit} >
                <Form.Item
                    label="姓名"
                    name="docName"
                    rules={[{ required: true, message: '请输入姓名' }]}
                >
                    <Input placeholder="请输入姓名" />
                </Form.Item>
                <Form.Item
                    label="科室"
                    name="department"
                    rules={[{ required: true, message: '请选择科室' }]}
                >
                    <Select placeholder="请选择科室">
                        <Option value="内科">内科</Option>
                        <Option value="外科">外科</Option>
                        <Option value="中医">中医</Option>
                        <Option value="儿科">儿科</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    label="职位"
                    name="title"
                    rules={[{ required: true, message: '请选择职位' }]}
                >
                    <Select placeholder="请选择职位">
                        <Option value="主任医师">主任医师</Option>
                        <Option value="副主任医师">副主任医师</Option>
                        <Option value="主治医师">主治医师</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    label="邮箱"
                    name="email"
                    rules={[{ required: true, type: 'email', message: '请输入有效的邮箱' }]}
                >
                    <Input placeholder="请输入邮箱" />
                </Form.Item>
                <Form.Item
                    label="电话"
                    name="phoneNumber"
                    rules={[{ required: true, message: '请输入电话号码' }]}
                >
                    <Input placeholder="请输入电话号码" />
                </Form.Item>
                <Form.Item
                    label="简介"
                    name="profile"
                    rules={[{ required: true, message: '请输入个人简介' }]}
                >
                    <Input.TextArea rows={2} placeholder="请输入个人简介" />
                </Form.Item>
            </Form>
            <button type="primary" htmlType="submit" style={{ float: 'right', backgroundColor: '#3A77A8', color: 'white', fontSize:'17px', borderRadius:'4px',borderColor:'#3A77A8'}}>
                提交
            </button>
        </div>
    );
};

export default DoctorList;