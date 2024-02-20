'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { useTransferTransactionContext } from '@/context/TransferContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useRef, useState } from 'react'
import { isPixKeyValid } from '@/validators/PixKeyValidator';
import { getPixKeyTypeAsStringForTransfer } from '@/utils/PixKeyUtil';
import TextHeaderForm from '@/components/TextHeaderForm';
import { Contact } from '@/types/Contact';
import ContactsList from '../../app/transfer/contacts/ContactList';
import { useFindAccountContext } from '@/context/FindAccountContext';
import toast from 'react-hot-toast';
import { ImSpinner2 } from 'react-icons/im';
import TextHeaderFormLoading from '@/components/loading/TextHeaderFormLoading';
import LoadingDiv from '@/components/LoadingDiv';
import ContactsListLoading from './ContactListLoading';

export default function ContactFormLoading() {

  return (
    <>
      <Container>
        <Card>
          <TextHeaderFormLoading
            title={
              <div className="flex items-center">
                <p>Para quem você quer transferir  &nbsp;</p>
                <div className='w-14'>
                  <LoadingDiv className="rounded-md" />
                </div>
              </div>
            }
            subtitle='Encontre um contato na sua lista ou inicie uma nova transferência'
          />

          <div className="mt-4 flex flex-col gap-3">
            <InputText placeholder='Conta ou chave Pix' disabled />

            <div className='flex'>
              <NextButton isDisabled={true} />
            </div>
          </div>
        </Card>

        <ContactsListLoading />
      </Container>
    </>
  );
}

const NextButton = ({ isDisabled }: { isDisabled: boolean }) => (
  <Button color='primary' disabled={isDisabled} type='submit' className={`w-full`}>
    <p>Transferir</p>
  </Button>
);