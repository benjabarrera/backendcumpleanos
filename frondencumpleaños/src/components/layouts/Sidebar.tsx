
import { NavLink, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../../app/store/authStore';
import { useUiStore } from '../../app/store/uiStore';
import { cn } from '../../utils/cn';
import {
  LayoutDashboard,
  CalendarDays,
  Users,
  Briefcase,
  Settings,
  LogOut,
  CalendarCheck,
  FileText,
  Clock
} from 'lucide-react';

export const Sidebar = () => {
  const { user, logout } = useAuthStore();
  const sidebarOpen = useUiStore((state) => state.sidebarOpen);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const adminLinks = [
    { to: '/admin/dashboard', icon: LayoutDashboard, label: 'Dashboard' },
    { to: '/admin/eventos', icon: CalendarDays, label: 'Eventos' },
    { to: '/admin/agenda', icon: CalendarCheck, label: 'Agenda' },
    { to: '/admin/clientes', icon: Users, label: 'Clientes' },
    { to: '/admin/personal', icon: Briefcase, label: 'Personal' },
    { to: '/admin/bitacora', icon: Clock, label: 'Bitácora' },
    { to: '/admin/minutas', icon: FileText, label: 'Minutas' },
    { to: '/admin/configuracion', icon: Settings, label: 'Configuración' },
  ];

  const userLinks = [
    { to: '/app/dashboard', icon: LayoutDashboard, label: 'Mi Panel' },
    { to: '/app/eventos', icon: CalendarDays, label: 'Mis Eventos' },
  ];

  const links = user?.rolSistema === 'CLIENTE' ? userLinks : adminLinks;

  if (!sidebarOpen) return null;

  return (
    <aside className="fixed inset-y-0 left-0 z-40 w-64 bg-surface border-r border-border flex flex-col transition-transform">
      <div className="flex h-16 shrink-0 items-center px-6 border-b border-border">
        <h1 className="text-xl font-display font-bold text-primary-light">
          Mi<span className="text-text-primary">Cumpleaños</span>
        </h1>
      </div>

      <nav className="flex-1 space-y-1 px-4 py-6 overflow-y-auto">
        {links.map((link) => {
          const Icon = link.icon;
          return (
            <NavLink
              key={link.to}
              to={link.to}
              className={({ isActive }) =>
                cn(
                  'flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors',
                  isActive
                    ? 'bg-primary/10 text-primary-light'
                    : 'text-text-secondary hover:bg-elevated hover:text-text-primary'
                )
              }
            >
              <Icon className="h-5 w-5 shrink-0" />
              {link.label}
            </NavLink>
          );
        })}
      </nav>

      <div className="p-4 border-t border-border">
        <div className="flex items-center gap-3 px-3 py-2 mb-2">
          <div className="flex-1 min-w-0">
            <p className="text-sm font-medium text-text-primary truncate">
              {user?.nombre} {user?.apellido}
            </p>
            <p className="text-xs text-text-muted truncate">
              {user?.rolSistema}
            </p>
          </div>
        </div>
        <button
          onClick={handleLogout}
          className="flex w-full items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-text-secondary hover:bg-elevated hover:text-text-primary transition-colors"
        >
          <LogOut className="h-5 w-5 shrink-0" />
          Cerrar Sesión
        </button>
      </div>
    </aside>
  );
};
