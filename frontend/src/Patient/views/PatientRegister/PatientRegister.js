import React, { useState } from 'react';
import { Table, Button } from 'antd';
import 'bootstrap/dist/css/bootstrap.min.css';

const PatientRegister = () => {
    const [expandedRowKeys, setExpandedRowKeys] = useState([]);

    const departments = [
        {
            key: '1',
            department: '内科',
            type: '科室',
            subs: [
                { key: '1-1', department: '心脏内科', type: '门诊' },
                { key: '1-2', department: '呼吸病', type: '门诊' },
                { key: '1-3', department: '普通内科', type: '门诊' }
            ],
        },
        {
            key: '2',
            department: '外科',
            type: '科室',
            subs: [
                { key: '2-1', department: '口腔科', type: '门诊' },
                { key: '2-2', department: '皮肤科', type: '门诊' },
                { key: '2-3', department: '眼科', type: '门诊' }
            ],
        },
        {
            key: '3',
            department: '中医',
            type: '科室',
            subs: [
                { key: '3-1', department: '中医呼吸科', type: '门诊' },
                { key: '3-2', department: '针灸科', type: '门诊' },
                { key: '3-3', department: '推拿科', type: '门诊' }
            ],
        },
        {
            key: '4',
            department: '儿科',
            type: '科室',
            subs: [
                { key: '4-1', department: '小儿内分泌', type: '门诊' },
                { key: '4-2', department: '儿童眼科', type: '门诊' }
            ],
        }
    ];

    const handleExpand = (expanded, record) => {
        const keys = expanded ? [...expandedRowKeys, record.key] : expandedRowKeys.filter(k => k !== record.key);
        setExpandedRowKeys(keys);
    };

    const columns = [
        { title: '科室', dataIndex: 'department', key: 'department' },
        { title: '类型', dataIndex: 'type', key: 'type' },
        { title: '操作', key: 'operation', render: () => <Button type="primary">挂号</Button> },
    ];

    return (
        <div className="container mt-3">
            <Table
                columns={columns}
                dataSource={departments}
                expandable={{
                    expandedRowKeys,
                    onExpand: handleExpand,
                    expandedRowRender: record => (
                        <Table
                            columns={columns}
                            dataSource={record.subs}
                            pagination={false}
                        />
                    ),
                }}
            />
        </div>
    );
};

export default PatientRegister;
