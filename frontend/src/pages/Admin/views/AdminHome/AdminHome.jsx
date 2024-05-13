import React, { Component } from 'react';
import { Table, Button, message } from 'antd';
import { checkIntegrity, generateDigest } from '../../../../api/integrity';

class AdminHome extends Component {
    state = {
        tables: [
            { key: '1', tableName: '用户表' },
            { key: '2', tableName: '医生表' },
            { key: '3', tableName: '患者表' },
            { key: '4', tableName: '病历表' },
            { key: '5', tableName: '预约表' }
        ]
    };

    handleCheckIntegrity = async (tableName) => {
        try {
            const result = await checkIntegrity(tableName);
            message.success(`完整性检查结果: ${result}`);
        } catch (error) {
            message.error(`完整性检查失败: ${error.message}`);
        }
    };

    handleGenerateDigest = async (tableName) => {
        try {
            const result = await generateDigest(tableName);
            message.success(`生成摘要结果: ${result}`);
        } catch (error) {
            message.error(`生成摘要失败: ${error.message}`);
        }
    };

    render() {
        const columns = [
            {
                title: '表名',
                dataIndex: 'tableName',
                key: 'tableName',
                align: 'center'
            },
            {
                title: '操作',
                key: 'action',
                align: 'center',
                render: (_, record) => (
                    <div>
                        <Button onClick={() => this.handleCheckIntegrity(record.tableName)}>
                            完整性检查
                        </Button>
                        <Button onClick={() => this.handleGenerateDigest(record.tableName)} style={{ marginLeft: 8 }}>
                            生成摘要
                        </Button>
                    </div>
                )
            }
        ];

        return (
            <div>
                <Table dataSource={this.state.tables} columns={columns} pagination={{position: ['bottomCenter']}}/>
            </div>
        );
    }
}

export default AdminHome;
