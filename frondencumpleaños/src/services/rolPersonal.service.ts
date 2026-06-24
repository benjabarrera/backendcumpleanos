import api from './api';
import type { RolPersonal } from '../interfaces';

export const rolPersonalService = {
  getAll: async () => {
    const { data } = await api.get<RolPersonal[]>('/roles-personal');
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<RolPersonal>(`/roles-personal/${id}`);
    return data;
  },
  create: async (payload: Omit<RolPersonal, 'idRol'>) => {
    const { data } = await api.post<RolPersonal>('/roles-personal', payload);
    return data;
  },
  update: async (id: number, payload: Omit<RolPersonal, 'idRol'>) => {
    const { data } = await api.put<RolPersonal>(`/roles-personal/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/roles-personal/${id}`);
    return data;
  },
};
