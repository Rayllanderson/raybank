package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserFinderService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(String id) throws BadRequestException {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("Usuário não encontrado."));
    }

    @Transactional(readOnly = true)
    public User findByPixOrAccountNumber(String key, int accountNumber){
        return userRepository
                .findByPixKeysKeyOrBankAccountAccountNumber(key, accountNumber)
                .orElseThrow(() -> new BadRequestException("Pix ou número da conta estão incorretos ou destinatário não existe"));
    }
}
