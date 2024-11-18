import React, { forwardRef, InputHTMLAttributes } from 'react';

export interface SelectProps extends InputHTMLAttributes<HTMLSelectElement> {
  sizing?: 'sm' | 'md' | 'lg';
  className?: string
}

const Select: React.ForwardRefRenderFunction<HTMLSelectElement, SelectProps> = ({ sizing = 'lg', className, ...props }, ref) => {


  return (
    <select
      {...props}
      ref={ref}
      className={`rounded-md w-full pl-2 border-0 bg-gray-100 dark:bg-black-3 dark:text-white placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-primary-1 ${getSize(sizing)} ${className}`}
    />
  );
}

export default forwardRef(Select);

const getSize = (size: 'sm' | 'md' | 'lg') => {
  if (size === 'lg')
    return 'h-12 md:h-14 lg:h-16 text-lg md:text-lg lg:text-xl'
  if (size === 'md')
    return 'h-10 md:h-12 lg:h-14 lg:text-lg'
  if (size === 'sm')
    return 'h-8 md:h-10 lg:h-12'
}