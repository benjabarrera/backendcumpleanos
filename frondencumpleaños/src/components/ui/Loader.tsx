import { Spinner } from './Spinner';

export const Loader = ({ text = 'Cargando...' }: { text?: string }) => {
  return (
    <div className="flex flex-col items-center justify-center min-h-[200px] w-full gap-4">
      <Spinner size="lg" />
      <p className="text-text-muted text-sm font-medium animate-pulse">{text}</p>
    </div>
  );
};
