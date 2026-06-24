import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useNavigate } from 'react-router-dom';
import { Button } from '../../components/ui/Button';
import { Input } from '../../components/ui/Input';
import { Textarea } from '../../components/ui/Textarea';
import { FormField } from '../../components/forms/FormField';
import { useAuthStore } from '../../app/store/authStore';
import { useUiStore } from '../../app/store/uiStore';
import { eventoService } from '../../services/evento.service';
import { tipoFiestaService } from '../../services/tipoFiesta.service';
import type { TipoFiesta } from '../../interfaces';
import { format } from 'date-fns';

const today = new Date();
const dateStr = format(today, 'yyyy-MM-dd');

const schema = z.object({
  nombreCelebrado: z.string().min(2, 'Debe ingresar el nombre del festejado'),
  fechaEvento: z.string().refine(val => val >= dateStr, { message: 'La fecha debe ser igual o posterior a hoy' }),
  horaInicio: z.string().min(1, 'Hora de inicio requerida'),
  horaFin: z.string().min(1, 'Hora de término requerida'),
  cantidadNinos: z.number().min(10, 'Mínimo 10 niños').max(40, 'Máximo 40 niños'),
  cantidadNinosVeganos: z.number().min(0).max(40).optional(),
  cantidadNinosCeliacos: z.number().min(0).max(40).optional(),
  idTipoFiesta: z.string().min(1, 'Seleccione un tipo de fiesta'),
  lugar: z.string().min(3, 'El lugar es obligatorio'),
  observaciones: z.string().optional(),
}).refine(data => data.horaFin > data.horaInicio, {
  message: "La hora de término debe ser posterior a la de inicio",
  path: ["horaFin"],
});

type FormData = z.infer<typeof schema>;

