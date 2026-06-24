import { forwardRef } from 'react';
import type { InputHTMLAttributes } from 'react';
import { cn } from '../../utils/cn';

export interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: string;
}

export const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ className, error, ...props }, ref) => {
    return (
      <div className="relative w-full">
        <input
          ref={ref}
          className={cn(
            'flex h-10 w-full rounded-md border border-border bg-surface px-3 py-2 text-sm text-text-primary placeholder:text-text-muted transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary focus-visible:border-primary disabled:cursor-not-allowed disabled:opacity-50',
            error && 'border-danger focus-visible:ring-danger focus-visible:border-danger',
            className
          )}
          {...props}
        />
        {error && (
          <span className="text-xs text-danger mt-1 block">
            {error}
          </span>
        )}
      </div>
    );
  }
);
Input.displayName = 'Input';
