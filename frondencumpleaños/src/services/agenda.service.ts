import api from './api';
import type { AgendaEvento, AgendaEventoRequest } from '../interfaces';

export const agendaService = {
  getAll: async () => {
    const { data } = await api.get<AgendaEvento[]>('/agendas-evento');
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<AgendaEvento>(`/agendas-evento/${id}`);
    return data;
  },
  getByEventoId: async (idEvento: number) => {
    const { data } = await api.get<AgendaEvento>(`/agendas-evento/evento/${idEvento}`);
    return data;
  },
  create: async (payload: AgendaEventoRequest) => {
    const { data } = await api.post<AgendaEvento>('/agendas-evento', payload);
    return data;
  },
  update: async (id: number, payload: AgendaEventoRequest) => {
    const { data } = await api.put<AgendaEvento>(`/agendas-evento/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/agendas-evento/${id}`);
    return data;
  },
};
