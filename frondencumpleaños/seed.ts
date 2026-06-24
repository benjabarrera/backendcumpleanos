import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

async function seed() {
  console.log('🌱 Iniciando población de datos (Seed)...');

  try {
    // 1. Usuarios (Admin y Cliente)
    console.log('Creando Usuarios...');
    await api.post('/usuarios-sistema', {
      username: 'admin',
      passwordHash: 'admin123',
      rolSistema: 'ADMIN',
      activo: true
    }).catch(e => console.log('Admin ya existe o error:', e.response?.data || e.message));

    await api.post('/usuarios-sistema', {
      username: 'cliente',
      passwordHash: 'cliente123',
      rolSistema: 'CLIENTE',
      activo: true
    }).catch(e => console.log('Cliente ya existe o error:', e.response?.data || e.message));

    // 2. Clientes
    console.log('Creando Clientes Ficticios...');
    const clientes = [
      { nombre: 'María', apellido: 'Gómez', email: 'maria@gmail.com', telefono: '987654321', direccion: 'Calle 123', ciudad: 'Santiago', activo: true },
      { nombre: 'Juan', apellido: 'Pérez', email: 'juan@gmail.com', telefono: '912345678', direccion: 'Avenida Providencia', ciudad: 'Santiago', activo: true },
      { nombre: 'Valeria', apellido: 'Fuentes', email: 'valeria@gmail.com', telefono: '955555555', direccion: 'Las Condes 44', ciudad: 'Santiago', activo: true },
    ];
    let createdClientes = [];
    for (const c of clientes) {
      try {
        const { data } = await api.post('/clientes', c);
        createdClientes.push(data);
      } catch(e) { console.log('Error creando cliente:', e); }
    }

    // 3. Personal y Roles
    console.log('Creando Roles de Personal...');
    let idRolAnimador = 1;
    let idRolDecorador = 2;
    try {
      const res1 = await api.post('/roles-personal', { nombre: 'Animador Principal', descripcion: 'Encargado de juegos', tarifaHora: 15000 });
      idRolAnimador = res1.data.idRol;
      const res2 = await api.post('/roles-personal', { nombre: 'Decorador', descripcion: 'Arma los escenarios', tarifaHora: 12000 });
      idRolDecorador = res2.data.idRol;
    } catch(e) { console.log('Error roles:', (e as Error).message); }

    console.log('Creando Miembros del Personal...');
    try {
      await api.post('/personal', { idRol: idRolAnimador, nombre: 'Carlos', apellido: 'Ruiz', email: 'carlos@staff.com', activo: true });
      await api.post('/personal', { idRol: idRolDecorador, nombre: 'Ana', apellido: 'Soto', email: 'ana@staff.com', activo: true });
    } catch(e) { console.log('Error personal:', (e as Error).message); }

    // 4. Tipos de Fiesta y Eventos
    console.log('Creando Tipos de Fiesta...');
    let idTipoAventura = 1;
    try {
      const resFiesta = await api.post('/tipos-fiesta', { nombre: 'Aventura Espacial', descripcion: 'Fiesta temática del espacio', colorHex: '#1E3A8A', activo: true });
      idTipoAventura = resFiesta.data.idTipoFiesta;
    } catch(e) { console.log('Error tipo fiesta:', (e as Error).message); }

    console.log('Creando Eventos...');
    if (createdClientes.length > 0) {
      const eventos = [
        { idCliente: createdClientes[0].idCliente, idTipoFiesta: idTipoAventura, nombreCelebrado: 'Tomasito', fechaEvento: '2026-07-20', horaInicio: '15:00:00', horaFin: '18:00:00', lugar: 'Salón Central', cantidadNinos: 25 },
        { idCliente: createdClientes[1].idCliente, idTipoFiesta: idTipoAventura, nombreCelebrado: 'Sofia', fechaEvento: '2026-07-25', horaInicio: '16:00:00', horaFin: '19:00:00', lugar: 'Patio Trasero', cantidadNinos: 15 },
        { idCliente: createdClientes[2].idCliente, idTipoFiesta: idTipoAventura, nombreCelebrado: 'Lucas', fechaEvento: '2026-08-01', horaInicio: '10:00:00', horaFin: '13:00:00', lugar: 'Parque Bicentenario', cantidadNinos: 30 }
      ];
      for (const ev of eventos) {
        try {
          await api.post('/eventos', ev);
        } catch(e) { console.log('Error creando evento:', (e as Error).message); }
      }
    }

    console.log('✅ Base de datos poblada exitosamente.');
    console.log('   Credenciales creadas:');
    console.log('   Admin -> User: admin | Pass: admin123');
    console.log('   Cliente -> User: cliente | Pass: cliente123');
  } catch (error) {
    console.error('❌ Error fatal en el seed:', (error as Error).message);
  }
}

seed();
