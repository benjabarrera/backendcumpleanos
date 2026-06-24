import { forwardRef } from 'react';
import type { TextareaHTMLAttributes } from 'react';
import { cn } from '../../utils/cn';

export interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  error?: string;
}

export const Textarea = forwardRef<HTMLTextAreaElement, TextareaProps>(
  ({ className, error, ...props }, ref) => {
    return (
      <div className="relative w-full">
        <textarea
          ref={ref}
          className={cn(
            'flex min-h-[80px] w-full rounded-md border border-border bg-surface px-3 py-2 text-sm text-text-primary placeholder:text-text-muted transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary focus-visible:border-primary disabled:cursor-not-allowed disabled:opacity-50',
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
Textarea.displayName = 'Textarea';
