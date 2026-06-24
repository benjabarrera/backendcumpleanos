import { useEffect, useState } from 'react';
import { tipoFiestaService } from '../../services/tipoFiesta.service';
import type { TipoFiesta } from '../../interfaces';
import { DataTable } from '../../components/tables/DataTable';
import { Badge } from '../../components/ui/Badge';

export default function TipoFiestaPage() {
  const [tipos, setTipos] = useState<TipoFiesta[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    tipoFiestaService.getAll().then(setTipos).finally(() => setIsLoading(false));
  }, []);

  const columns = [
    { header: 'ID', accessorKey: 'idTipoFiesta' as keyof TipoFiesta },
    { header: 'Nombre', cell: (t: TipoFiesta) => (
      <div className="flex items-center gap-2">
        <div className="w-3 h-3 rounded-full" style={{ backgroundColor: t.colorHex }} />
        <span>{t.nombre}</span>
      </div>
    ) },
    { header: 'Descripción', accessorKey: 'descripcion' as keyof TipoFiesta },
    { header: 'Estado', cell: (t: TipoFiesta) => <Badge variant={t.activo ? 'success' : 'danger'}>{t.activo ? 'Activo' : 'Inactivo'}</Badge> },
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Tipos de Fiesta</h1>
      <DataTable data={tipos} columns={columns} isLoading={isLoading} />
    </div>
  );
}
