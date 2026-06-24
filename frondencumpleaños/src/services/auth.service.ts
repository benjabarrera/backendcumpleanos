import api from './api';
import type { LoginRequest, AuthUser } from '../interfaces';

export const authService = {
  login: async (credentials: LoginRequest) => {
    const { data } = await api.post<AuthUser>('/auth/login', credentials);
    return data;
  },
};
