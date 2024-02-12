// pages/transfer/contacts.tsx

import { GetServerSideProps } from 'next';

interface Contact {
  id: string;
  name: string;
}

interface TransferContactsProps {
  amount: number;
  contacts: Contact[];
}

export default function TransferContactsPage({ amount, contacts }: TransferContactsProps) {
  return (
    <div>
      <h1>Contatos</h1>
      <ul>
        {contacts.map((contact) => (
          <li key={contact.id}>{contact.name}</li>
        ))}
      </ul>
    </div>
  );
}

export const getServerSideProps: GetServerSideProps<TransferContactsProps> = async (context) => {
  const { amount } = context.query;

  // Simulação de busca de contatos do servidor usando a quantidade de transferência
  const contacts: Contact[] = [
    { id: '1', name: 'Contato 1' },
    { id: '2', name: 'Contato 2' },
    { id: '3', name: 'Contato 3' },
  ];

  return {
    props: {
      amount: typeof amount === 'string' ? parseInt(amount) : 0,
      contacts,
    },
  };
};
