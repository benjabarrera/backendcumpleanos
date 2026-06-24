import api from './api';
import type { Personal, PersonalRequest } from '../interfaces';

export const personalService = {
  getAll: async () => {
    const { data } = await api.get<Personal[]>('/personal');
    return data;
  },
  getActivos: async () => {
    const { data } = await api.get<Personal[]>('/personal/activos');
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<Personal>(`/personal/${id}`);
    return data;
  },
  create: async (payload: PersonalRequest) => {
    const { data } = await api.post<Personal>('/personal', payload);
    return data;
  },
  update: async (id: number, payload: PersonalRequest) => {
    const { data } = await api.put<Personal>(`/personal/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/personal/${id}`);
    return data;
  },
};
