import axios from 'axios';
import { useAuthStore } from '../app/store/authStore';

const api = axios.create({
  baseURL: '/api/v1', // Using proxy from vite.config.ts
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Clear auth store and redirect to login
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
