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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;
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

    @Transactional
    public UserPostResponseDto register (UserPostDto userDto){
        this.assertThatUsernameNotExists(userDto.getUsername());
        User userToBeSaved =  userDto.toUser();
        userToBeSaved = userRepository.save(userToBeSaved);
        userToBeSaved.setBankAccount(bankAccountService.createAccountBank(userToBeSaved));
//        userToBeSaved.setPassword(encoder.encode(userDto.getPassword()));
        userToBeSaved.setAuthorities("ROLE_USER");
        userToBeSaved = userRepository.save(userToBeSaved);
        return UserPostResponseDto.fromUser(userToBeSaved);
    }

    @Transactional
    public void deleteById(String id) throws BadRequestException {
        userFinderService.findById(id);
        userRepository.deleteById(id);
    }

    /**
     * Use this method if you want to update username, email or name
     *
     * Need to set id before send
     *
     * @param userDto user from the body;
     *
     * @throws BadRequestException - if user update username and username has already taken.
     */
    @Transactional
    public void updateNameOrUsername(UserPutDto userDto) throws BadRequestException {
        User userFromDataBase = userFinderService.findById(userDto.getId());
        boolean hasUpdatedUsername = StringUtil.notMatches(userDto.getUsername(), userFromDataBase.getUsername());
        if (hasUpdatedUsername){
            this.assertThatUsernameNotExists(userDto.getUsername());
        }
        UserUpdater.updateNameOrUsername(userDto, userFromDataBase);
    }

    /**
     * Use this method if you want to update ONLY password.
     */
    @Transactional
    public void updatePassword(UserPutDto user) {
        if(StringUtil.isEmpty(user.getPassword())){
            throw new IllegalArgumentException("Senha está vazia");
        }
        User userFromDataBase = userFinderService.findById(user.getId());
        UserUpdater.updatePassword(user, userFromDataBase);
//        userFromDataBase.setPassword(encoder.encode(userFromDataBase.getPassword()));
        this.userRepository.save(userFromDataBase);
    }

    /**
     * Verifica no banco de dados se o username passado já está registrado no banco de dados
     * @param username username a ser verificado
     * @throws BadRequestException se o username já estiver cadastrado
     */
    public void assertThatUsernameNotExists(String username) throws BadRequestException{
        boolean usernameExists = userRepository.existsByUsername(username);
        if (usernameExists) {
            throw new BadRequestException("Username já está em uso. Tente outro.");
        }
    }
}
