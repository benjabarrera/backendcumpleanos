import { create } from 'zustand';

interface ToastOptions {
  message: string;
  type: 'success' | 'error' | 'info' | 'warning';
  duration?: number;
}

interface Toast extends ToastOptions {
  id: string;
}

interface UiState {
  sidebarOpen: boolean;
  toggleSidebar: () => void;
  setSidebarOpen: (isOpen: boolean) => void;
  
  toasts: Toast[];
  addToast: (toast: ToastOptions) => void;
  removeToast: (id: string) => void;
}

export const useUiStore = create<UiState>((set) => ({
  sidebarOpen: true,
  toggleSidebar: () => set((state) => ({ sidebarOpen: !state.sidebarOpen })),
  setSidebarOpen: (isOpen) => set({ sidebarOpen: isOpen }),

  toasts: [],
  addToast: (toastOptions) => {
    const id = Math.random().toString(36).substring(2, 9);
    set((state) => ({
      toasts: [...state.toasts, { ...toastOptions, id }],
    }));

    if (toastOptions.duration !== 0) {
      setTimeout(() => {
        set((state) => ({
          toasts: state.toasts.filter((t) => t.id !== id),
        }));
      }, toastOptions.duration || 3000);
    }
  },
  removeToast: (id) =>
    set((state) => ({ toasts: state.toasts.filter((t) => t.id !== id) })),
}));
