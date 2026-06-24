import { forwardRef } from 'react';
import type { SelectHTMLAttributes } from 'react';
import { cn } from '../../utils/cn';

export interface SelectProps extends SelectHTMLAttributes<HTMLSelectElement> {
  error?: string;
  options: { label: string; value: string | number }[];
}

export const Select = forwardRef<HTMLSelectElement, SelectProps>(
  ({ className, error, options, ...props }, ref) => {
    return (
      <div className="relative w-full">
        <select
          ref={ref}
          className={cn(
            'flex h-10 w-full rounded-md border border-border bg-surface px-3 py-2 text-sm text-text-primary transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary focus-visible:border-primary disabled:cursor-not-allowed disabled:opacity-50 appearance-none',
            error && 'border-danger focus-visible:ring-danger focus-visible:border-danger',
            className
          )}
          {...props}
        >
          <option value="" disabled>Seleccione una opción</option>
          {options.map((opt) => (
            <option key={opt.value} value={opt.value}>
              {opt.label}
            </option>
          ))}
        </select>
        <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-text-muted">
          <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7"></path></svg>
        </div>
        {error && (
          <span className="text-xs text-danger mt-1 block">
            {error}
          </span>
        )}
      </div>
    );
  }
);
Select.displayName = 'Select';
