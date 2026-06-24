import { useEffect, useState } from 'react';
import { clienteService } from '../../services/cliente.service';
import type { Cliente } from '../../interfaces';
import { DataTable } from '../../components/tables/DataTable';
import { Badge } from '../../components/ui/Badge';

export default function ClientesPage() {
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    clienteService.getAll().then(setClientes).finally(() => setIsLoading(false));
  }, []);

  const columns = [
    { header: 'ID', accessorKey: 'idCliente' as keyof Cliente },
    { header: 'Nombre', cell: (c: Cliente) => `${c.nombre} ${c.apellido}` },
    { header: 'Email', accessorKey: 'email' as keyof Cliente },
    { header: 'Teléfono', accessorKey: 'telefono' as keyof Cliente },
    { header: 'Estado', cell: (c: Cliente) => <Badge variant={c.activo ? 'success' : 'danger'}>{c.activo ? 'Activo' : 'Inactivo'}</Badge> },
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Clientes</h1>
      <DataTable data={clientes} columns={columns} isLoading={isLoading} />
    </div>
  );
}
