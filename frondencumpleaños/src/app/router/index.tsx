import { Suspense, lazy } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import { PublicLayout } from '../../components/layouts/PublicLayout';
import { AdminLayout } from '../../components/layouts/AdminLayout';
import { UserLayout } from '../../components/layouts/UserLayout';
import { Loader } from '../../components/ui/Loader';

// Lazy loading pages
const Home = lazy(() => import('../../pages/Home'));
const LoginPage = lazy(() => import('../../modules/auth/LoginPage'));
const RegisterPage = lazy(() => import('../../modules/auth/RegisterPage'));
const ForgotPasswordPage = lazy(() => import('../../modules/auth/ForgotPasswordPage'));
const NotFound = lazy(() => import('../../pages/NotFound'));

// Admin Pages
const DashboardAdmin = lazy(() => import('../../modules/dashboard/DashboardAdmin'));
const EventosAdmin = lazy(() => import('../../modules/eventos/EventosList')); // shared list
const EventoDetailAdmin = lazy(() => import('../../modules/eventos/EventoDetail'));
const ClientesPage = lazy(() => import('../../modules/clientes/ClientesPage'));
const PersonalPage = lazy(() => import('../../modules/personal/PersonalPage'));
const AgendaPage = lazy(() => import('../../modules/agenda/AgendaPage'));
const MinutasPage = lazy(() => import('../../modules/minuta/MinutaPage'));
const BitacoraPage = lazy(() => import('../../modules/bitacora/BitacoraPage'));
const TipoFiestaPage = lazy(() => import('../../modules/admin/TipoFiestaPage'));
const RolesPersonalPage = lazy(() => import('../../modules/admin/RolesPersonalPage'));

// User Pages
const DashboardUsuario = lazy(() => import('../../modules/dashboard/DashboardUsuario'));
const EventosUser = lazy(() => import('../../modules/eventos/EventosList')); // shared list
const EventoCreate = lazy(() => import('../../modules/eventos/EventoCreate'));
const EventoDetailUser = lazy(() => import('../../modules/eventos/EventoDetail')); // shared details

const SuspenseWrapper = ({ children }: { children: React.ReactNode }) => (
  <Suspense fallback={<Loader />}>{children}</Suspense>
);

export const router = createBrowserRouter([
  {
    element: <PublicLayout />,
    children: [
      { path: '/', element: <SuspenseWrapper><Home /></SuspenseWrapper> },
      { path: '/login', element: <SuspenseWrapper><LoginPage /></SuspenseWrapper> },
      { path: '/registro', element: <SuspenseWrapper><RegisterPage /></SuspenseWrapper> },
      { path: '/recuperar', element: <SuspenseWrapper><ForgotPasswordPage /></SuspenseWrapper> },
    ],
  },
  {
    path: '/admin',
    element: <AdminLayout />,
    children: [
      { path: 'dashboard', element: <SuspenseWrapper><DashboardAdmin /></SuspenseWrapper> },
      { path: 'eventos', element: <SuspenseWrapper><EventosAdmin /></SuspenseWrapper> },
      { path: 'eventos/:id', element: <SuspenseWrapper><EventoDetailAdmin /></SuspenseWrapper> },
      { path: 'clientes', element: <SuspenseWrapper><ClientesPage /></SuspenseWrapper> },
      { path: 'personal', element: <SuspenseWrapper><PersonalPage /></SuspenseWrapper> },
      { path: 'agenda', element: <SuspenseWrapper><AgendaPage /></SuspenseWrapper> },
      { path: 'minutas', element: <SuspenseWrapper><MinutasPage /></SuspenseWrapper> },
      { path: 'bitacora', element: <SuspenseWrapper><BitacoraPage /></SuspenseWrapper> },
      { path: 'configuracion/tipos', element: <SuspenseWrapper><TipoFiestaPage /></SuspenseWrapper> },
      { path: 'configuracion/roles', element: <SuspenseWrapper><RolesPersonalPage /></SuspenseWrapper> },
    ],
  },
  {
    path: '/app',
    element: <UserLayout />,
    children: [
      { path: 'dashboard', element: <SuspenseWrapper><DashboardUsuario /></SuspenseWrapper> },
      { path: 'eventos', element: <SuspenseWrapper><EventosUser /></SuspenseWrapper> },
      { path: 'eventos/nuevo', element: <SuspenseWrapper><EventoCreate /></SuspenseWrapper> },
      { path: 'eventos/:id', element: <SuspenseWrapper><EventoDetailUser /></SuspenseWrapper> },
    ],
  },
  {
    path: '*',
    element: <SuspenseWrapper><NotFound /></SuspenseWrapper>,
  },
]);
