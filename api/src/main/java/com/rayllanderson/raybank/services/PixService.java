package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.pix.PixPostDto;
import com.rayllanderson.raybank.dtos.requests.pix.PixPutDto;
import com.rayllanderson.raybank.dtos.responses.pix.PixPostResponse;
import com.rayllanderson.raybank.dtos.responses.pix.PixResponseDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.Pix;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.PixRepository;
import com.rayllanderson.raybank.utils.PixUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PixService {

    private final PixRepository pixRepository;

    /**
     * Registra uma chave pix para o usuário contido em @pixDto.
     * Verifica se a chave já existe. Verifica se o usuário já tem o número limite de chaves.
     * Se todas as verificações passarem, persiste no banco a chave.
     * @param pixDto necessita setar um user cadastrado no banco de dados pra registrar uma chave
     * @throws BadRequestException caso falhe em alguma das verificações.
     */
    @Transactional
    public PixPostResponse register(PixPostDto pixDto) throws BadRequestException {
        if (pixDto.getOwner() == null) throw new BadRequestException("Owner não está setado no objeto PixDto. Necessita estar setado");
        this.checkThatPixNotExists(pixDto.getKey());
        int numberOfPixKeysFromUser = pixRepository.countByOwnerId(pixDto.getOwner().getId());
        final int MAX_NUMBER_OF_KEYS = 5;
        boolean hasExceededNumberOfKeys = numberOfPixKeysFromUser >= MAX_NUMBER_OF_KEYS;
        if (hasExceededNumberOfKeys){
            String message = "Sua lista de Pix já está cheia. Número máximo de chaves permitidas é de " + MAX_NUMBER_OF_KEYS
            + ". Apague uma chave Pix e tente novamente.";
            throw new BadRequestException(message);
        }
        return PixPostResponse.fromPix(pixRepository.save(PixPostDto.toPix(pixDto)));
    }

    @Transactional
    public void update(PixPutDto pixDto) throws BadRequestException {
        checkThatPixNotExists(pixDto.getKey());
        Pix pixToBeUpdated = pixRepository.findById(pixDto.getId()).orElseThrow(() -> new BadRequestException("Pix não existe"));
        PixUpdater.updatePixKey(pixDto, pixToBeUpdated);
        pixRepository.save(PixPutDto.toPix(pixDto));
    }

    public PixResponseDto findAllFromUser(User user){
        return PixResponseDto.fromPixList(pixRepository.findAllByOwnerId(user.getId()));
    }

    public Pix findById(Long id, User authenticatedUser){
        return pixRepository.findByIdAndOwnerId(id, authenticatedUser.getId()).orElseThrow(() -> new BadRequestException("Pix não " +
                "existe na sua conta"));
    }

    @Transactional
    public void deleteById(Long id, User owner) throws BadRequestException {
        this.findById(id, owner);
        pixRepository.deleteById(id);
    }

    /**
     * Verifica se o pix já está em uso por outra pessoa.
     */
    private void checkThatPixNotExists(String pixKey) throws BadRequestException {
        boolean keyAlreadyExists = pixRepository.existsByKey(pixKey);
        if(keyAlreadyExists){
            throw new BadRequestException("Este Pix já está em uso. Por favor, selecione outro");
        }
    }
}
