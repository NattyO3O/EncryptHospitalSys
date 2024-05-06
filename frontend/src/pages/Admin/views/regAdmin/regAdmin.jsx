import React, { Component } from 'react';
import { Table, Space, Button, Modal, Form, Input, message } from 'antd';
import * as api from '../../../../api/manage';

class RegAdmin extends Component {
    state = {
        dataSource: [],
        loading: true,
        isModalVisible: false,
    };

    componentDidMount() {
        this.loadAdmins();
    }

    // Fetch all admins from the backend
    loadAdmins = async () => {
        try {
            const response = await api.fetchAdmins();
            // Transform the data to fit the Table component by mapping "userID" to "key"
            const mappedData = response.data.map(admin => ({
                ...admin,
                key: admin.userID
            }));
            this.setState({ dataSource: mappedData, loading: false });
        } catch (error) {
            message.error('加载管理员数据失败');
            this.setState({ loading: false });
        }
    };

    handleModalShow = () => {
        this.setState({ isModalVisible: true });
    };

    handleModalCancel = () => {
        this.setState({ isModalVisible: false });
    };

    // Handle submission of new admin data
    handleSubmit = async (values) => {
        this.setState({ loading: true });
        try {
            await api.addAdmin(values.username, values.password);
            message.success('添加管理员成功');
            this.handleModalCancel();
            this.loadAdmins(); // Refresh data
        } catch (error) {
            message.error('添加管理员失败');
            this.setState({ loading: false });
        }
    };

    // Handle admin deletion
    handleDelete = async (adminID) => {
        this.setState({ loading: true });
        try {
            await api.deleteAdmin(adminID);
            message.success('删除管理员成功');
            this.loadAdmins(); // Refresh data
        } catch (error) {
            message.error('删除管理员失败');
            this.setState({ loading: false });
        }
    };

    // Define table columns and action buttons
    columns = [
        { title: '用户名', dataIndex: 'userName', key: 'userName', align: 'center' },
        {
            title: '操作',
            key: 'action',
            align: 'center',
            render: (_, record) => (
                <Space size="middle">
                    <button type="primary" danger onClick={() => this.handleDelete(record.key)}
                            style={{ backgroundColor: '#CD3333', color: 'white', marginTop: '10px', fontSize:'17px', borderRadius:'4px',borderColor:'#CD3333'}}>
                        删除</button>
                </Space>
            ),
        }
    ];

    // Render the modal form for adding an admin
    renderModalContent = () => {
        return (
            <Form layout="vertical" onFinish={this.handleSubmit}>
                <Form.Item name="username" label="用户名" rules={[{ required: true, message: '请输入用户名' }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="password" label="密码" rules={[{ required: true, message: '请输入密码' }]}>
                    <Input.Password />
                </Form.Item>
                <Space>
                    <Button type="primary" htmlType="submit">确定</Button>
                    <Button onClick={this.handleModalCancel}>取消</Button>
                </Space>
            </Form>
        );
    };

    render() {
        const { dataSource, isModalVisible } = this.state;

        return (
            <div style={{ margin: '10px' }}>
                <button type="primary" onClick={this.handleModalShow}
                        style={{ backgroundColor: '#3A77A8', color: 'white', marginTop: '10px', fontSize:'17px', borderRadius:'4px',borderColor:'#3A77A8'}}>
                    添加</button>
                <Table dataSource={dataSource} columns={this.columns} loading={this.state.loading} pagination={{ pageSize: 5, position: ['bottomCenter'] }} />
                <Modal title='添加管理员' visible={isModalVisible} onCancel={this.handleModalCancel} footer={null}>
                    {this.renderModalContent()}
                </Modal>
            </div>
        );
    }
}

export default RegAdmin;
