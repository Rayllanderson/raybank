package com.rayllanderson.raybank.pix.service.qrcode;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.PixQrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenerateQrCodeService {

    private final PixGateway pixGateway;
    private final GenerateQrCodeMapper mapper;

    @Transactional
    public GenerateQrCodeOutput generate(GenerateQrCodeInput qrCodeInput) {
        final var creditKey = pixGateway.findKeyByAccountId(qrCodeInput.getCreditAccountId());

        final var qrCode = PixQrCode.newQrCode(qrCodeInput.getAmount(), creditKey, qrCodeInput.getDescription());
        pixGateway.save(qrCode);

        return mapper.from(qrCode);
    }
}
