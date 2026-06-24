import { useEffect, useState } from 'react';
import { minutaService } from '../../services/minuta.service';
import type { MinutaEvento } from '../../interfaces';
import { DataTable } from '../../components/tables/DataTable';
import { Badge } from '../../components/ui/Badge';
import { Button } from '../../components/ui/Button';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';

export default function MinutaPage() {
  const [minutas, setMinutas] = useState<MinutaEvento[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    minutaService.getAll().then(setMinutas).finally(() => setIsLoading(false));
  }, []);

  const columns = [
    { header: 'N° Minuta', accessorKey: 'numeroMinuta' as keyof MinutaEvento },
    { header: 'ID Evento', accessorKey: 'idEvento' as keyof MinutaEvento },
    { 
      header: 'Fecha Emisión', 
      cell: (m: MinutaEvento) => format(new Date(m.fechaEmision), 'dd MMM yyyy', { locale: es }) 
    },
    { 
      header: 'Enviada al Cliente', 
      cell: (m: MinutaEvento) => <Badge variant={m.enviadaCliente ? 'success' : 'default'}>{m.enviadaCliente ? 'Sí' : 'No'}</Badge> 
    },
    { 
      header: 'Firma Cliente', 
      cell: (m: MinutaEvento) => <Badge variant={m.firmaCliente ? 'success' : 'warning'}>{m.firmaCliente ? 'Firmada' : 'Pendiente'}</Badge> 
    },
    {
      header: 'Acciones',
      cell: () => <Button size="sm" variant="outline">Ver Detalle</Button>
    }
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Minutas</h1>
      <DataTable data={minutas} columns={columns} isLoading={isLoading} />
    </div>
  );
}
