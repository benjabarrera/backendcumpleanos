import { useEffect, useState } from 'react';
import { bitacoraService } from '../../services/bitacora.service';
import type { BitacoraEvento, TipoRegistroBitacora } from '../../interfaces';
import { Loader } from '../../components/ui/Loader';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';

export default function BitacoraPage() {
  const [bitacoras, setBitacoras] = useState<BitacoraEvento[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    bitacoraService.getAll().then(setBitacoras).finally(() => setIsLoading(false));
  }, []);

  const getIcon = (tipo: TipoRegistroBitacora) => {
    switch (tipo) {
      case 'INICIO': return '🚀';
      case 'INCIDENCIA': return '⚠️';
      case 'NOVEDAD': return '📝';
      case 'CIERRE': return '✅';
      default: return '📌';
    }
  };

  if (isLoading) return <Loader text="Cargando bitácora..." />;

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Bitácora Global</h1>
      
      <div className="bg-surface border border-border rounded-2xl p-6 shadow-sm">
        <div className="space-y-6 relative before:absolute before:inset-y-0 before:left-[19px] before:w-[2px] before:bg-border">
          {bitacoras.map((b) => (
            <div key={b.idBitacora} className="relative flex items-start gap-6">
              <div className="w-10 h-10 rounded-full bg-elevated border-2 border-border flex items-center justify-center text-lg z-10 shrink-0 shadow-sm">
                {getIcon(b.tipoRegistro)}
              </div>
              <div className="flex-1 bg-elevated border border-border rounded-xl p-4 shadow-sm">
                <div className="flex items-center justify-between mb-2">
                  <div className="font-semibold text-text-primary">Evento #{b.idEvento} - {b.tipoRegistro}</div>
                  <div className="text-xs text-text-muted">
                    {format(new Date(b.timestampLog), "dd MMM yyyy HH:mm", { locale: es })}
                  </div>
                </div>
                <p className="text-text-secondary text-sm">{b.descripcion}</p>
                {b.personalNombre && (
                  <p className="text-xs text-text-muted mt-2 mt-auto text-right">Por: {b.personalNombre}</p>
                )}
              </div>
            </div>
          ))}
          {bitacoras.length === 0 && (
            <p className="text-text-muted text-center italic py-10">No hay registros en la bitácora.</p>
          )}
        </div>
      </div>
    </div>
  );
}
