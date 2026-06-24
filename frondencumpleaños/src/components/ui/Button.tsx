import { forwardRef } from 'react';
import type { ButtonHTMLAttributes } from 'react';
import { Loader2 } from 'lucide-react';
import { cn } from '../../utils/cn';

export interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost' | 'danger';
  size?: 'sm' | 'md' | 'lg';
  isLoading?: boolean;
}

export const Button = forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant = 'primary', size = 'md', isLoading, children, disabled, ...props }, ref) => {
    const variants = {
      primary: 'bg-primary text-white hover:bg-primary-light border border-primary-light/50 shadow-sm',
      secondary: 'bg-secondary text-white hover:bg-secondary/90 border border-secondary/50 shadow-sm',
      outline: 'bg-transparent text-text-primary border border-border-strong hover:bg-elevated',
      ghost: 'bg-transparent text-text-primary hover:bg-elevated border-transparent',
      danger: 'bg-danger text-white hover:bg-danger/90 border border-danger/50 shadow-sm',
    };

    const sizes = {
      sm: 'h-8 px-3 text-xs rounded-sm',
      md: 'h-10 px-4 py-2 text-sm rounded-md',
      lg: 'h-12 px-6 text-base rounded-lg',
    };

    return (
      <button
        ref={ref}
        disabled={disabled || isLoading}
        className={cn(
          'inline-flex items-center justify-center font-medium transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary focus-visible:ring-offset-2 focus-visible:ring-offset-void disabled:opacity-50 disabled:pointer-events-none',
          variants[variant],
          sizes[size],
          className
        )}
        {...props}
      >
        {isLoading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
        {children}
      </button>
    );
  }
);
Button.displayName = 'Button';
