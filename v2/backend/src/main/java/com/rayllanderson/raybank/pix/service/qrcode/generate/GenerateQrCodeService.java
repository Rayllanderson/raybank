package com.rayllanderson.raybank.pix.service.qrcode.generate;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.qrcode.PixQrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenerateQrCodeService {

    private final PixGateway pixGateway;
    private final GenerateQrCodeMapper mapper;

    @Transactional
    public GenerateQrCodeOutput generate(final GenerateQrCodeInput qrCodeInput) {
        final var creditKey = pixGateway.findKeyByKey(qrCodeInput.getCreditKey());

        final var qrCode = PixQrCode.newQrCode(qrCodeInput.getAmount(), creditKey, qrCodeInput.getDescription());
        pixGateway.save(qrCode);

        return mapper.from(qrCode);
    }
}
