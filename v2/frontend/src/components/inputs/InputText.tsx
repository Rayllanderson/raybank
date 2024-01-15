import React, { forwardRef, InputHTMLAttributes } from 'react';

interface InputTextProps extends InputHTMLAttributes<HTMLInputElement> {}

const InputText: React.ForwardRefRenderFunction<HTMLInputElement, InputTextProps> = ({ ...props }, ref) => {
  return (
    <input
      {...props}
      ref={ref}
      type="text"
      className='rounded-md w-full h-12 md:h-14 pl-2 lg:h-16 border-0 bg-gray-100 dark:bg-black-3 dark:text-white placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-primary-1 text-lg md:text-lg lg:text-xl'
    />
  );
}

export default forwardRef(InputText);