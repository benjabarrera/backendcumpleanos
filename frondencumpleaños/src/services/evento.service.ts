import api from './api';
import type { Evento, EstadoEvento, EventoCreateRequest, EventoUpdateRequest } from '../interfaces';

export const eventoService = {
  getAll: async (params?: { estado?: EstadoEvento; fecha?: string }) => {
    const { data } = await api.get<Evento[]>('/eventos', { params });
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<Evento>(`/eventos/${id}`);
    return data;
  },
  create: async (payload: EventoCreateRequest) => {
    const { data } = await api.post<Evento>('/eventos', payload);
    return data;
  },
  update: async (id: number, payload: EventoUpdateRequest) => {
    const { data } = await api.put<Evento>(`/eventos/${id}`, payload);
    return data;
  },
  confirmar: async (id: number) => {
    const { data } = await api.post<Evento>(`/eventos/${id}/confirmar`);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/eventos/${id}`);
    return data;
  },
};
