import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { Input } from '../../components/ui/Input';
import { Alert } from '../../components/ui/Alert';
import api from '../../services/api';
import { Cake } from 'lucide-react';

const registerSchema = z.object({
  nombre: z.string().min(2, 'El nombre debe tener al menos 2 caracteres'),
  apellido: z.string().min(2, 'El apellido debe tener al menos 2 caracteres'),
  email: z.string().email('Debe ser un email válido'),
  telefono: z.string().min(8, 'El teléfono es muy corto').optional(),
  direccion: z.string().min(5, 'La dirección debe ser más detallada').optional(),
  ciudad: z.string().min(2, 'La ciudad debe tener al menos 2 caracteres').optional(),
  username: z.string().min(4, 'El usuario debe tener al menos 4 caracteres'),
  password: z.string().min(6, 'La contraseña debe tener al menos 6 caracteres'),
});

type RegisterForm = z.infer<typeof registerSchema>;

export default function RegisterPage() {
  const navigate = useNavigate();
  const [apiError, setApiError] = useState<string | null>(null);

  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<RegisterForm>({
    resolver: zodResolver(registerSchema)
  });

  const onSubmit = async (data: RegisterForm) => {
    try {
      setApiError(null);
      // 1. Crear el cliente
      const clienteResponse = await api.post('/clientes', { 
        nombre: data.nombre,
        apellido: data.apellido,
        email: data.email,
        telefono: data.telefono,
        direccion: data.direccion,
        ciudad: data.ciudad,
        activo: true 
      });
      
      const idCliente = clienteResponse.data.idCliente;

      // 2. Crear el usuario del sistema vinculado al cliente
      await api.post('/usuarios-sistema', {
        username: data.username,
        password: data.password,
        idCliente: idCliente,
        idPersonal: null,
        rolSistema: 'CLIENTE',
        activo: true
      });

      navigate('/login');
    } catch (error: any) {
      setApiError(error.response?.data?.message || 'Error al registrar el cliente');
    }
  };

  return (
    <div className="min-h-screen bg-void flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-surface border border-border rounded-xl shadow-2xl p-8 relative overflow-hidden">
        {/* Decorative elements */}
        <div className="absolute top-0 right-0 w-32 h-32 bg-primary/10 rounded-bl-full -z-10" />
        <div className="absolute bottom-0 left-0 w-24 h-24 bg-accent/10 rounded-tr-full -z-10" />

        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-12 h-12 bg-primary/20 rounded-full mb-4">
            <Cake className="w-6 h-6 text-primary" />
          </div>
          <h1 className="text-2xl font-bold text-text-primary">Registro de Cliente</h1>
          <p className="text-sm text-text-muted mt-2">Crea tu cuenta para organizar tu fiesta</p>
        </div>

        {apiError && (
          <Alert variant="danger" className="mb-6">
            {apiError}
          </Alert>
        )}

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-text-secondary mb-1">Nombre</label>
              <Input
                {...register('nombre')}
                error={errors.nombre?.message}
                placeholder="Juan"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-text-secondary mb-1">Apellido</label>
              <Input
                {...register('apellido')}
                error={errors.apellido?.message}
                placeholder="Pérez"
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-text-secondary mb-1">Email</label>
            <Input
              type="email"
              {...register('email')}
              error={errors.email?.message}
              placeholder="juan@ejemplo.com"
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-text-secondary mb-1">Nombre de Usuario</label>
              <Input
                {...register('username')}
                error={errors.username?.message}
                placeholder="juanperez"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-text-secondary mb-1">Contraseña</label>
              <Input
                type="password"
                {...register('password')}
                error={errors.password?.message}
                placeholder="******"
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-text-secondary mb-1">Teléfono</label>
            <Input
              {...register('telefono')}
              error={errors.telefono?.message}
              placeholder="+56 9 1234 5678"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-text-secondary mb-1">Dirección</label>
            <Input
              {...register('direccion')}
              error={errors.direccion?.message}
              placeholder="Av. Principal 123"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-text-secondary mb-1">Ciudad</label>
            <Input
              {...register('ciudad')}
              error={errors.ciudad?.message}
              placeholder="Santiago"
            />
          </div>

          <button
            type="submit"
            disabled={isSubmitting}
            className="w-full bg-primary hover:bg-primary-dark text-white font-medium py-2.5 px-4 rounded-md transition-all active:scale-[0.98] mt-6 disabled:opacity-50 flex items-center justify-center gap-2"
          >
            {isSubmitting ? (
              <>
                <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                Registrando...
              </>
            ) : (
              'Registrarse'
            )}
          </button>
        </form>

        <div className="mt-6 text-center text-sm text-text-muted">
          ¿Ya tienes una cuenta?{' '}
          <Link to="/login" className="text-primary hover:text-primary-light font-medium transition-colors">
            Inicia sesión
          </Link>
        </div>
      </div>
    </div>
  );
}
