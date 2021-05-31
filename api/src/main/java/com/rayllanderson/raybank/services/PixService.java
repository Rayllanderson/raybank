package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.pix.PixPostDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.repositories.PixRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PixService {

    private PixRepository pixRepository;
    private UserRepository userRepository;
    private final int MAX_NUMBER_OF_KEYS = 5;

    /**
     * Registra uma chave pix para o usuário contido em @pixDto.
     * Verifica se a chave já existe. Verifica se o usuário já tem o número limite de chaves.
     * Se todas as verificações passarem, persiste no banco a chave.
     * @param pixDto necessita setar um user cadastrado no banco de dados pra registrar uma chave
     * @throws BadRequestException caso falhe em alguma das verificações.
     */
    public void register(PixPostDto pixDto) throws BadRequestException{
        if (pixDto.getOwner() == null) throw new BadRequestException("Owner não está setado no objeto PixDto. Necessita estar setado");
        String pixKey = pixDto.getKey();
        boolean keyAlreadyExists = pixRepository.existsByKey(pixKey);
        if(keyAlreadyExists){
            throw new BadRequestException("Este Pix já está em uso. Por favor, selecione outro");
        }
        int numberOfPixKeysFromUser = pixRepository.countByOwnerId(pixDto.getOwner().getId());
        boolean hasExceededNumberOfKeys = numberOfPixKeysFromUser >= MAX_NUMBER_OF_KEYS;
        if (hasExceededNumberOfKeys){
            String message = "Sua lista de Pix já está cheia. Número máximo de chaves permitidas é de " + MAX_NUMBER_OF_KEYS
            + ". Apague uma chave Pix e tente novamente.";
            throw new BadRequestException(message);
        }
        pixRepository.save(PixPostDto.toPix(pixDto));
    }
}
