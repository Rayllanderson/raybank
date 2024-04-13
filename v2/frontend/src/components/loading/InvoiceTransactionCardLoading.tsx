import React from 'react';
import { InvoiceTransaction } from '@/types/Invoice';
import { formartToDayMonth } from '@/utils/DateFormatter';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import Separator from '@/components/Separator';
import Link from 'next/link';
import LoadingDiv from '@/components/LoadingDiv';


export function InvoiceTransactionCardLoading() {

  return (
    <>
      <Content />
    </>
  );
}
function Content() {
  return (
    <>
      <div className={`flex justify-between items-center w-full`}>
        <div className="flex space-x-4">
          <div className={`w-8`}>
            <LoadingDiv className='rounded-md'/>
          </div>

          <div className={`w-24`}>
            <LoadingDiv className='rounded-md'/>
          </div>
        </div>

        <div>
          <div className={`w-8`}>
            <LoadingDiv className='rounded-md'/>
          </div>
        </div>

      </div>
      <Separator />
    </>
  );
}