export default function EventoCreate() {
  const navigate = useNavigate();
  const user = useAuthStore(state => state.user);
  const addToast = useUiStore(state => state.addToast);
  const [tiposFiesta, setTiposFiesta] = useState<TipoFiesta[]>([]);

  const { register, handleSubmit, watch, formState: { errors, isSubmitting } } = useForm<FormData>({
    resolver: zodResolver(schema),
    defaultValues: {
      cantidadNinos: 15,
      cantidadNinosVeganos: 0,
      cantidadNinosCeliacos: 0,
    }
  });

  useEffect(() => {
    const fetchTipos = async () => {
      try {
        const data = await tipoFiestaService.getAll();
        setTiposFiesta(data.filter(t => t.activo));
      } catch (error) {
        console.error(error);
      }
    };
    fetchTipos();
  }, []);

  const onSubmit = async (data: FormData) => {
    try {
      if (!user?.idCliente) {
        addToast({ type: 'error', message: 'No tienes un cliente asociado para crear eventos.' });
        return;
      }

      await eventoService.create({
        idCliente: user.idCliente,
        idTipoFiesta: Number(data.idTipoFiesta),
        nombreCelebrado: data.nombreCelebrado,
        fechaEvento: data.fechaEvento,
        horaInicio: data.horaInicio.length === 5 ? data.horaInicio + ':00' : data.horaInicio,
        horaFin: data.horaFin.length === 5 ? data.horaFin + ':00' : data.horaFin,
        lugar: data.lugar,
        cantidadNinos: data.cantidadNinos,
        cantidadNinosVeganos: data.cantidadNinosVeganos || 0,
        cantidadNinosCeliacos: data.cantidadNinosCeliacos || 0,
        observaciones: data.observaciones,
      });

      addToast({ type: 'success', message: 'Evento solicitado exitosamente.' });
      navigate('/app/eventos');
    } catch (error) {
      addToast({ type: 'error', message: 'Error al crear el evento.' });
    }
  };

  return (
    <div className="max-w-3xl mx-auto space-y-6">
      <div className="text-center mb-8">
        <h1 className="text-3xl font-display font-bold text-text-primary">Solicitar Nueva Fiesta</h1>
        <p className="text-text-muted mt-2">Completa los detalles para organizar un evento inolvidable</p>
      </div>
      
      <div className="bg-surface border border-border rounded-2xl p-8 shadow-xl relative overflow-hidden">
        {/* Decorative elements */}
        <div className="absolute top-0 right-0 w-64 h-64 bg-primary/5 rounded-bl-full -z-10" />
        <div className="absolute bottom-0 left-0 w-32 h-32 bg-accent/5 rounded-tr-full -z-10" />

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-8">
          
          {/* SECCIÓN 1: El Festejado */}
          <div>
            <h2 className="text-lg font-semibold text-text-primary mb-4 flex items-center gap-2 border-b border-border pb-2">
              <span className="bg-primary/20 text-primary w-6 h-6 rounded-full flex items-center justify-center text-xs">1</span>
              Datos del Festejado
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <FormField label="Nombre del Festejado(a)" error={errors.nombreCelebrado?.message} required>
                <Input {...register('nombreCelebrado')} placeholder="Ej. Juanito" error={errors.nombreCelebrado?.message} className="bg-void/50" />
              </FormField>
              <FormField label="Cantidad de Niños" error={errors.cantidadNinos?.message} required>
                <div className="flex items-center gap-4">
                  <Input 
                    type="range" 
                    {...register('cantidadNinos', { valueAsNumber: true })} 
                    min={10} max={40}
                    className="w-full accent-primary"
                  />
                  <span className="font-bold text-xl text-primary w-12 text-center">{watch('cantidadNinos')}</span>
                </div>
                <p className="text-xs text-text-muted mt-1">Mínimo 10, Máximo 40</p>
              </FormField>
            </div>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
              <FormField label="Niños con menú Vegano" error={errors.cantidadNinosVeganos?.message}>
                <Input type="number" {...register('cantidadNinosVeganos', { valueAsNumber: true })} min={0} max={40} className="bg-void/50" />
              </FormField>
              <FormField label="Niños con menú Celíaco" error={errors.cantidadNinosCeliacos?.message}>
                <Input type="number" {...register('cantidadNinosCeliacos', { valueAsNumber: true })} min={0} max={40} className="bg-void/50" />
              </FormField>
            </div>
          </div>

          {/* SECCIÓN 2: Tipo de Fiesta */}
          <div>
            <h2 className="text-lg font-semibold text-text-primary mb-4 flex items-center gap-2 border-b border-border pb-2">
              <span className="bg-primary/20 text-primary w-6 h-6 rounded-full flex items-center justify-center text-xs">2</span>
              Temática
            </h2>
            <FormField label="Selecciona el Tipo de Fiesta" error={errors.idTipoFiesta?.message} required>
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mt-2">
                {tiposFiesta.map(t => {
                  const isSelected = watch('idTipoFiesta') === String(t.idTipoFiesta);
                  return (
                    <label 
                      key={t.idTipoFiesta} 
                      className={`relative flex flex-col p-4 cursor-pointer rounded-xl border-2 transition-all duration-200 ${isSelected ? 'border-primary bg-primary/5 shadow-md scale-[1.02]' : 'border-border bg-void/30 hover:border-primary/50 hover:bg-surface'}`}
                    >
                      <input 
                        type="radio" 
                        value={t.idTipoFiesta} 
                        {...register('idTipoFiesta')} 
                        className="sr-only"
                      />
                      <div className="flex items-center justify-between mb-2">
                        <span className="font-semibold text-text-primary">{t.nombre}</span>
                        <span className="w-4 h-4 rounded-full border border-border/50 shadow-sm" style={{ backgroundColor: t.colorHex }}></span>
                      </div>
                      <p className="text-xs text-text-muted line-clamp-2">{t.descripcion || 'Sin descripción'}</p>
                    </label>
                  );
                })}
              </div>
            </FormField>
          </div>

          {/* SECCIÓN 3: Cuándo y Dónde */}
          <div>
            <h2 className="text-lg font-semibold text-text-primary mb-4 flex items-center gap-2 border-b border-border pb-2">
              <span className="bg-primary/20 text-primary w-6 h-6 rounded-full flex items-center justify-center text-xs">3</span>
              Cuándo y Dónde
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <FormField label="Fecha del Evento" error={errors.fechaEvento?.message} required>
                <Input type="date" {...register('fechaEvento')} error={errors.fechaEvento?.message} min={dateStr} className="bg-void/50" />
              </FormField>
              <FormField label="Hora Inicio" error={errors.horaInicio?.message} required>
                <Input type="time" {...register('horaInicio')} error={errors.horaInicio?.message} className="bg-void/50" />
              </FormField>
              <FormField label="Hora Fin" error={errors.horaFin?.message} required>
                <Input type="time" {...register('horaFin')} error={errors.horaFin?.message} className="bg-void/50" />
              </FormField>
            </div>
            <div className="mt-6">
              <FormField label="Lugar del Evento" error={errors.lugar?.message} required>
                <Input {...register('lugar')} placeholder="Dirección completa del salón o casa" error={errors.lugar?.message} className="bg-void/50" />
              </FormField>
            </div>
          </div>

          {/* SECCIÓN 4: Extras */}
          <div>
            <h2 className="text-lg font-semibold text-text-primary mb-4 flex items-center gap-2 border-b border-border pb-2">
              <span className="bg-primary/20 text-primary w-6 h-6 rounded-full flex items-center justify-center text-xs">4</span>
              Extras
            </h2>
            <FormField label="Observaciones (Opcional)" error={errors.observaciones?.message}>
              <Textarea {...register('observaciones')} placeholder="Alergias, peticiones especiales de decoración..." error={errors.observaciones?.message} className="bg-void/50" rows={3} />
            </FormField>
          </div>

          <div className="flex justify-end pt-6 border-t border-border gap-4">
            <Button type="button" variant="outline" onClick={() => navigate(-1)}>Cancelar</Button>
            <Button type="submit" isLoading={isSubmitting} className="min-w-[150px] shadow-lg shadow-primary/25">
              Confirmar Solicitud
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
