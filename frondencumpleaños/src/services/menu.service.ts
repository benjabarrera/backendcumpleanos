import api from './api';
import type { MenuEvento } from '../interfaces';

export const menuService = {
  getByEventoId: async (idEvento: number) => {
    const { data } = await api.get<MenuEvento>(`/menus/evento/${idEvento}`);
    return data;
  },
};
