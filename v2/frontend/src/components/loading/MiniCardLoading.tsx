import React from 'react';
import { Card } from 'flowbite-react';
import Link from 'next/link';
import LoadingDiv, { loadingClassName } from '../LoadingDiv';

export interface MiniCardProps {
  title: string;
  icon: any,
  href?: string,
  className?: string
}

export function MiniCardLoading({ title, icon: Icon, href, className }: MiniCardProps) {
  return href ? (
    <Link href={href}>
      <MiniCardComponent title={title} icon={Icon} className={className} />
    </Link>
  ) : (
    <button title={title}>
      <MiniCardComponent title={title} icon={Icon} className={className} />
    </button>
  );
}

export function MiniCardLoadingSkeleton() {
  return <Card className={` flex-shrink-0  w-24 h-24 md:w-28 lg:w-32 ${loadingClassName} bg-gray-300 dark:bg-black-3`}>
  </Card>
}

function MiniCardComponent({ title, icon: Icon, className }: MiniCardProps) {
  return (<Card className={`text-primary-2 flex-shrink-0 bg-gray-100 hover:scale-110 hover:text-primary-1 transform transition-transform p-1 cursor-pointer w-24 h-24 md:w-28 lg:w-32 dark:bg-black-3  border border-gray-200 dark:border-black-3 ${className}`}>
    <div className='flex justify-center items-center'>
      <Icon className='w-6 h-6' />
    </div>
    <div className='flex justify-center text-center'>
      <p className='text-sm md:text-md font-semibold'>{title}</p>
    </div>
  </Card>);
}