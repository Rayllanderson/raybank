package com.rayllanderson.raybank.pix.service;

import com.rayllanderson.raybank.pix.controllers.requests.PixPostDto;
import com.rayllanderson.raybank.pix.controllers.requests.PixPutDto;
import com.rayllanderson.raybank.pix.controllers.responses.PixPostResponse;
import com.rayllanderson.raybank.pix.controllers.responses.PixResponseDto;
import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.pix.repository.PixRepository;
import com.rayllanderson.raybank.users.repository.UserRepository;
import com.rayllanderson.raybank.utils.PixUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PixService {

    private final PixRepository pixRepository;
    private final UserRepository userRepository;

    /**
     * Registra uma chave pix para o usuário contido em @pixDto.
     * Verifica se a chave já existe. Verifica se o usuário já tem o número limite de chaves.
     * Se todas as verificações passarem, persiste no banco a chave.
     * @param pixDto necessita setar um user cadastrado no banco de dados pra registrar uma chave
     * @throws BadRequestException caso falhe em alguma das verificações.
     */
//    @Transactional
//    public PixPostResponse register(PixPostDto pixDto) throws BadRequestException {
//        User owner = userRepository.findById(pixDto.getOwnerId()).orElseThrow(() -> new BadRequestException("User não existe"));
//        this.checkThatPixNotExists(pixDto.getKey());
////        int numberOfPixKeysFromUser = pixRepository.countByOwnerId(pixDto.getOwnerId());
//        final int MAX_NUMBER_OF_KEYS = 5;
//        boolean hasExceededNumberOfKeys = numberOfPixKeysFromUser >= MAX_NUMBER_OF_KEYS;
//        if (hasExceededNumberOfKeys){
//            String message = "Sua lista de Pix já está cheia. Número máximo de chaves permitidas é de " + MAX_NUMBER_OF_KEYS
//            + ". Apague uma chave Pix e tente novamente.";
//            throw new BadRequestException(message);
//        }
//        Pix pix = pixRepository.save(PixPostDto.toPix(pixDto));
//        owner.getPixKeys().add(pix);
//        userRepository.save(owner);
//        return PixPostResponse.fromPix(pix);
//    }
//
//    @Transactional
//    public void update(PixPutDto pixDto) throws BadRequestException {
//        User owner = userRepository.findById(pixDto.getOwnerId()).orElseThrow(() -> new BadRequestException("User não existe"));
//        checkThatPixNotExists(pixDto.getKey());
//        Pix pixToBeUpdated = pixRepository.findById(pixDto.getId()).orElseThrow(() -> new BadRequestException("Pix não existe"));
//        PixUpdater.updatePixKey(pixDto, pixToBeUpdated);
//        Pix pix = pixRepository.save(PixPutDto.toPix(pixDto));
//        owner.getPixKeys().add(pix);
//        userRepository.save(owner);
//        pixRepository.save(PixPutDto.toPix(pixDto));
//    }
//
//    @Transactional
//    public List<PixResponseDto> findAllFromUserId(String userId){
//        return pixRepository.findAllByOwnerId(userId).stream().map(PixResponseDto::fromPix).collect(Collectors.toList());
//    }
//
//    @Transactional
//    public Pix findById(Long id, String userId){
//        return pixRepository.findByIdAndOwnerId(id, userId).orElseThrow(() -> new BadRequestException("Pix não " +
//                "existe na sua conta"));
//    }
//
//    @Transactional
//    public Pix findByIdAndUserId(Long id, String userId){
//        return pixRepository.findByIdAndOwnerId(id, userId).orElseThrow(() -> new BadRequestException("Pix não " +
//                "existe na sua conta"));
//    }
//
//    @Transactional
//    public void deleteById(Long id, String userId) throws BadRequestException {
//        User user = userRepository.findByIdWithPix(userId).orElseThrow(() -> new BadRequestException("User not found"));
//        Pix pixToBeDeleted = this.findById(id, userId);
//        user.getPixKeys().removeIf(pix -> pix.equals(pixToBeDeleted));
//        pixRepository.deleteById(id);
//    }

    /**
     * Verifica se o pix já está em uso por outra pessoa.
     */
    private void checkThatPixNotExists(String pixKey) throws BadRequestException {
//        boolean keyAlreadyExists = pixRepository.existsByKey(pixKey);
//        if(keyAlreadyExists){
//            throw new BadRequestException("Este Pix já está em uso. Por favor, selecione outro");
//        }
    }
}
