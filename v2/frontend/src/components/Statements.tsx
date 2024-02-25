'use client';
import React, { useEffect, useState } from 'react';
import InputWithIcon from './inputs/InputWithIcon';
import { FaSearch } from 'react-icons/fa';
import StatamentCard from './StatamentCard';
import { Statement } from '@/types/Statement';
import { getAllCardStatementsWithToken } from '@/services/StatementService';
import { useSession } from 'next-auth/react';
import { Page } from '@/types/Page';
import { StatementHeaderLoading } from './loading/StatementHeaderLoading';
import { StatementCreditCardBodyLoading } from './loading/StatementCreditCardBodyLoading';
import StatementDetailsModal from './modal/StatementDetailsModal';

interface Props {
    type: "card" | "account";
    statements?: Statement[];
}

export function Statements({ type }: Props) {
    const [openModal, setOpenModal] = useState(false)
    const [selectedStatement, setSelectedStatement] = useState<Statement | null>(null);
    const handleStatementClick = (statement: Statement) => {
        setSelectedStatement(statement);
        setOpenModal(true);
    };


    const [pagination, setPagination] = useState<Page<Statement>>({
        page: 0,
        size: 1,
        total: 0,
        items: []
    });
    const [page, setPage] = useState(0);
    const { data: session } = useSession();
    const [loading, setLoading] = useState(true);
    const [canFetch, setCanFetch] = useState(true);

    useEffect(() => {
        setPagination({
            page: 0,
            size: 1,
            total: 0,
            items: []
        });
        setPage(0)
    }, [type]);

    useEffect(() => {
        async function fetchItems() {
            setLoading(true);
            const data: Page<Statement> = await getAllCardStatementsWithToken(session?.token!, { type: type!, page: page, size: 10, sort: 'moment,desc' })
            setPagination(prevPagination => ({
                ...data,
                items: [...prevPagination.items, ...data.items]
            }));
            setCanFetch(data.items.length > 0);
            setLoading(false);
        }

        fetchItems();
    }, [session?.token!, page, type]);

    function handleScroll() {
        if (window.innerHeight + document.documentElement.scrollTop === document.documentElement.offsetHeight) {
            if (canFetch)
                setPage(prevPage => prevPage + 1);
        }
    }

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [pagination]);


    return <div>
        <StatementDetailsModal statement={selectedStatement} show={openModal} setOpenModal={setOpenModal}/>
        <h1 className="text-2xl font-semibold font-mono">Histórico</h1>
        {pagination.items.length === 0 && !loading ? (
            <p className="text-gray-500 mt-5"> Sem transações até o momento</p>
        ) : (
            <>
                <div className='mt-2'>
                    <InputWithIcon icon={FaSearch} type="text" placeholder='Buscar' />
                </div>

                <div className='space-y-2 mt-5'>
                    {pagination.items.map((statement: Statement) => {
                        return (
                            <StatamentCard statement={statement} key={statement.id} type={type} onClick={() => handleStatementClick(statement)}/>
                        )
                    })}
                </div>
                {loading && (
                    <div className="space-y-2 mt-2">
                        {
                            [1, 2, 3, 4, 5].map(i => {
                                return <div className="p-4 bg-gray-100 dark:bg-black-2 shadow-lg rounded-md" key={i}>
                                    <StatementHeaderLoading />
                                    <StatementCreditCardBodyLoading />
                                </div>
                            })
                        }
                    </div>
                )
                }
            </>
        )
        }
    </div>;
}
