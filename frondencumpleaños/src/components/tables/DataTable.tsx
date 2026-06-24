import type { ReactNode } from 'react';
import { cn } from '../../utils/cn';
import { Loader } from '../ui/Loader';

interface Column<T> {
  header: string;
  accessorKey?: keyof T;
  cell?: (item: T) => ReactNode;
  className?: string;
}

interface DataTableProps<T> {
  columns: Column<T>[];
  data: T[];
  isLoading?: boolean;
  emptyMessage?: string;
  onRowClick?: (item: T) => void;
}

export function DataTable<T>({
  columns,
  data,
  isLoading,
  emptyMessage = 'No hay datos disponibles',
  onRowClick,
}: DataTableProps<T>) {
  if (isLoading) {
    return (
      <div className="w-full rounded-md border border-border bg-surface p-8">
        <Loader text="Cargando datos..." />
      </div>
    );
  }

  if (data.length === 0) {
    return (
      <div className="w-full rounded-md border border-border bg-surface p-8 text-center text-text-muted">
        {emptyMessage}
      </div>
    );
  }

  return (
    <div className="w-full overflow-x-auto rounded-md border border-border bg-surface">
      <table className="w-full text-sm text-left">
        <thead className="bg-elevated text-text-secondary border-b border-border">
          <tr>
            {columns.map((col, i) => (
              <th key={i} className={cn("px-4 py-3 font-medium whitespace-nowrap", col.className)}>
                {col.header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {data.map((item, i) => (
            <tr
              key={(item as any).id ? String((item as any).id) : i}
              onClick={() => onRowClick?.(item)}
              className={cn(
                "transition-colors hover:bg-elevated/50",
                onRowClick && "cursor-pointer"
              )}
            >
              {columns.map((col, colIdx) => (
                <td key={colIdx} className={cn("px-4 py-3", col.className)}>
                  {col.cell ? col.cell(item) : col.accessorKey ? String(item[col.accessorKey]) : null}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
