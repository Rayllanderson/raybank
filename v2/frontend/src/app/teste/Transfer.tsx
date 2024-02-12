// pages/transfer/[amount].tsx

import { useRouter } from 'next/navigation';
import { useState } from 'react';

export default function TransferAmountPage() {
  const router = useRouter();
  const [amount, setAmount] = useState<number>(0);

  const handleTransfer = () => {
    router.push(`/teste/contacts`);
  };

  return (
    <div>
      <h1>Transferência</h1>
      <input type="number" title='vai' value={amount} onChange={(e) => setAmount(parseInt(e.target.value))} />
      <button onClick={handleTransfer}>Selecionar</button>
    </div>
  );
}
