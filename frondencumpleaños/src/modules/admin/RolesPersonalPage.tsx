import { useEffect, useState } from 'react';
import { rolPersonalService } from '../../services/rolPersonal.service';
import type { RolPersonal } from '../../interfaces';
import { DataTable } from '../../components/tables/DataTable';

export default function RolesPersonalPage() {
  const [roles, setRoles] = useState<RolPersonal[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    rolPersonalService.getAll().then(setRoles).finally(() => setIsLoading(false));
  }, []);

  const formatCLP = (val: number) => new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP' }).format(val);

  const columns = [
    { header: 'ID', accessorKey: 'idRol' as keyof RolPersonal },
    { header: 'Nombre', accessorKey: 'nombre' as keyof RolPersonal },
    { header: 'Descripción', accessorKey: 'descripcion' as keyof RolPersonal },
    { header: 'Tarifa/Hora', cell: (r: RolPersonal) => formatCLP(r.tarifaHora) },
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Roles de Personal</h1>
      <DataTable data={roles} columns={columns} isLoading={isLoading} />
    </div>
  );
}
