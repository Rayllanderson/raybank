import React from 'react';
import { Card } from 'flowbite-react';

export interface MiniCardProps {
  title: string;
  icon: any
}

export function MiniCard({title, icon: Icon}: MiniCardProps) {
  return (
    <Card className="text-primary-2 bg-gray-100 hover:scale-110 hover:text-primary-1 transform transition-transform p-1 cursor-pointer w-24 h-24 md:w-28 lg:w-32 dark:bg-black-3">
      <div className='flex justify-center items-center'>
        <Icon className='w-6 h-6' />
      </div>
      <div className='flex justify-center'>
        <p className='text-sm md:text-md font-mono font-semibold'>{title}</p>
      </div>
    </Card>
  );
}
