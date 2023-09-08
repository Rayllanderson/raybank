package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.responses.user.UserResponseDto;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FindAllUsersService {

    private final UserRepository userRepository;

    public List<UserResponseDto> findAll(){
        return userRepository.findAll().stream().map(user -> {
            UserResponseDto userDto = UserResponseDto.fromUser(user);
            BankAccountDto bankDto = BankAccountDto.fromBankAccount(user.getBankAccount());
            CreditCardDto creditCardDto = CreditCardDto.fromCreditCard(user.getBankAccount().getCreditCard());
            bankDto.setCreditCardDto(creditCardDto);
            userDto.setBankAccountDto(bankDto);
            return userDto;
        }).collect(Collectors.toList());
    }
}
