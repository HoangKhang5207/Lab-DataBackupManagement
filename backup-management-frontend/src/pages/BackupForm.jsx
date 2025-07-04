import { useEffect } from 'react';
import '@ant-design/v5-patch-for-react-19';
import { Form, Input, Button, DatePicker, notification } from 'antd';
import { useNavigate, useParams } from 'react-router-dom';
import dayjs from 'dayjs';
import {
  createBackup,
  fetchBackup,
  updateBackup,
} from '../services/api';

export default function BackupForm() {
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const { id } = useParams();
  const isEdit = Boolean(id);

  useEffect(() => {
    if (isEdit) {
      fetchBackup(id).then(res => {
        form.setFieldsValue({
          ...res.data,
          backupTime: dayjs(res.data.backupTime),
        });
      });
    } else {
      form.setFieldsValue({ backupTime: dayjs() });
    }
  }, [form, id, isEdit]);

  const onFinish = async (values) => {
    const payload = {
      ...values,
      backupTime: values.backupTime.format('YYYY-MM-DDTHH:mm:ss'),
    };
    try {
      if (isEdit) await updateBackup(id, payload);
      else await createBackup(payload);
      notification.success({ message: isEdit ? 'Cập nhật thành công' : 'Tạo mới thành công' });
      navigate('/backups');
    } catch (err) {
      notification.error({ message: err.response?.data?.details || 'Lỗi không xác định' });
    }
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={onFinish}
      initialValues={{ note: '' }}
    >
      <Form.Item
        name="backupTime"
        label="Thời điểm Backup"
        rules={[{ required: true, message: 'Chọn thời điểm backup' }]}
      >
        <DatePicker showTime />
      </Form.Item>

      <Form.Item
        name="userId"
        label="User ID"
        rules={[{ required: true, message: 'Nhập User ID' }]}
      >
        <Input type="number" />
      </Form.Item>

      <Form.Item
        name="backupPath"
        label="Đường dẫn file .bak"
        rules={[
          { required: true, message: 'Nhập đường dẫn file' },
          { max: 255, message: 'Tối đa 255 ký tự' },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="note"
        label="Ghi chú"
        rules={[{ max: 255, message: 'Tối đa 255 ký tự' }]}
      >
        <Input.TextArea rows={4} />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          {isEdit ? 'Cập nhật' : 'Tạo mới'}
        </Button>
        <Button style={{ marginLeft: 8 }} onClick={() => navigate('/backups')}>
          Hủy
        </Button>
      </Form.Item>
    </Form>
  );
}