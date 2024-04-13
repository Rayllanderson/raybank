import React from 'react';
import { Container } from '@/components/Container';
import { loadingClassName } from '../LoadingDiv';

export function InstallmentCardLoading() {
    return <Container>
        <div className={`rounded-lg shadow-md h-80 bg-gray-300 dark:bg-black-3 ${loadingClassName}`}>
            <></>
        </div>
    </Container>;
}
