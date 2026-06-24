import type { FC } from 'react';
import { cn } from '../../utils/cn';

interface AvatarProps {
  name: string;
  src?: string;
  size?: 'sm' | 'md' | 'lg';
  className?: string;
}

export const Avatar: FC<AvatarProps> = ({ name, src, size = 'md', className }) => {
  const getInitials = (n: string) => {
    const parts = n.split(' ').filter(Boolean);
    if (parts.length === 0) return '?';
    if (parts.length === 1) return parts[0].substring(0, 2).toUpperCase();
    return (parts[0][0] + parts[parts.length - 1][0]).toUpperCase();
  };

  const sizes = {
    sm: 'h-8 w-8 text-xs',
    md: 'h-10 w-10 text-sm',
    lg: 'h-12 w-12 text-base',
  };

  return (
    <div className={cn(
      'relative flex shrink-0 overflow-hidden rounded-full bg-primary-surface border border-primary/20 items-center justify-center font-medium text-primary-light',
      sizes[size],
      className
    )}>
      {src ? (
        <img src={src} alt={name} className="h-full w-full object-cover" />
      ) : (
        <span>{getInitials(name)}</span>
      )}
    </div>
  );
};
