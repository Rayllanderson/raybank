package com.rayllanderson.raybank.external.boleto;

import com.rayllanderson.raybank.boleto.BoletoRepository;
import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.boleto.controllers.payment.BoletoPaymentRequest;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
//
//    public void pay(BoletoPaymentRequest request) {
//        var boleto = boletoRepository.findByBarCode(request.getCode()).orElseThrow(() -> {
//            log.info("Falha ao pagar boleto: Boleto {} não encontrado", request.getCode());
//            throw new RaybankExternalException.BoletoNotFound("Boleto " + request.getCode() + " was not found");
//        });
//
//        var payerBankAccount = getBankAccount(request);
//
//        log.info("Realizando pagamento boleto {} em conta {}", boleto, payerBankAccount);
//
////        payerBankAccount.pay(boleto);
//        bankAccountRepository.save(payerBankAccount);
//        boletoRepository.save(boleto);
//    }
//
//    private BankAccount getBankAccount(BoletoPaymentRequest request) {
//        return userRepository.findById(request.getOwnerId()).orElseThrow(() -> {
//            log.info("Falha ao pagar boleto {}: Usuário pagador id {} não encontrado", request.getCode(), request.getCode());
//            throw new BadRequestException("Pagador não encontrado");
//        }).getBankAccount();
//    }


    public static void main(String[] args) {
        int length = 2;
        Square square = x -> x * x;
        int a = square.calculate(length);
        System.out.println(a);
    }

    public interface Square {
        int calculate(int x);
    }
}
