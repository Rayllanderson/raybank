package com.rayllanderson.raybank.pix.service.key.delete;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeletePixKeyService {

    private final PixGateway pixGateway;

    @Transactional
    public void deleteByKeyAndAccount(String key, String accountId) {
        List<PixKey> allKeysByAccountId = pixGateway.findAllKeysByAccountId(accountId);

        allKeysByAccountId.stream()
                .filter(k -> k.getKey().equals(key))
                .findFirst()
                .ifPresent(k -> pixGateway.deleteKeyById(key));
    }
}
