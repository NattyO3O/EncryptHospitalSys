import React, { Component } from 'react';
import {Table, Space, Modal, Form, Input, message, Button} from 'antd';
import * as api from '../../../../api/manage';

class RegDoc extends Component {
    constructor(props) {
        super(props);
        this.formRef = React.createRef();  // 创建表单引用
        this.state = {
            dataSource: [],
            loading: true,
            isModalVisible: false,
            modalType: '', // 'add' or 'edit'
            editingRecord: null,
        };
    }

    componentDidMount() {
        this.loadDoctors();
    }

    // Fetch all doctors from the backend and map them correctly
    loadDoctors = async () => {
        try {
            const response = await api.fetchDoctors();
            // Transform the data to fit the Table component by mapping "docID" to "key"
            const mappedData = response.data.map(doctor => ({
                ...doctor,
                key: doctor.docID
            }));
            this.setState({ dataSource: mappedData, loading: false });
        } catch (error) {
            message.error('加载医生数据失败');
            this.setState({ loading: false });
        }
    };

    handleModalShow = (type, record = null) => {
        this.setState({ isModalVisible: true, modalType: type, editingRecord: record }, () => {
            // 当状态更新后并且是编辑模式，设置表单值
            if (type === 'edit' && record) {
                this.formRef.current.setFieldsValue({
                    docName: record.docName,
                    department: record.department,
                    title: record.title,
                    email: record.email,
                    phoneNumber: record.phoneNumber,
                    profile: record.profile
                });
            } else {
                // 清除表单值
                this.formRef.current.resetFields();
            }
        });
    };

    handleModalCancel = () => {
        this.setState({ isModalVisible: false, editingRecord: null });
    };

    handleDelete = async (doctorId) => {
        this.setState({ loading: true });
        try {
            await api.deleteDoctor(doctorId);
            message.success('删除医生成功');
            this.loadDoctors(); // Refresh data
        } catch (error) {
            message.error('删除医生失败');
            this.setState({ loading: false });
        }
    };

    // Handle submission of edited or new data
    handleSubmit = async (values) => {
        const { modalType, editingRecord } = this.state;
        this.setState({ loading: true });

        try {
            if (modalType === 'edit') {
                // Include the doctor's ID to identify the record in the backend
                const payload = { ...values, docID: editingRecord.key };
                await api.updateDoctor(editingRecord.key, payload);
                message.success('修改医生信息成功');
            } else {
                // Adding new doctor remains the same
                await api.addDoctor({ username: values.username, password: values.password }, { docName: values.docName });
                message.success('添加医生成功');
            }
            this.handleModalCancel();
            this.loadDoctors(); // Refresh data
        } catch (error) {
            message.error(modalType === 'add' ? '添加医生失败' : '修改医生信息失败');
            this.setState({ loading: false });
        }
    };

    renderModalContent = () => {
        return (
            <Form layout="vertical" onFinish={this.handleSubmit} ref={this.formRef}>
                {this.state.modalType === 'add' && (
                    <>
                        <Form.Item name="username" label="用户名" rules={[{ required: true, message: '请输入用户名' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="password" label="密码" rules={[{ required: true, message: '请输入密码' }]}>
                            <Input.Password />
                        </Form.Item>
                    </>
                )}
                {this.state.modalType === 'edit' && (
                    // 移除 initialValues 使用 setFieldsValue 动态设置
                    <>
                        <Form.Item name="docName" label="医生姓名" rules={[{ required: true, message: '请输入医生姓名' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="department" label="科室" rules={[{ required: true, message: '请输入科室' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="title" label="职称" rules={[{ required: true, message: '请输入职称' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="email" label="邮箱" rules={[{ required: true, message: '请输入邮箱' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="phoneNumber" label="电话" rules={[{ required: true, message: '请输入电话' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="profile" label="简介" rules={[{ required: true, message: '请输入简介' }]}>
                            <Input />
                        </Form.Item>
                    </>
                )}
                <Space>
                    <Button type="primary" htmlType="submit">确定</Button>
                    <Button onClick={this.handleModalCancel}>取消</Button>
                </Space>
            </Form>
        );
    };

    // Define table columns and action buttons
    columns = [
        { title: '医生姓名', dataIndex: 'docName', key: 'docName', align: 'center' },
        { title: '科室', dataIndex: 'department', key: 'department', align: 'center' },
        { title: '职称', dataIndex: 'title', key: 'title', align: 'center' },
        { title: '邮箱', dataIndex: 'email', key: 'email', align: 'center' },
        { title: '电话', dataIndex: 'phoneNumber', key: 'phoneNumber', align: 'center' },
        { title: '简介', dataIndex: 'profile', key: 'profile', align: 'center' },
        {
            title: '操作',
            key: 'action',
            align: 'center',
            render: (_, record) => (
                <Space size="middle">
                    <button type="primary" onClick={() => this.handleModalShow('edit', record)}
                            style={{ float: 'right', backgroundColor: '#3A77A8', color: 'white', marginTop: '10px', fontSize:'17px', borderRadius:'4px',borderColor:'#3A77A8'}}>
                        编辑</button>
                    <button type="primary" danger onClick={() => this.handleDelete(record.key)}
                            style={{ float: 'right', backgroundColor: '#CD3333', color: 'white', marginTop: '10px', fontSize:'17px', borderRadius:'4px',borderColor:'#CD3333'}}>
                        删除</button>
                </Space>
            ),
        }
    ];

    render() {
        const { dataSource, isModalVisible, modalType } = this.state;

        return (
            <div style={{ margin: '10px' }}>
                <button type="primary" onClick={() => this.handleModalShow('add')}
                        style={{ backgroundColor: '#3A77A8', color: 'white', marginTop: '10px', fontSize:'17px', borderRadius:'4px',borderColor:'#3A77A8'}}>
                    添加医生</button>
                <Table dataSource={dataSource} columns={this.columns} loading={this.state.loading} pagination={{ pageSize: 5, position: ['bottomCenter'] }} />
                <Modal title={modalType === 'add' ? '添加医生' : '编辑医生'} visible={isModalVisible} onCancel={this.handleModalCancel} footer={null}>
                    {this.renderModalContent()}
                </Modal>
            </div>
        );
    }
}

export default RegDoc;
