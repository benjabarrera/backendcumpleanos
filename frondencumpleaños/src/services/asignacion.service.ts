import api from './api';
import type { AsignacionPersonal, AsignacionPersonalRequest, EstadoAsignacion } from '../interfaces';

export const asignacionService = {
  getAll: async (params?: { idEvento?: number; estado?: EstadoAsignacion }) => {
    const { data } = await api.get<AsignacionPersonal[]>('/asignaciones-personal', { params });
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<AsignacionPersonal>(`/asignaciones-personal/${id}`);
    return data;
  },
  create: async (payload: AsignacionPersonalRequest) => {
    const { data } = await api.post<AsignacionPersonal>('/asignaciones-personal', payload);
    return data;
  },
  update: async (id: number, payload: AsignacionPersonalRequest) => {
    const { data } = await api.put<AsignacionPersonal>(`/asignaciones-personal/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/asignaciones-personal/${id}`);
    return data;
  },
};
