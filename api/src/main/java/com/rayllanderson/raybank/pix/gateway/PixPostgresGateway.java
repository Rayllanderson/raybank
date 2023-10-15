package com.rayllanderson.raybank.pix.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixLimit;
import com.rayllanderson.raybank.pix.model.PixQrCode;
import com.rayllanderson.raybank.pix.model.PixQrCodeStatus;
import com.rayllanderson.raybank.pix.model.PixReturn;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.repositories.PixKeyRepository;
import com.rayllanderson.raybank.pix.repositories.PixLimitRepository;
import com.rayllanderson.raybank.pix.repositories.PixQrCodeRepository;
import com.rayllanderson.raybank.pix.repositories.PixRepository;
import com.rayllanderson.raybank.pix.repositories.PixReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PixPostgresGateway implements PixGateway {

    private final PixRepository pixRepository;
    private final PixLimitRepository limitRepository;
    private final PixKeyRepository keyRepository;
    private final PixQrCodeRepository qrCodeRepository;
    private final PixReturnRepository returnRepository;

    @Override
    public void save(Pix pix) {
        this.pixRepository.save(pix);
    }

    @Override
    public void save(PixKey pixKey) {
        keyRepository.save(pixKey);
    }

    @Override
    public void save(PixLimit pixLimit) {
        limitRepository.save(pixLimit);
    }

    @Override
    public void save(PixQrCode pixQrCode) {
        qrCodeRepository.save(pixQrCode);
    }

    @Override
    public void save(PixReturn pixReturn) {
        returnRepository.save(pixReturn);
    }

    @Override
    public PixLimit findLimitByAccountId(String accountId) {
        return limitRepository.findByBankAccountId(accountId)
                .orElseThrow(() -> NotFoundException.formatted("Limit was not found for account id %s", accountId));
    }

    @Override
    public boolean existsByKey(String key) {
        return keyRepository.existsByKey(key);
    }

    @Override
    public boolean existsLimitByAccountId(String accountId) {
        return limitRepository.existsByBankAccount_Id(accountId);
    }

    @Override
    public void deleteKeyById(String key) {
        keyRepository.deleteById(key);
    }

    @Override
    public List<PixKey> findAllKeysByAccountId(String accountId) {
        return keyRepository.findAllByBankAccountId(accountId);
    }

    @Override
    public PixKey findKeyByKey(String key) {
        return keyRepository.findById(key)
                .orElseThrow(() -> NotFoundException.formatted("Chave %s não encontrada", key));
    }

    @Override
    public PixQrCode findQrCodeByQrCode(String qrCode) {
        final PixQrCode pixQrCode = qrCodeRepository.findByCode(qrCode).orElseThrow(() -> NotFoundException.formatted("Qr Code não encontrado"));
        expireQrcodeIfPossible(pixQrCode);
        return pixQrCode;
    }

    @Override
    public PixQrCode findQrCodeById(String id) {
        final PixQrCode pixQrCode = qrCodeRepository.findById(id).orElseThrow(() -> NotFoundException.formatted("Qr Code não encontrado"));
        expireQrcodeIfPossible(pixQrCode);
        return pixQrCode;
    }

    private void expireQrcodeIfPossible(PixQrCode pixQrCode) {
        if (pixQrCode.isExpired() && pixQrCode.getStatus().equals(PixQrCodeStatus.WAITING_PAYMENT)) {
            pixQrCode.expire();
            this.save(pixQrCode);
        }
    }

    @Override
    public PixKey findKeyByAccountId(String accountId) {
        return keyRepository.findAllByBankAccountId(accountId).stream().findFirst()
                .orElseThrow(() -> NotFoundException.formatted("Nenhuma chave Pix encontrada para conta %s", accountId));
    }

    @Override
    public Pix findPixById(String pixId) {
        return pixRepository.findById(pixId)
                .orElseThrow(() -> NotFoundException.formatted("Pix %s não encontrado", pixId));
    }

    @Override
    public List<PixReturn> findAllPixReturnByPixId(String pixId) {
        return returnRepository.findAllByOriginId(pixId);
    }

    @Override
    public int countKeysByAccountId(String accountId) {
        return keyRepository.countByBankAccountId(accountId);
    }
}
