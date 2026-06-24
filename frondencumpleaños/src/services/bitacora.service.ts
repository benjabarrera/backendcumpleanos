import api from './api';
import type { BitacoraEvento, BitacoraEventoRequest } from '../interfaces';

export const bitacoraService = {
  getAll: async (params?: { idEvento?: number }) => {
    const { data } = await api.get<BitacoraEvento[]>('/bitacoras-evento', { params });
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<BitacoraEvento>(`/bitacoras-evento/${id}`);
    return data;
  },
  create: async (payload: BitacoraEventoRequest) => {
    const { data } = await api.post<BitacoraEvento>('/bitacoras-evento', payload);
    return data;
  },
  update: async (id: number, payload: BitacoraEventoRequest) => {
    const { data } = await api.put<BitacoraEvento>(`/bitacoras-evento/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/bitacoras-evento/${id}`);
    return data;
  },
};
