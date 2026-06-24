import { Link } from 'react-router-dom';
import { Button } from '../components/ui/Button';

export default function NotFound() {
  return (
    <div className="min-h-screen bg-void flex flex-col items-center justify-center text-center px-4">
      <h1 className="text-9xl font-display font-bold text-primary-surface mb-4">404</h1>
      <h2 className="text-2xl font-semibold text-text-primary mb-6">Página no encontrada</h2>
      <p className="text-text-secondary mb-8 max-w-md">
        Lo sentimos, la página que buscas no existe o ha sido movida.
      </p>
      <Link to="/">
        <Button>Volver al inicio</Button>
      </Link>
    </div>
  );
}
