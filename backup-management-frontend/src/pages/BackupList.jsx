import React, { useEffect, useState } from 'react';
import '@ant-design/v5-patch-for-react-19';
import { Table, Button, Input, DatePicker, Space, Popconfirm, notification } from 'antd';
import { Link } from 'react-router-dom';
import dayjs from 'dayjs';
import {
  fetchBackups,
  deleteBackup,
  exportExcel,
} from '../services/api';

export default function BackupList() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [filters, setFilters] = useState({});

  const loadData = React.useCallback(async () => {
    setLoading(true);
    try {
      const res = await fetchBackups(filters);
      setData(res.data);
    } catch (error) {
      notification.error({ message: 'Tải dữ liệu thất bại' });
    } finally {
      setLoading(false);
    }
  }, [filters]);

  useEffect(() => { loadData(); }, [loadData]);

  const handleDelete = async (id) => {
    await deleteBackup(id);
    notification.success({ message: 'Xóa thành công' });
    loadData();
  };

  const handleExport = async () => {
    try {
      const res = await exportExcel();
      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `backup_history_${dayjs().format('YYYYMMDD')}.xlsx`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch {
      notification.error({ message: 'Xuất Excel thất bại' });
    }
  };

  const columns = [
    { title: 'ID', dataIndex: 'id' },
    { title: 'Thời gian', dataIndex: 'backupTime' },
    { title: 'Người tạo', dataIndex: 'fullName' },
    { title: 'Đường dẫn', dataIndex: 'backupPath' },
    { title: 'Ghi chú', dataIndex: 'note' },
    {
      title: 'Hành động',
      render: (_, record) => (
        <Space>
          <Link to={`/backups/${record.id}/edit`}>Sửa</Link>
          <Popconfirm
            title="Xác nhận xóa?"
            onConfirm={() => handleDelete(record.id)}
          >
            <a>Xóa</a>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Space style={{ marginBottom: 16 }}>
        <DatePicker.RangePicker
          onChange={(dates) => dates ? setFilters({
            ...filters,
            startDate: dates[0].format(),
            endDate: dates[1].format()
          }) : setFilters({})}
        />
        <Input
          placeholder="Ghi chú"
          onChange={(e) => setFilters({ ...filters, note: e.target.value })}
        />
        <Button type="primary"><Link to="/backups/new">Thêm mới</Link></Button>
        <Button onClick={handleExport}>Xuất Excel</Button>
      </Space>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={data}
        loading={loading}
      />
    </>
  );
}