
import { cn } from '../../utils/cn';

interface FormFieldProps {
  label: string;
  error?: string;
  children: React.ReactNode;
  className?: string;
  required?: boolean;
}

export const FormField = ({ label, children, className, required }: FormFieldProps) => {
  return (
    <div className={cn('space-y-2 mb-4', className)}>
      <label className="text-sm font-medium text-text-primary">
        {label}
        {required && <span className="text-danger ml-1">*</span>}
      </label>
      {children}
    </div>
  );
};
