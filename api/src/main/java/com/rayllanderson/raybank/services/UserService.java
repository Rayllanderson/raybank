package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.requests.user.UserPutDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.responses.user.UserPostResponseDto;
import com.rayllanderson.raybank.dtos.responses.user.UserResponseDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.utils.StringUtil;
import com.rayllanderson.raybank.utils.UserUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFinderService userFinderService;

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
