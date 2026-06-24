
import { useAuthStore } from '../../app/store/authStore';
import { Button } from '../../components/ui/Button';
import { useNavigate } from 'react-router-dom';
import { PartyPopper } from 'lucide-react';

export default function DashboardUsuario() {
  const user = useAuthStore(state => state.user);
  const navigate = useNavigate();

  return (
    <div className="space-y-6">
      <div className="bg-surface border border-border p-8 rounded-2xl shadow-sm text-center">
        <div className="mx-auto h-16 w-16 bg-primary/20 text-primary-light rounded-full flex items-center justify-center mb-6">
          <PartyPopper className="h-8 w-8" />
        </div>
        <h1 className="text-3xl font-display font-bold text-text-primary mb-2">
          ¡Hola, {user?.nombre}!
        </h1>
        <p className="text-text-secondary max-w-lg mx-auto mb-8">
          Bienvenido a tu panel de MiCumpleaños. Aquí puedes gestionar tus próximos eventos y solicitar nuevas celebraciones.
        </p>
        <div className="flex justify-center gap-4">
          <Button size="lg" onClick={() => navigate('/app/eventos/nuevo')}>
            Solicitar Fiesta
          </Button>
          <Button size="lg" variant="outline" onClick={() => navigate('/app/eventos')}>
            Mis Eventos
          </Button>
        </div>
      </div>
    </div>
  );
}
