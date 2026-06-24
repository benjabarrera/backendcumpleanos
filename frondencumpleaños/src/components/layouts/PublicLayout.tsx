
import { Outlet, Navigate } from 'react-router-dom';
import { useAuthStore } from '../../app/store/authStore';

export const PublicLayout = () => {
  const user = useAuthStore((state) => state.user);

  if (user) {
    return <Navigate to={user.rolSistema === 'CLIENTE' ? '/app/dashboard' : '/admin/dashboard'} replace />;
  }

  return (
    <div className="min-h-screen bg-void text-text-primary">
      <Outlet />
    </div>
  );
};
