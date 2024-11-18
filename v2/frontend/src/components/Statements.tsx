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

const emptyPage ={
    page: 0,
    size: 1,
    total: 0,
    items: []
}

export function Statements({ type }: Props) {
    const [openModal, setOpenModal] = useState(false)
    const [selectedStatement, setSelectedStatement] = useState<Statement | null>(null);
    const handleStatementClick = (statement: Statement) => {
        setSelectedStatement(statement);
        setOpenModal(true);
    };


    const [pagination, setPagination] = useState<Page<Statement>>(emptyPage);
    const [page, setPage] = useState(0);
    const [search, setSearch] = useState<string|null>(null);
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

    async function fetchItems() {
        setLoading(true);
    
        const data: Page<Statement> = await getAllCardStatementsWithToken(session?.token!, {
            type: type!,
            page: page,
            size: 10,
            sort: 'moment,desc',
            ...(search ? { search: search } : {}),
        });
    
        setPagination(prevPagination => {
            if (page === 0) {
                return {
                    ...data,
                    items: [...data.items]
                };
            } else {
                return {
                    ...data,
                    items: [...prevPagination.items, ...data.items]
                };
            }
        });
    
        setCanFetch(data.items.length > 0);
        setLoading(false);
    }
    useEffect(() => {

        fetchItems();
    }, [session?.token!, page, type]);

    
    useEffect(() => {
        setPagination(emptyPage)
        fetchItems();
    }, [search]);

    function handleScroll() {
        if (window.innerHeight + document.documentElement.scrollTop === document.documentElement.offsetHeight) {
            if (canFetch)
                setPage(prevPage => prevPage + 1);
        }
    }

    async function handlerSearchChange(e:any) {
        const searchTerm:string = e.target.value;
        setSearch(searchTerm);
    }

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [pagination]);


    return <div>
        <StatementDetailsModal statement={selectedStatement} show={openModal} setOpenModal={setOpenModal}/>
        <h1 className="text-2xl font-semibold font-mono">Histórico</h1>
        {pagination.items.length === 0 && !loading ? (
            <>
                {search &&  <div className='mt-2'>
                    <InputWithIcon icon={FaSearch} type="text" placeholder='Buscar' onChange={handlerSearchChange}/>
                </div>}

                <p className="text-gray-500 mt-5"> Sem transações</p>
            </>
        ) : (
            <>
                <div className='mt-2'>
                    <InputWithIcon icon={FaSearch} type="text" placeholder='Buscar' onChange={handlerSearchChange}/>
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
