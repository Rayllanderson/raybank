package com.rayllanderson.raybank.users.services.find;

import com.rayllanderson.raybank.bankaccount.controllers.reponses.BankAccountDto;
import com.rayllanderson.raybank.users.controllers.find.UserResponseDto;
import com.rayllanderson.raybank.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FindAllUsersService {

    private final UserRepository userRepository;
//todo::deletar
    public List<UserResponseDto> findAll(){
        return userRepository.findAll().stream().map(user -> {
            UserResponseDto userDto = UserResponseDto.fromUser(user);
            BankAccountDto bankDto = BankAccountDto.fromBankAccount(user.getBankAccount());
//            CreditCardDto creditCardDto = CreditCardDto.fromCreditCard(user.getBankAccount().getCard());
//            bankDto.setCreditCardDto(creditCardDto);
            userDto.setBankAccountDto(bankDto);
            return userDto;
        }).collect(Collectors.toList());
    }
}
