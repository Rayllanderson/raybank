package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.requests.user.UserPutDto;
import com.rayllanderson.raybank.dtos.responses.UserPostResponseDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.utils.StringUtil;
import com.rayllanderson.raybank.utils.UserUpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;

    @Transactional
    public UserPostResponseDto register (UserPostDto userDto){
        this.assertThatUsernameNotExists(userDto.getUsername());
        User userToBeSaved =  userDto.toUser();
        userToBeSaved = userRepository.save(userToBeSaved);
        bankAccountService.createAccountBank(userToBeSaved);
        return UserPostResponseDto.fromUser(userToBeSaved);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) throws BadRequestException {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("Usuário não encontrado."));
    }

    @Transactional
    public void deleteById(Long id) throws BadRequestException {
        findById(id);
        //TODO: deletar os pixs e conta bancária
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
        User userFromDataBase = this.findById(userDto.getId());
        boolean hasUpdatedUsername = StringUtil.notMatches(userDto.getUsername(), userFromDataBase.getUsername());
        if (hasUpdatedUsername){
            this.assertThatUsernameNotExists(userDto.getUsername());
        }
        UserUpdateUtils.updateNameOrUsername(userDto, userFromDataBase);
    }

    /**
     * Use this method if you want to update ONLY password.
     */
    @Transactional
    public void updatePassword(UserPutDto user) {
        if(StringUtil.isEmpty(user.getPassword())){
            throw new IllegalArgumentException("Senha está vazia");
        }
        User userFromDataBase = this.findById(user.getId());
        UserUpdateUtils.updatePassword(user, userFromDataBase);
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
