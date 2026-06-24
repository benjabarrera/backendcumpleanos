import { useUiStore } from '../../app/store/uiStore';
import { AlertCircle, CheckCircle2, Info, X, AlertTriangle } from 'lucide-react';
import { cn } from '../../utils/cn';

export const ToastContainer = () => {
  const toasts = useUiStore((state) => state.toasts);
  const removeToast = useUiStore((state) => state.removeToast);

  return (
    <div className="fixed bottom-4 right-4 z-50 flex flex-col gap-2">
      {toasts.map((toast) => {
        const icons = {
          info: <Info className="h-5 w-5 text-info" />,
          success: <CheckCircle2 className="h-5 w-5 text-success" />,
          warning: <AlertTriangle className="h-5 w-5 text-warning" />,
          error: <AlertCircle className="h-5 w-5 text-danger" />,
        };

        const bgColors = {
          info: 'bg-elevated border-info/50',
          success: 'bg-elevated border-success/50',
          warning: 'bg-elevated border-warning/50',
          error: 'bg-elevated border-danger/50',
        };

        return (
          <div
            key={toast.id}
            className={cn(
              'flex items-center gap-3 rounded-lg border p-4 shadow-modal transition-all animate-in slide-in-from-right-full',
              bgColors[toast.type]
            )}
          >
            <div className="flex-shrink-0">{icons[toast.type]}</div>
            <p className="text-sm font-medium">{toast.message}</p>
            <button
              onClick={() => removeToast(toast.id)}
              className="ml-auto flex-shrink-0 text-text-muted hover:text-text-primary"
            >
              <X className="h-4 w-4" />
            </button>
          </div>
        );
      })}
    </div>
  );
};
