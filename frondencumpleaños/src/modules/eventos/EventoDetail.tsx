import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { eventoService } from '../../services/evento.service';
import type { Evento, EstadoEvento } from '../../interfaces';
import { useAuthStore } from '../../app/store/authStore';
import { useUiStore } from '../../app/store/uiStore';
import { Loader } from '../../components/ui/Loader';
import { Badge } from '../../components/ui/Badge';
import { Button } from '../../components/ui/Button';

export default function EventoDetail() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [evento, setEvento] = useState<Evento | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('resumen');
  
  const user = useAuthStore(state => state.user);
  const addToast = useUiStore(state => state.addToast);
  
  const isAdmin = user?.rolSistema !== 'CLIENTE';

  const fetchEvento = async () => {
    try {
      setIsLoading(true);
      const data = await eventoService.getById(Number(id));
      setEvento(data);
    } catch (error) {
      addToast({ type: 'error', message: 'Error al cargar evento' });
      navigate(-1);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchEvento();
  }, [id]);

  const changeEstado = async (nuevoEstado: EstadoEvento) => {
    if (!evento) return;
    try {
      if (nuevoEstado === 'CONFIRMADO') {
        await eventoService.confirmar(Number(id));
      } else {
        await eventoService.update(Number(id), {
          idCliente: evento.idCliente,
          idTipoFiesta: evento.idTipoFiesta,
          nombreCelebrado: evento.nombreCelebrado,
          fechaEvento: evento.fechaEvento,
          horaInicio: evento.horaInicio,
          horaFin: evento.horaFin,
          lugar: evento.lugar,
          cantidadNinos: evento.cantidadNinos,
          cantidadNinosVeganos: evento.cantidadNinosVeganos,
          cantidadNinosCeliacos: evento.cantidadNinosCeliacos,
          estado: nuevoEstado,
          observaciones: evento.observaciones || ''
        });
      }
      addToast({ type: 'success', message: `Estado cambiado a ${nuevoEstado}` });
      fetchEvento();
    } catch (error) {
      addToast({ type: 'error', message: 'Error al cambiar estado' });
    }
  };

  if (isLoading) return <Loader text="Cargando detalle de evento..." />;
  if (!evento) return null;

  const tabs = [
    { id: 'resumen', label: 'Resumen' },
    { id: 'menu', label: 'Menú' },
    ...(isAdmin ? [
      { id: 'agenda', label: 'Agenda' },
      { id: 'personal', label: 'Personal' },
      { id: 'bitacora', label: 'Bitácora' },
      { id: 'minuta', label: 'Minuta' },
    ] : [])
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-3 mb-2">
            <h1 className="text-2xl font-display font-bold text-text-primary">
              Evento #{evento.idEvento} - {evento.nombreCelebrado}
            </h1>
            <Badge variant={evento.estado.toLowerCase() as any}>{evento.estado}</Badge>
          </div>
          <p className="text-text-secondary">{evento.tipoFiestaNombre} | {evento.fechaEvento} | {evento.horaInicio.substring(0,5)} - {evento.horaFin.substring(0,5)}</p>
        </div>
        
        {isAdmin && (
          <div className="flex flex-wrap gap-2">
            {evento.estado === 'SOLICITADO' && <Button size="sm" onClick={() => changeEstado('EN_REVISION')}>Pasar a Revisión</Button>}
            {evento.estado === 'EN_REVISION' && <Button size="sm" variant="primary" onClick={() => changeEstado('CONFIRMADO')}>Confirmar Evento</Button>}
            {evento.estado === 'CONFIRMADO' && <Button size="sm" variant="primary" onClick={() => changeEstado('EJECUTADO')}>Marcar Ejecutado</Button>}
            {['SOLICITADO', 'EN_REVISION', 'CONFIRMADO'].includes(evento.estado) && <Button size="sm" variant="danger" onClick={() => changeEstado('CANCELADO')}>Cancelar</Button>}
          </div>
        )}
      </div>

      <div className="bg-surface border border-border rounded-2xl overflow-hidden shadow-sm">
        <div className="flex overflow-x-auto border-b border-border hide-scrollbar">
          {tabs.map(tab => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`px-6 py-4 text-sm font-medium whitespace-nowrap border-b-2 transition-colors ${
                activeTab === tab.id 
                  ? 'border-primary text-primary-light bg-primary/5' 
                  : 'border-transparent text-text-secondary hover:text-text-primary hover:bg-elevated/50'
              }`}
            >
              {tab.label}
            </button>
          ))}
        </div>

        <div className="p-6">
          {activeTab === 'resumen' && (
            <div className="grid md:grid-cols-2 gap-8">
              <div>
                <h3 className="font-semibold text-lg mb-4">Detalles Generales</h3>
                <dl className="space-y-4 text-sm">
                  <div className="grid grid-cols-3 gap-4 border-b border-border pb-4">
                    <dt className="text-text-muted">Cliente</dt>
                    <dd className="col-span-2 font-medium">{evento.clienteNombre}</dd>
                  </div>
                  <div className="grid grid-cols-3 gap-4 border-b border-border pb-4">
                    <dt className="text-text-muted">Lugar</dt>
                    <dd className="col-span-2">{evento.lugar}</dd>
                  </div>
                  <div className="grid grid-cols-3 gap-4 border-b border-border pb-4">
                    <dt className="text-text-muted">Asistentes</dt>
                    <dd className="col-span-2">
                      {evento.cantidadNinos} Total
                      {evento.cantidadNinosVeganos > 0 && ` (${evento.cantidadNinosVeganos} Veganos)`}
                      {evento.cantidadNinosCeliacos > 0 && ` (${evento.cantidadNinosCeliacos} Celíacos)`}
                    </dd>
                  </div>
                  <div className="grid grid-cols-3 gap-4">
                    <dt className="text-text-muted">Observaciones</dt>
                    <dd className="col-span-2">{evento.observaciones || '-'}</dd>
                  </div>
                </dl>
              </div>
            </div>
          )}
          
          {activeTab === 'menu' && (
            <div>
              {evento.menuEvento ? (
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                  <div className="bg-elevated p-4 rounded-xl border border-border text-center">
                    <p className="text-sm text-text-muted">Platos Salados</p>
                    <p className="text-2xl font-bold">{evento.menuEvento.platosSalados}</p>
                  </div>
                  <div className="bg-elevated p-4 rounded-xl border border-border text-center">
                    <p className="text-sm text-text-muted">Platos Dulces</p>
                    <p className="text-2xl font-bold">{evento.menuEvento.platosDulces}</p>
                  </div>
                  {(evento.menuEvento.platosVeganos > 0 || evento.menuEvento.platosCeliacos > 0) && (
                    <div className="bg-elevated p-4 rounded-xl border border-primary/30 text-center col-span-2 flex justify-around">
                      <div>
                        <p className="text-sm text-text-muted">Platos Veganos</p>
                        <p className="text-2xl font-bold text-primary">{evento.menuEvento.platosVeganos}</p>
                      </div>
                      <div>
                        <p className="text-sm text-text-muted">Platos Celíacos</p>
                        <p className="text-2xl font-bold text-primary">{evento.menuEvento.platosCeliacos}</p>
                      </div>
                    </div>
                  )}
                  <div className="bg-elevated p-4 rounded-xl border border-border text-center">
                    <p className="text-sm text-text-muted">Bolsas Sorpresa</p>
                    <p className="text-2xl font-bold">{evento.menuEvento.bolsasSorpresa}</p>
                  </div>
                  <div className="bg-elevated p-4 rounded-xl border border-border text-center">
                    <p className="text-sm text-text-muted">Tortas</p>
                    <p className="text-2xl font-bold">{evento.menuEvento.tortas}</p>
                  </div>
                  <div className="col-span-2 md:col-span-4 bg-primary/10 border border-primary/20 p-4 rounded-xl flex items-center justify-between">
                    <span className="font-medium text-primary-light">Piñata: {evento.menuEvento.tipoPinata}</span>
                    <span className="font-bold">Cantidad: {evento.menuEvento.cantidadPinatas}</span>
                  </div>
                </div>
              ) : (
                <div className="text-center py-10 text-text-muted">
                  El menú se genera automáticamente al confirmar el evento.
                </div>
              )}
            </div>
          )}

          {isAdmin && activeTab === 'agenda' && <div className="text-text-muted text-center py-10">Agenda del evento (En construcción)</div>}
          {isAdmin && activeTab === 'personal' && <div className="text-text-muted text-center py-10">Personal asignado (En construcción)</div>}
          {isAdmin && activeTab === 'bitacora' && <div className="text-text-muted text-center py-10">Bitácora del evento (En construcción)</div>}
          {isAdmin && activeTab === 'minuta' && <div className="text-text-muted text-center py-10">Minuta del evento (En construcción)</div>}
        </div>
      </div>
    </div>
  );
}
