import { forwardRef } from 'react';
import type { HTMLAttributes } from 'react';
import { cn } from '../../utils/cn';

interface BadgeProps extends HTMLAttributes<HTMLSpanElement> {
  variant?: 'default' | 'success' | 'warning' | 'danger' | 'info' | 'solicitado' | 'revision' | 'confirmado' | 'ejecutado' | 'cancelado';
}

export const Badge = forwardRef<HTMLSpanElement, BadgeProps>(
  ({ className, variant = 'default', children, ...props }, ref) => {
    const variants = {
      default: 'bg-elevated text-text-secondary border border-border-strong',
      success: 'bg-success/15 text-success border border-success/30',
      warning: 'bg-warning/15 text-warning border border-warning/30',
      danger: 'bg-danger/15 text-danger border border-danger/30',
      info: 'bg-info/15 text-info border border-info/30',
      solicitado: 'bg-solicitado/15 text-solicitado border border-solicitado/30',
      revision: 'bg-revision/15 text-revision border border-revision/30',
      confirmado: 'bg-confirmado/15 text-confirmado border border-confirmado/30',
      ejecutado: 'bg-ejecutado/15 text-ejecutado border border-ejecutado/30',
      cancelado: 'bg-cancelado/15 text-cancelado border border-cancelado/30',
    };

    return (
      <span
        ref={ref}
        className={cn(
          'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium',
          variants[variant],
          className
        )}
        {...props}
      >
        {children}
      </span>
    );
  }
);
Badge.displayName = 'Badge';
