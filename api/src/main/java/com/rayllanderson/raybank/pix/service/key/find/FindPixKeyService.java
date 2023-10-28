package com.rayllanderson.raybank.pix.service.key.find;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.PIX_KEY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FindPixKeyService {

    private final PixGateway pixGateway;
    private final FindPixKeyMapper mapper;

    public List<FindPixKeyOutput> findAllByAccountId(String accountId) {
        List<PixKey> allKeysByAccountId = pixGateway.findAllKeysByAccountId(accountId);

        return allKeysByAccountId.stream().map(mapper::from).collect(Collectors.toList());
    }

    public FindPixKeyOutput findByKeyAndAccountId(String key, String accountId) {
        final List<PixKey> allKeysByAccountId = pixGateway.findAllKeysByAccountId(accountId);

        final PixKey pixKey = allKeysByAccountId.stream()
                .filter(k -> k.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> NotFoundException.formatted(PIX_KEY_NOT_FOUND, "Chave %s não encontrada", key));

        return mapper.from(pixKey);
    }
}
