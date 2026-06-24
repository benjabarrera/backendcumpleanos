import { useEffect, useState } from 'react';
import { personalService } from '../../services/personal.service';
import type { Personal } from '../../interfaces';
import { DataTable } from '../../components/tables/DataTable';
import { Badge } from '../../components/ui/Badge';

export default function PersonalPage() {
  const [personal, setPersonal] = useState<Personal[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    personalService.getAll().then(setPersonal).finally(() => setIsLoading(false));
  }, []);

  const columns = [
    { header: 'ID', accessorKey: 'idPersonal' as keyof Personal },
    { header: 'Nombre', cell: (p: Personal) => `${p.nombre} ${p.apellido}` },
    { header: 'Rol', accessorKey: 'rolNombre' as keyof Personal },
    { header: 'Email', accessorKey: 'email' as keyof Personal },
    { header: 'Estado', cell: (p: Personal) => <Badge variant={p.activo ? 'success' : 'danger'}>{p.activo ? 'Activo' : 'Inactivo'}</Badge> },
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Personal</h1>
      <DataTable data={personal} columns={columns} isLoading={isLoading} />
    </div>
  );
}
