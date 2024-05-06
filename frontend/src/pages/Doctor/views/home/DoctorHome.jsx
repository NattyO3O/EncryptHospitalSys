import React from 'react';
import { Card, Col, Row } from 'antd';
import { UserOutlined, MedicineBoxOutlined } from '@ant-design/icons';

const DoctorHome = () => {
    return (
        <div style={{ padding: '30px' }}>
            <Row gutter={16}>
                <Col span={12}>
                    <Card style={{
                        textAlign: 'center',
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center',
                        height: '200px',  // 调整高度以更好地控制布局
                        borderRadius: '10px', // 圆角效果
                        boxShadow: '0 0 10px rgba(0,0,0,0.1)' // 添加阴影效果
                    }}>
                        <MedicineBoxOutlined style={{ fontSize: '40px', color: '#08c' }} />
                        <div style={{ fontSize: '16px', color: 'rgba(0, 0, 0, 0.65)', marginTop: '10px' }}>门诊数 今日门诊人数</div>
                        <div style={{ fontSize: '24px', color: 'rgba(0,0,0,0.85)', marginTop: '10px' }}>8</div>
                    </Card>
                </Col>
                <Col span={12}>
                    <Card style={{
                        textAlign: 'center',
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center',
                        height: '200px',
                        borderRadius: '10px',
                        boxShadow: '0 0 10px rgba(0,0,0,0.1)'
                    }}>
                        <UserOutlined style={{ fontSize: '40px', color: '#08c' }} />
                        <div style={{ fontSize: '16px', color: 'rgba(0, 0, 0, 0.65)', marginTop: '10px' }}>住院数 当前住院人数</div>
                        <div style={{ fontSize: '24px', color: 'rgba(0,0,0,0.85)', marginTop: '10px' }}>4</div>
                    </Card>
                </Col>
            </Row>
        </div>
    );
};

export default DoctorHome;

