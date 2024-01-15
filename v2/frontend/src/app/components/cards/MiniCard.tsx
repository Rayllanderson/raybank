import React from 'react';
import { Card } from 'flowbite-react';
import Link from 'next/link';

export interface MiniCardProps {
  title: string;
  icon: any,
  href: string,
  className?: string
}

export function MiniCard({ title, icon: Icon, href, className }: MiniCardProps) {
  return (
    <Link href={href}>
      <Card className={`text-primary-2 flex-shrink-0 bg-gray-100 hover:scale-110 hover:text-primary-1 transform transition-transform p-1 cursor-pointer w-24 h-24 md:w-28 lg:w-32 dark:bg-black-3  border border-gray-200 dark:border-black-3 ${className}`}>
        <div className='flex justify-center items-center'>
          <Icon className='w-6 h-6' />
        </div>
        <div className='flex justify-center text-center'>
          <p className='text-sm md:text-md font-mono font-semibold'>{title}</p>
        </div>
      </Card>
    </Link>
  );
}
