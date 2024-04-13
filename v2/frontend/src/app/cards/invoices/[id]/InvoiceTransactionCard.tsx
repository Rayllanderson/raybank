import React from 'react';
import { InvoiceTransaction } from '@/types/Invoice';
import { formartToDayMonth } from '@/utils/DateFormatter';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import Separator from '@/components/Separator';
import Link from 'next/link';

const isCredit = (transaction: InvoiceTransaction): boolean => {
  return transaction.type === 'REFUND' || transaction.type === 'INVOICE_PAYMENT' || transaction.type === 'REMAINING';
};
export function InvoiceTransactionCard({ transaction }: { transaction: InvoiceTransaction; }) {

  return (
    <>{transaction.planId === undefined ?
      (<Content transaction={transaction} />)
      : (
        <Link href={`/plan/${transaction.planId}`}>
          <Content transaction={transaction} />
        </Link>
      )}
    </>
  );
}
function Content({ transaction }: { transaction: InvoiceTransaction; }) {
  const getBg = isCredit(transaction) ? 'text-green-500' : '';
  return (
    <>
      <div className={`flex justify-between items-center w-full ${getBg}`}>
        <div className="flex space-x-4">
          <p className={`text-gray-500 ${getBg}`}>{formartToDayMonth(transaction.occuredOn)}</p>
          <p>{transaction.description}</p>
        </div>

        <div>
          <p>{MoneyFormatter.format(transaction.value)}</p>
        </div>

      </div>
      <Separator />
    </>
  );
}
