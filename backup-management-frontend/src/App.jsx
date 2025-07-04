import { Routes, Route, Navigate } from 'react-router-dom';
import { Layout } from 'antd';
import BackupList from './pages/BackupList';
import BackupForm from './pages/BackupForm';

const { Header, Content } = Layout;

export default function App() {
  return (
    <Layout>
      <Header style={{ color: '#fff', fontSize: '1.5rem' }}>Backup Management</Header>
      <Content style={{ padding: '24px' }}>
        <Routes>
          <Route path="/" element={<Navigate to="/backups" replace />} />
          <Route path="/backups" element={<BackupList />} />
          <Route path="/backups/new" element={<BackupForm />} />
          <Route path="/backups/:id/edit" element={<BackupForm />} />
        </Routes>
      </Content>
    </Layout>
  );
}