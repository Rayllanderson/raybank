package com.rayllanderson.raybank.pix.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.repository.PixKeyRepository;
import com.rayllanderson.raybank.pix.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PixPostgresGateway implements PixGateway {

    private final PixRepository pixRepository;
    private final PixKeyRepository keyRepository;

    @Override
    public void save(PixKey pixKey) {
        keyRepository.save(pixKey);
    }

    @Override
    public boolean existsByKey(String key) {
        return keyRepository.existsByKey(key);
    }

    @Override
    public List<PixKey> findAllKeysByAccountId(String accountId) {
        return keyRepository.findAllByBankAccountId(accountId);
    }

    @Override
    public PixKey findByKey(String key) {
        return keyRepository.findById(key)
                .orElseThrow(() -> NotFoundException.formatted("Chave %s não encontrada", key));
    }

    @Override
    public int countKeysByAccountId(String accountId) {
        return keyRepository.countByBankAccountId(accountId);
    }
}
