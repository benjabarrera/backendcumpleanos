import api from './api';
import type { MinutaEvento, MinutaEventoRequest } from '../interfaces';

export const minutaService = {
  getAll: async () => {
    const { data } = await api.get<MinutaEvento[]>('/minutas-evento');
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<MinutaEvento>(`/minutas-evento/${id}`);
    return data;
  },
  getByEventoId: async (idEvento: number) => {
    const { data } = await api.get<MinutaEvento>(`/minutas-evento/evento/${idEvento}`);
    return data;
  },
  create: async (payload: MinutaEventoRequest) => {
    const { data } = await api.post<MinutaEvento>('/minutas-evento', payload);
    return data;
  },
  update: async (id: number, payload: MinutaEventoRequest) => {
    const { data } = await api.put<MinutaEvento>(`/minutas-evento/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/minutas-evento/${id}`);
    return data;
  },
};
