import { useEffect, useState } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, LineChart, Line } from 'recharts';
import { CalendarDays, Users, Briefcase, Activity } from 'lucide-react';
import type { DashboardMetrics } from '../../interfaces';
import { eventoService } from '../../services/evento.service';
import { clienteService } from '../../services/cliente.service';
import { personalService } from '../../services/personal.service';
import { Loader } from '../../components/ui/Loader';

const MOCK_METRICS: DashboardMetrics = {
  totalEventos: 45,
  eventosSolicitados: 5,
  eventosEnRevision: 3,
  eventosConfirmados: 12,
  eventosEjecutados: 20,
  eventosCancelados: 5,
  totalClientes: 120,
  totalPersonal: 15,
  personalActivo: 12,
  eventosEstaSemana: [],
};

const ingresosMensuales = [
  { name: 'Ene', ingresos: 400000 },
  { name: 'Feb', ingresos: 300000 },
  { name: 'Mar', ingresos: 500000 },
  { name: 'Abr', ingresos: 450000 },
  { name: 'May', ingresos: 600000 },
  { name: 'Jun', ingresos: 700000 },
];

const estadosData = [
  { name: 'Solicitados', cantidad: 5, fill: '#f59e0b' },
  { name: 'En Revisión', cantidad: 3, fill: '#3b82f6' },
  { name: 'Confirmados', cantidad: 12, fill: '#10b981' },
  { name: 'Ejecutados', cantidad: 20, fill: '#8b5cf6' },
];

const Widget = ({ title, value, icon: Icon, color }: any) => (
  <div className="bg-surface border border-border p-6 rounded-2xl flex items-start justify-between shadow-sm">
    <div>
      <p className="text-sm font-medium text-text-secondary">{title}</p>
      <h3 className="text-3xl font-display font-bold mt-2 text-text-primary">{value}</h3>
    </div>
    <div className={`p-3 rounded-xl ${color}`}>
      <Icon className="h-6 w-6" />
    </div>
  </div>
);

export default function DashboardAdmin() {
  const [metrics, setMetrics] = useState<DashboardMetrics | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const eventos = await eventoService.getAll();
        const clientes = await clienteService.getAll();
        const personal = await personalService.getAll();
        
        setMetrics({
          totalEventos: eventos.length,
          eventosSolicitados: eventos.filter(e => e.estado === 'SOLICITADO').length,
          eventosEnRevision: eventos.filter(e => e.estado === 'EN_REVISION').length,
          eventosConfirmados: eventos.filter(e => e.estado === 'CONFIRMADO').length,
          eventosEjecutados: eventos.filter(e => e.estado === 'EJECUTADO').length,
          eventosCancelados: eventos.filter(e => e.estado === 'CANCELADO').length,
          totalClientes: clientes.length,
          totalPersonal: personal.length,
          personalActivo: personal.filter(p => p.activo).length,
          eventosEstaSemana: eventos.slice(0, 5),
        });
      } catch (error) {
        console.error(error);
        setMetrics(MOCK_METRICS);
      } finally {
        setIsLoading(false);
      }
    };
    fetchDashboardData();
  }, []);

  if (isLoading) return <Loader text="Cargando métricas..." />;



  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Panel Administrativo</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Widget title="Total Eventos" value={metrics?.totalEventos} icon={CalendarDays} color="bg-primary/20 text-primary-light" />
        <Widget title="En Revisión" value={metrics?.eventosEnRevision} icon={Activity} color="bg-info/20 text-info" />
        <Widget title="Clientes Activos" value={metrics?.totalClientes} icon={Users} color="bg-success/20 text-success" />
        <Widget title="Personal Activo" value={metrics?.personalActivo} icon={Briefcase} color="bg-warning/20 text-warning" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-surface border border-border rounded-2xl p-6 shadow-sm">
          <h2 className="text-lg font-semibold text-text-primary mb-6">Eventos por Estado</h2>
          <div className="h-80">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={estadosData} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" stroke="#2a2c36" />
                <XAxis dataKey="name" stroke="#8b8fa8" />
                <YAxis stroke="#8b8fa8" />
                <Tooltip 
                  contentStyle={{ backgroundColor: '#1e2028', border: '1px solid #3d3f4d', borderRadius: '8px' }}
                  itemStyle={{ color: '#e8eaf0' }}
                />
                <Bar dataKey="cantidad" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="bg-surface border border-border rounded-2xl p-6 shadow-sm">
          <h2 className="text-lg font-semibold text-text-primary mb-6">Ingresos Estimados</h2>
          <div className="h-80">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={ingresosMensuales} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" stroke="#2a2c36" />
                <XAxis dataKey="name" stroke="#8b8fa8" />
                <YAxis stroke="#8b8fa8" />
                <Tooltip 
                  contentStyle={{ backgroundColor: '#1e2028', border: '1px solid #3d3f4d', borderRadius: '8px' }}
                  itemStyle={{ color: '#e8eaf0' }}
                />
                <Line type="monotone" dataKey="ingresos" stroke="#7C3AED" strokeWidth={3} dot={{ r: 4, fill: '#7C3AED' }} />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
    </div>
  );
}
