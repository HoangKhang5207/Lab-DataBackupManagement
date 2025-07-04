import axios from 'axios';

const api = axios.create({ baseURL: 'http://localhost:8080' });

export const fetchBackups = (params) =>
  api.get('/api/backups', { params });
export const fetchBackup = (id) =>
  api.get(`/api/backups/${id}`);
export const createBackup = (data) =>
  api.post('/api/backups', data);
export const updateBackup = (id, data) =>
  api.put(`/api/backups/${id}`, data);
export const deleteBackup = (id) =>
  api.delete(`/api/backups/${id}`);
export const exportExcel = () =>
  api.get('/api/backups/export', { responseType: 'blob' });