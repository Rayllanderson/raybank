import React from 'react'

export default function NotFound() {
  return (
    <div className="flex flex-col mt-5 text-center text">
      <div className="text-lg">Conta não encontrada ou ainda não foi criada. </div>
      <div>Pode ser que ela ainda esteja em processo de criação. Por favor, tente novamente em alguns segundos.</div>
      <div>Se o problema persistir, entre em contato conosco para obter assistência adicional. Agradecemos sua paciência!</div>
    </div>
  )
}
