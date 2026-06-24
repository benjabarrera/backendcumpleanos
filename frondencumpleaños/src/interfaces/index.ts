export type RolSistema = 'ADMIN' | 'COORDINADOR' | 'STAFF' | 'CLIENTE';

export type EstadoEvento = 
  | 'SOLICITADO' 
  | 'EN_REVISION' 
  | 'CONFIRMADO' 
  | 'EJECUTADO' 
  | 'CANCELADO';

export type EstadoAsignacion = 
  | 'ASIGNADO' 
  | 'CONFIRMADO' 
  | 'EN_TURNO' 
  | 'FINALIZADO' 
  | 'AUSENTE';

export type TipoRegistroBitacora = 
  | 'INICIO' 
  | 'INCIDENCIA' 
  | 'NOVEDAD' 
  | 'CIERRE';

export type TipoPinata = 'PEQUEÑA' | 'MEDIANA' | 'GRANDE';

export interface AuthUser {
  idUsuario: number;
  username: string;
  rolSistema: RolSistema;
  nombre: string;
  apellido: string;
  idCliente: number | null;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface Cliente {
  idCliente: number;
  nombre: string;
  apellido: string;
  email: string;
  telefono?: string;
  direccion?: string;
  ciudad?: string;
  notas?: string;
  activo: boolean;
}

export interface ClienteRequest {
  nombre: string;
  apellido: string;
  email: string;
  telefono?: string;
  direccion?: string;
  ciudad?: string;
  notas?: string;
  activo?: boolean;
}

export interface RolPersonal {
  idRol: number;
  nombre: string;
  descripcion?: string;
  tarifaHora: number;
}

export interface Personal {
  idPersonal: number;
  idRol: number;
  rolNombre: string;
  nombre: string;
  apellido: string;
  email: string;
  telefono?: string;
  rut?: string;
  activo: boolean;
}

export interface PersonalRequest {
  idRol: number;
  nombre: string;
  apellido: string;
  email: string;
  telefono?: string;
  rut?: string;
  activo?: boolean;
}

export interface TipoFiesta {
  idTipoFiesta: number;
  nombre: string;
  descripcion?: string;
  colorHex: string;
  activo: boolean;
}

export interface MenuEvento {
  idMenu: number;
  idEvento: number;
  platosDulces: number;
  platosSalados: number;
  platosVeganos: number;
  platosCeliacos: number;
  tortas: number;
  bolsasSorpresa: number;
  tipoPinata: TipoPinata;
  cantidadPinatas: number;
  notasMenu?: string;
}

export interface Evento {
  idEvento: number;
  idCliente: number;
  clienteNombre: string;
  idTipoFiesta: number;
  tipoFiestaNombre: string;
  nombreCelebrado: string;
  fechaEvento: string;         // "YYYY-MM-DD"
  horaInicio: string;          // "HH:mm:ss"
  horaFin: string;
  lugar: string;
  cantidadNinos: number;
  cantidadNinosVeganos: number;
  cantidadNinosCeliacos: number;
  estado: EstadoEvento;
  observaciones?: string;
  menuEvento?: MenuEvento;
}

export interface EventoCreateRequest {
  idCliente: number;
  idTipoFiesta: number;
  nombreCelebrado: string;
  fechaEvento: string;
  horaInicio: string;
  horaFin: string;
  lugar: string;
  cantidadNinos: number;
  cantidadNinosVeganos?: number;
  cantidadNinosCeliacos?: number;
  observaciones?: string;
}

export interface EventoUpdateRequest extends Partial<EventoCreateRequest> {
  estado?: EstadoEvento;
}

export interface AgendaEvento {
  idAgenda: number;
  idEvento: number;
  horaMontaje: string;
  horaBienvenida: string;
  horaJuegos: string;
  horaTorta: string;
  horaDesmontaje: string;
  detalles?: string;
}

export interface AgendaEventoRequest {
  idEvento: number;
  horaMontaje: string;
  horaBienvenida: string;
  horaJuegos: string;
  horaTorta: string;
  horaDesmontaje: string;
  detalles?: string;
}

export interface MinutaEvento {
  idMinuta: number;
  idEvento: number;
  numeroMinuta: string;
  fechaEmision: string;
  contenidoHtml?: string;
  enviadaCliente: boolean;
  fechaEnvio?: string;
  firmaCliente: boolean;
}

export interface MinutaEventoRequest {
  idEvento: number;
  numeroMinuta: string;
  fechaEmision: string;
  contenidoHtml?: string;
  enviadaCliente?: boolean;
  fechaEnvio?: string;
  firmaCliente?: boolean;
}

export interface BitacoraEvento {
  idBitacora: number;
  idEvento: number;
  idPersonal?: number;
  personalNombre?: string;
  timestampLog: string;
  tipoRegistro: TipoRegistroBitacora;
  descripcion: string;
}

export interface BitacoraEventoRequest {
  idEvento: number;
  idPersonal?: number;
  timestampLog: string;
  tipoRegistro: TipoRegistroBitacora;
  descripcion: string;
}

export interface AsignacionPersonal {
  idAsignacion: number;
  idEvento: number;
  idPersonal: number;
  personalNombre: string;
  rolNombre: string;
  horaEntrada?: string;
  horaSalida?: string;
  horaEntradaReal?: string;
  horaSalidaReal?: string;
  estado: EstadoAsignacion;
  notas?: string;
}

export interface AsignacionPersonalRequest {
  idEvento: number;
  idPersonal: number;
  horaEntrada?: string;
  horaSalida?: string;
  horaEntradaReal?: string;
  horaSalidaReal?: string;
  estado?: EstadoAsignacion;
  notas?: string;
}

export interface DashboardMetrics {
  totalEventos: number;
  eventosSolicitados: number;
  eventosEnRevision: number;
  eventosConfirmados: number;
  eventosEjecutados: number;
  eventosCancelados: number;
  totalClientes: number;
  totalPersonal: number;
  personalActivo: number;
  eventosEstaSemana: Evento[];
}
