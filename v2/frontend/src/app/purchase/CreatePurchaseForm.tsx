'use client'
import GradientButton from '@/components/Buttons/GradientButton'
import { CurrencyInput } from '@/components/inputs/InputMoney'
import InputText from '@/components/inputs/InputText'
import Select from '@/components/inputs/Select'
import { CardDetails, CreatePurchaseFormData, createPurchaseFormSchema } from '@/types/Card'
import { ApiErrorException } from '@/types/Error'
import { zodResolver } from '@hookform/resolvers/zod'
import { LabelProps } from 'flowbite-react'
import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import toast from 'react-hot-toast'


export default function CreatePurchaseForm({ card }: { card: CardDetails }) {
    const [paymentType, setPaymentType] = useState<"credit" | "debit">("credit");
    const { register, handleSubmit, watch, formState, setValue } = useForm<CreatePurchaseFormData>({ resolver: zodResolver(createPurchaseFormSchema) })
    const [isLoading, setIsLoading] = useState<boolean>(false)

    const onSubmit = async (data: CreatePurchaseFormData) => {
        setIsLoading(true)

        try {

            const response = await fetch('/api/purchase', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });
            
            if (!response.ok) {
                try {
                    const responseError = await response.json();
                    console.log('Erro da API:', responseError.message);
                    toast.error( responseError.message);
                } catch(err) {
                    toast.error('Ocorreu um erro desconhecido.');
                } finally {
                    return;
                }
            }

            toast.success("Compra realizada com sucesso");
        } catch (e: any) {
            console.error("Erro de validação", e.errors);
            toast.error(e.message)
        } finally {
            setIsLoading(false) // Set loading to false when the request completes

        }
    };

    if (!formState.isValid) {
        const fields = [
            'amount',
            'description',
            'installments',
            'payment_type',
            'card.number',
            'card.security_code',
            'card.expiry_date',
        ];
        fields.forEach((field) => {
            const fieldError = getNestedError(formState.errors, field);
            if (fieldError?.message) {
                toast.error(fieldError.message);
            }
        });

    }
    const handleCurrencyChange = (value: number) => {
        setValue('amount', value as number);
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className='className="flex max-w-md flex-col gap-4"'>
            <div>
                <Label>Valor da Compra</Label>
                <CurrencyInput
                    value={0}
                    onValueChange={handleCurrencyChange}
                    sizing='md'
                    placeholder="R$ 0,00"
                    className="mt-1"
                />
            </div>

            {/* Tipo de Pagamento */}
            <div>
                <Label>Tipo de Pagamento</Label>
                <Select
                    sizing='md'
                    {...register("payment_type", { required: "Selecione o tipo de pagamento" })}
                    value={paymentType}
                    onChange={(e) => setPaymentType(e.target.value as "credit" | "debit")}
                    className="mt-1"
                >
                    <option value="credit">Crédito</option>
                    <option value="debit">Débito</option>
                </Select>
            </div>

            {/* Parcelas (apenas se for pagamento por crédito) */}
            {paymentType === "credit" && (
                <div>
                    <Label>Número de Parcelas</Label>
                    <Select
                        sizing="md"
                        {...register("installments", {
                            required: "Número de parcelas é obrigatório",
                            valueAsNumber: true,  // Garante que o valor seja tratado como número
                        })}
                    >
                        {Array.from({ length: 12 }, (_, i) => i + 1).map((num) => (
                            <option key={num} value={num}>
                                {num} {"x Sem Juros"}
                            </option>
                        ))}
                    </Select>
                </div>
            )}
            {/* Descrição */}
            <div>
                <Label >Descrição</Label>
                <InputText
                    sizing='md'
                    placeholder="Ex: Mercado Livre"
                    {...register("description", { required: "Descrição é obrigatória" })}
                    className="mt-1"
                />
            </div>

            {/* Dados do Cartão */}
            <div>
                <Label>Número do Cartão</Label>
                <InputText
                    sizing='md'
                    placeholder="Número do cartão"
                    {...register("card.number", { required: "Número do cartão é obrigatório" })}
                    className="mt-1"
                    defaultValue={card.number}
                />
            </div>

            <div>
                <Label >Código de Segurança (CVV)</Label>
                <InputText
                    sizing='md'
                    placeholder="Código de segurança"
                    {...register("card.security_code", { required: "Código de segurança é obrigatório" })}
                    className="mt-1"
                    defaultValue={card.securityCode}
                />
            </div>

            <div>
                <Label >Data de Validade</Label>
                <InputText
                    sizing="md"
                    placeholder="MM/AA"
                    {...register("card.expiry_date", { required: "Data de validade é obrigatória" })}
                    className="mt-1"
                    defaultValue={formatExpiryDate(card.expiryDate)}
                />
            </div>

            <div className="mt-5">
                <GradientButton loading={isLoading} text='Realizar Compra' />
            </div>
        </form>
    )
}

const Label: React.FC<LabelProps> = ({ htmlFor, children, className = '' }) => {
    return (
        <label
            htmlFor={htmlFor}
            className={`mt-2 block text-sm font-medium text-gray-700 dark:text-gray-200 ${className}`}
        >
            {children}
        </label>
    );
};

const formatExpiryDate = (date: string | undefined) => {
    if (date) {
        const [year, month] = date.split('-');
        return `${month}/${year}`;
    }
};

const handleFormErrors = (errors: any) => {
    Object.keys(errors).forEach((field) => {
        const error = errors[field];
        if (error && typeof error === 'object' && !Array.isArray(error)) {
            handleFormErrors(error);
        } else if (error?.message) {
            toast.error(error.message);
        }
    });
};

// Função auxiliar para acessar erros de campos aninhados
const getNestedError = (errors: any, path: string) => {
    const pathParts = path.split('.');
    let error = errors;
    for (let part of pathParts) {
        error = error?.[part];
    }
    return error;
};
