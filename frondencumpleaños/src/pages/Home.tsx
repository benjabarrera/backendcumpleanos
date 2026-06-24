import { Link } from 'react-router-dom';
import { PartyPopper } from 'lucide-react';
import { Button } from '../components/ui/Button';

export default function Home() {
  return (
    <div className="min-h-screen bg-void flex flex-col">
      <header className="border-b border-border bg-surface/50 backdrop-blur sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2 text-primary-light font-display text-xl font-bold">
            <PartyPopper className="h-6 w-6" />
            Mi<span className="text-text-primary">Cumpleaños</span>
          </div>
          <div className="flex items-center gap-4">
            <Link to="/login" className="text-sm font-medium text-text-secondary hover:text-text-primary transition-colors">
              Iniciar Sesión
            </Link>
            <Link to="/registro">
              <Button size="sm">Crear Cuenta</Button>
            </Link>
          </div>
        </div>
      </header>

      <main className="flex-1">
        <section className="py-20 lg:py-32 text-center px-4">
          <h1 className="text-4xl md:text-6xl font-display font-bold text-text-primary mb-6 tracking-tight">
            Celebraciones que los niños <br className="hidden md:block"/>
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-primary-light to-secondary">
              recuerdan para siempre
            </span>
          </h1>
          <p className="text-lg md:text-xl text-text-secondary max-w-2xl mx-auto mb-10">
            Gestiona, organiza y disfruta de la mejor fiesta de cumpleaños sin complicaciones. Nosotros nos encargamos de la magia.
          </p>
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
            <Link to="/registro">
              <Button size="lg" className="w-full sm:w-auto px-8">Solicitar mi fiesta</Button>
            </Link>
            <Link to="/login">
              <Button variant="outline" size="lg" className="w-full sm:w-auto px-8">Ya soy cliente</Button>
            </Link>
          </div>
        </section>

        <section className="py-20 bg-surface border-y border-border">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="text-center mb-16">
              <h2 className="text-3xl font-display font-bold text-text-primary mb-4">¿Cómo funciona?</h2>
              <p className="text-text-secondary">Tres simples pasos para la fiesta perfecta</p>
            </div>
            <div className="grid md:grid-cols-3 gap-8">
              {[
                { title: 'Crea tu evento', desc: 'Elige fecha, lugar y tipo de fiesta.' },
                { title: 'Revisamos todo', desc: 'Confirmamos disponibilidad y menú.' },
                { title: '¡A celebrar!', desc: 'Nos encargamos de que sea inolvidable.' }
              ].map((step, i) => (
                <div key={i} className="bg-elevated p-8 rounded-2xl border border-border text-center">
                  <div className="h-12 w-12 rounded-full bg-primary/20 text-primary-light flex items-center justify-center mx-auto mb-6 text-xl font-bold">
                    {i + 1}
                  </div>
                  <h3 className="text-xl font-semibold mb-2">{step.title}</h3>
                  <p className="text-text-muted">{step.desc}</p>
                </div>
              ))}
            </div>
          </div>
        </section>
      </main>

      <footer className="py-8 bg-surface text-center border-t border-border">
        <p className="text-text-muted text-sm">&copy; {new Date().getFullYear()} MiCumpleaños. Todos los derechos reservados.</p>
      </footer>
    </div>
  );
}
