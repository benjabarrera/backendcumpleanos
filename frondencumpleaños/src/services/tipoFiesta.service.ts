import api from './api';
import type { TipoFiesta } from '../interfaces';

export const tipoFiestaService = {
  getAll: async () => {
    const { data } = await api.get<TipoFiesta[]>('/tipos-fiesta');
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<TipoFiesta>(`/tipos-fiesta/${id}`);
    return data;
  },
  create: async (payload: Omit<TipoFiesta, 'idTipoFiesta'>) => {
    const { data } = await api.post<TipoFiesta>('/tipos-fiesta', payload);
    return data;
  },
  update: async (id: number, payload: Omit<TipoFiesta, 'idTipoFiesta'>) => {
    const { data } = await api.put<TipoFiesta>(`/tipos-fiesta/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/tipos-fiesta/${id}`);
    return data;
  },
};
