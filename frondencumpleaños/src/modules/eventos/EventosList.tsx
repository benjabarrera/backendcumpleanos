import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { eventoService } from '../../services/evento.service';
import type { Evento } from '../../interfaces';
import { DataTable } from '../../components/tables/DataTable';
import { Badge } from '../../components/ui/Badge';
import { Button } from '../../components/ui/Button';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { Plus } from 'lucide-react';
import { useAuthStore } from '../../app/store/authStore';

export default function EventosList() {
  const [eventos, setEventos] = useState<Evento[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
  const user = useAuthStore(state => state.user);

  useEffect(() => {
    const fetchEventos = async () => {
      try {
        const data = await eventoService.getAll();
        if (user?.rolSistema === 'CLIENTE') {
          setEventos(data.filter(e => e.idCliente === user.idCliente));
        } else {
          setEventos(data);
        }
      } catch (error) {
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchEventos();
  }, [user]);

  const columns = [
    { header: 'ID', accessorKey: 'idEvento' as keyof Evento },
    { header: 'Celebrado', accessorKey: 'nombreCelebrado' as keyof Evento },
    {
      header: 'Fecha',
      cell: (item: Evento) => format(new Date(item.fechaEvento), "dd MMM yyyy", { locale: es })
    },
    { header: 'Tipo', accessorKey: 'tipoFiestaNombre' as keyof Evento },
    {
      header: 'Estado',
      cell: (item: Evento) => (
        <Badge variant={item.estado.toLowerCase() as any}>
          {item.estado.replace('_', ' ')}
        </Badge>
      )
    }
  ];

  const basePath = user?.rolSistema === 'CLIENTE' ? '/app/eventos' : '/admin/eventos';

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-display font-bold text-text-primary">Eventos</h1>
        {user?.rolSistema === 'CLIENTE' && (
          <Button onClick={() => navigate('/app/eventos/nuevo')}>
            <Plus className="h-4 w-4 mr-2" /> Nuevo Evento
          </Button>
        )}
      </div>

      <DataTable
        data={eventos}
        columns={columns}
        isLoading={isLoading}
        onRowClick={(item) => navigate(`${basePath}/${item.idEvento}`)}
      />
    </div>
  );
}
