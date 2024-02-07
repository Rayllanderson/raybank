import LinkButton from '../Buttons/LinkButton';
import { Card } from './Card';
import CardHeader from './CardHeader';


export default function NoCreditCardCard() {
  return (
    <Card>

      <CardHeader title='Cartão de Crédito' />

      <div className='mt-5 flex flex-col gap-2'>

        <p className='text-lg font-mono dark:text-white'>Sem cartão de crédito?</p>
        <LinkButton gradientMonochrome={'purple'} href={'/cards'}> Pedir Cartão </LinkButton>
      </div>
    </Card>
  )
}