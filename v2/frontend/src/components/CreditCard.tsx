import { formatCardNumber, formatExpirationDate, getAbbreviatedName } from '@/utils/CardUtils'
import Image from 'next/image'
import React from 'react'


interface Props {
    name: string,
    number: string,
    cvv: number,
    expiryDate: string
}

export default function CreditCard({ name, number, cvv, expiryDate }: Props) {
    return (
        <div className="w-full h-48 md:w-96 md:h-56 m-auto bg-primary-2 rounded-xl relative text-white shadow-2xl">

            <div className="w-full px-6 md:px-8 absolute top-3 md:top-8">

                <div className="flex justify-between">
                    <div>
                        <p className="font-light">
                            Nome
                        </p>
                        <p className="font-medium tracking-widest">
                            {getAbbreviatedName(name)}
                        </p>
                    </div>
                    <Image className="w-12 h-12 md:w-14 md:h-14"
                        width={10}
                        height={10}
                        alt='img'
                        src="./mc_symbol.svg" />
                </div>

                <div className="pt-4 md:pt-2">
                    <p className="font-light">
                        Número do Cartão
                    </p>
                    <p className="font-medium">
                        {formatCardNumber(number)}
                    </p>
                </div>

                <div className="pt-4 pr-6">
                    <div className="flex justify-between">
                        <div>
                            <p className="font-light text-xs">
                                Data Expiração
                            </p>
                            <p className="font-medium tracking-wider text-sm">
                                {formatExpirationDate(expiryDate)}
                            </p>
                        </div>

                        <div>
                            <p className="font-light text-xs">
                                Cód. de Segurança
                            </p>
                            <p className="font-bold text-sm">
                                {cvv}
                            </p>
                        </div>
                        <div></div>
                    </div>
                </div>

            </div>
        </div>
    )
}
