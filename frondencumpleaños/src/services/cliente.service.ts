import api from './api';
import type { Cliente, ClienteRequest } from '../interfaces';

export const clienteService = {
  getAll: async () => {
    const { data } = await api.get<Cliente[]>('/clientes');
    return data;
  },
  getById: async (id: number) => {
    const { data } = await api.get<Cliente>(`/clientes/${id}`);
    return data;
  },
  create: async (payload: ClienteRequest) => {
    const { data } = await api.post<Cliente>('/clientes', payload);
    return data;
  },
  update: async (id: number, payload: ClienteRequest) => {
    const { data } = await api.put<Cliente>(`/clientes/${id}`, payload);
    return data;
  },
  delete: async (id: number) => {
    const { data } = await api.delete(`/clientes/${id}`);
    return data;
  },
};
