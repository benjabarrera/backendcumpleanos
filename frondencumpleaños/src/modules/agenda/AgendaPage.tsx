import { useEffect, useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { eventoService } from '../../services/evento.service';
import type { EstadoEvento, Evento } from '../../interfaces';
import { useNavigate } from 'react-router-dom';

const getEventColor = (estado: EstadoEvento) => {
  switch(estado) {
    case 'SOLICITADO': return '#f59e0b';
    case 'EN_REVISION': return '#3b82f6';
    case 'CONFIRMADO': return '#10b981';
    case 'EJECUTADO': return '#8b5cf6';
    case 'CANCELADO': return '#ef4444';
    default: return '#7C3AED';
  }
};

export default function AgendaPage() {
  const [eventos, setEventos] = useState<Evento[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    eventoService.getAll().then(setEventos).catch(console.error);
  }, []);

  const calendarEvents = eventos.map(e => ({
    id: String(e.idEvento),
    title: `${e.nombreCelebrado} - ${e.tipoFiestaNombre}`,
    start: `${e.fechaEvento}T${e.horaInicio}`,
    end: `${e.fechaEvento}T${e.horaFin}`,
    color: getEventColor(e.estado),
    extendedProps: { ...e }
  }));

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-display font-bold text-text-primary">Agenda Calendario</h1>
      <div className="bg-surface border border-border rounded-2xl p-6 shadow-sm overflow-hidden">
        <FullCalendar
          plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
          initialView="timeGridWeek"
          headerToolbar={{
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
          }}
          events={calendarEvents}
          eventClick={(info) => navigate(`/admin/eventos/${info.event.id}`)}
          height="auto"
          slotMinTime="08:00:00"
          slotMaxTime="22:00:00"
          allDaySlot={false}
          locale="es"
          buttonText={{
            today: 'Hoy',
            month: 'Mes',
            week: 'Semana',
            day: 'Día',
          }}
        />
      </div>
    </div>
  );
}
