
import { Outlet, Navigate } from 'react-router-dom';
import { useAuthStore } from '../../app/store/authStore';
import { useUiStore } from '../../app/store/uiStore';
import { Sidebar } from './Sidebar';
import { Navbar } from './Navbar';
import { cn } from '../../utils/cn';

export const AdminLayout = () => {
  const user = useAuthStore((state) => state.user);
  const sidebarOpen = useUiStore((state) => state.sidebarOpen);

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (user.rolSistema === 'CLIENTE') {
    return <Navigate to="/app/dashboard" replace />;
  }

  return (
    <div className="min-h-screen bg-void flex">
      <Sidebar />
      <div className={cn("flex flex-1 flex-col transition-all", sidebarOpen ? "ml-64" : "ml-0")}>
        <Navbar />
        <main className="flex-1 p-6">
          <div className="mx-auto max-w-7xl animate-in fade-in duration-300">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
};
