import type { FC, ReactNode } from 'react';
import { cn } from '../../utils/cn';
import { AlertCircle, CheckCircle2, Info, AlertTriangle } from 'lucide-react';

interface AlertProps {
  title?: string;
  children: ReactNode;
  variant?: 'info' | 'success' | 'warning' | 'danger';
  className?: string;
}

export const Alert: FC<AlertProps> = ({ title, children, variant = 'info', className }) => {
  const icons = {
    info: <Info className="h-5 w-5 text-info" />,
    success: <CheckCircle2 className="h-5 w-5 text-success" />,
    warning: <AlertTriangle className="h-5 w-5 text-warning" />,
    danger: <AlertCircle className="h-5 w-5 text-danger" />,
  };

  const variants = {
    info: 'bg-info/10 border-info/20 text-info',
    success: 'bg-success/10 border-success/20 text-success',
    warning: 'bg-warning/10 border-warning/20 text-warning',
    danger: 'bg-danger/10 border-danger/20 text-danger',
  };

  return (
    <div className={cn('flex gap-3 rounded-lg border p-4', variants[variant], className)}>
      <div className="flex-shrink-0">{icons[variant]}</div>
      <div className="text-sm">
        {title && <h5 className="font-semibold mb-1">{title}</h5>}
        <div className="text-current opacity-90">{children}</div>
      </div>
    </div>
  );
};
