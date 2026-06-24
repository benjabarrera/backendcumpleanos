import { useEffect } from 'react';
import { X } from 'lucide-react';
import { cn } from '../../utils/cn';

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
  className?: string;
}

export const Modal: React.FC<ModalProps> = ({ isOpen, onClose, title, children, className }) => {
  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = 'unset';
    }
    return () => {
      document.body.style.overflow = 'unset';
    };
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 sm:p-0">
      <div 
        className="fixed inset-0 bg-void/80 backdrop-blur-sm transition-opacity"
        onClick={onClose}
      />
      
      <div className={cn(
        "relative w-full max-w-lg transform overflow-hidden rounded-xl bg-surface border border-border text-left align-middle shadow-modal transition-all animate-in zoom-in-95",
        className
      )}>
        <div className="flex items-center justify-between border-b border-border px-6 py-4">
          <h3 className="text-lg font-semibold text-text-primary">
            {title}
          </h3>
          <button
            onClick={onClose}
            className="rounded-full p-1 text-text-muted hover:bg-elevated hover:text-text-primary transition-colors focus:outline-none focus:ring-2 focus:ring-primary"
          >
            <X className="h-5 w-5" />
          </button>
        </div>
        
        <div className="px-6 py-4">
          {children}
        </div>
      </div>
    </div>
  );
};
