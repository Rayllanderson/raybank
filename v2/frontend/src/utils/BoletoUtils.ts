const BoletoUtils = {
    formartStatus
}

function formartStatus(status: string): string {
    switch (status) {
        case 'WAITING_PAYMENT':
            return 'Aguardando Pagamento';
        case 'EXPIRED':
            return 'Expirado';
        case 'PAID':
            return 'Pago';
        case 'PROCESSING':
            return 'Processando';
        case 'PROCESSING_FAILURE':
            return 'Falha no Processamento';
        case 'REFUNDED':
            return 'Reembolsado';
        default:
            return 'Status Desconhecido';
    }
}

export default BoletoUtils