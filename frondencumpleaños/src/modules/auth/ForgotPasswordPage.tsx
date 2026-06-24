
import { Link } from 'react-router-dom';

export default function ForgotPasswordPage() {
  return (
    <div className="min-h-screen bg-void flex items-center justify-center p-4">
      <div className="text-center">
        <h1 className="text-2xl font-bold mb-4 text-text-primary">Recuperar Contraseña</h1>
        <p className="text-text-muted mb-8">En construcción</p>
        <Link to="/login" className="text-primary-light hover:underline">Volver a Login</Link>
      </div>
    </div>
  );
}
