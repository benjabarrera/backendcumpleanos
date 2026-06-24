
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useNavigate, Link } from 'react-router-dom';
import { Button } from '../../components/ui/Button';
import { Input } from '../../components/ui/Input';
import { FormField } from '../../components/forms/FormField';
import { useAuthStore } from '../../app/store/authStore';
import { authService } from '../../services/auth.service';
import { useUiStore } from '../../app/store/uiStore';
import { PartyPopper } from 'lucide-react';

const loginSchema = z.object({
  username: z.string().min(1, 'El usuario es requerido'),
  password: z.string().min(1, 'La contraseña es requerida'),
});

type LoginForm = z.infer<typeof loginSchema>;

export default function LoginPage() {
  const navigate = useNavigate();
  const login = useAuthStore((state) => state.login);
  const addToast = useUiStore((state) => state.addToast);
  
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<LoginForm>({
    resolver: zodResolver(loginSchema)
  });

  const onSubmit = async (data: LoginForm) => {
    try {
      const user = await authService.login(data);
      login(user);
      
      addToast({ type: 'success', message: `¡Bienvenido ${user.nombre}!` });
      
      if (user.rolSistema === 'CLIENTE') {
        navigate('/app/dashboard');
      } else {
        navigate('/admin/dashboard');
      }
    } catch (error) {
      addToast({ type: 'error', message: 'Credenciales inválidas. Intente nuevamente.' });
    }
  };

  return (
    <div className="min-h-screen bg-void flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-surface p-8 rounded-2xl border border-border shadow-2xl">
        <div className="flex flex-col items-center mb-8">
          <div className="h-16 w-16 bg-primary/20 text-primary-light rounded-full flex items-center justify-center mb-4">
            <PartyPopper className="h-8 w-8" />
          </div>
          <h2 className="text-2xl font-display font-bold text-text-primary">Iniciar Sesión</h2>
          <p className="text-sm text-text-secondary mt-2">Ingresa a tu panel de MiCumpleaños</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
          <FormField label="Usuario" required>
            <Input 
              {...register('username')} 
              placeholder="Ej. admin" 
              error={errors.username?.message}
            />
          </FormField>

          <FormField label="Contraseña" required>
            <Input 
              type="password" 
              {...register('password')} 
              placeholder="••••••••" 
              error={errors.password?.message}
            />
          </FormField>

          <Button type="submit" className="w-full" size="lg" isLoading={isSubmitting}>
            Entrar
          </Button>
        </form>

        <div className="mt-6 text-center text-sm text-text-muted">
          ¿No tienes una cuenta? <Link to="/registro" className="text-primary-light hover:underline">Regístrate</Link>
        </div>
        
        {/* Demo Credentials */}
        <div className="mt-8 p-4 bg-elevated rounded-lg border border-border-strong text-xs text-text-secondary text-center">
          <p className="font-semibold mb-2">Credenciales Demo</p>
          <p>Admin: admin / Admin2025!</p>
          <p>Staff: sofia.castro / Staff2025!</p>
        </div>
      </div>
    </div>
  );
}
