package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.pix.PixPostDto;
import com.rayllanderson.raybank.dtos.pix.PixPutDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.Pix;
import com.rayllanderson.raybank.repositories.PixRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.utils.PixUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PixService {

    private PixRepository pixRepository;
    private UserRepository userRepository;

    /**
     * Registra uma chave pix para o usuário contido em @pixDto.
     * Verifica se a chave já existe. Verifica se o usuário já tem o número limite de chaves.
     * Se todas as verificações passarem, persiste no banco a chave.
     * @param pixDto necessita setar um user cadastrado no banco de dados pra registrar uma chave
     * @throws BadRequestException caso falhe em alguma das verificações.
     */
    public void register(PixPostDto pixDto) throws BadRequestException {
        if (pixDto.getOwner() == null) throw new BadRequestException("Owner não está setado no objeto PixDto. Necessita estar setado");
        this.checkThatPixNotExists(pixDto.getKey());
        int numberOfPixKeysFromUser = pixRepository.countByOwnerId(pixDto.getOwner().getId());
        int MAX_NUMBER_OF_KEYS = 5;
        boolean hasExceededNumberOfKeys = numberOfPixKeysFromUser >= MAX_NUMBER_OF_KEYS;
        if (hasExceededNumberOfKeys){
            String message = "Sua lista de Pix já está cheia. Número máximo de chaves permitidas é de " + MAX_NUMBER_OF_KEYS
            + ". Apague uma chave Pix e tente novamente.";
            throw new BadRequestException(message);
        }
        pixRepository.save(PixPostDto.toPix(pixDto));
    }

    public void update(PixPutDto pixDto) throws BadRequestException {
        checkThatPixNotExists(pixDto.getKey());
        Pix pixToBeUpdated = pixRepository.findById(pixDto.getId()).orElseThrow(() -> new BadRequestException("Pix não existe"));
        PixUpdater.updatePix(pixDto, pixToBeUpdated);
        pixRepository.save(PixPutDto.toPix(pixDto));
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
