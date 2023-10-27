package com.rayllanderson.raybank.pix.gateway;

import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixLimit;
import com.rayllanderson.raybank.pix.model.qrcode.PixQrCode;
import com.rayllanderson.raybank.pix.model.PixReturn;
import com.rayllanderson.raybank.pix.model.key.PixKey;

import java.util.List;

public interface PixGateway {

    void save(Pix pix);

    void save(PixKey pixKey);

    void save(PixLimit pixLimit);

    void save(PixQrCode pixQrCode);

    void save(PixReturn pixReturn);

    PixLimit findLimitByAccountId(String accountId);

    boolean existsByKey(final String key);

    boolean existsLimitByAccountId(final String accountId);

    void deleteKeyById(final String key);

    List<PixKey> findAllKeysByAccountId(String accountId);

    PixKey findKeyByKey(String key);

    PixQrCode findQrCodeByQrCode(String qrCode);

    PixQrCode findQrCodeById(String id);

    PixKey findKeyByAccountId(String accountId);

    Pix findPixById(String pixId);

    List<PixReturn> findAllPixReturnByPixId(String pixId);
    PixReturn findPixReturnById(String pixReturnId);

    int countKeysByAccountId(String accountId);
}
