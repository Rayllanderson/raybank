package com.rayllanderson.raybank.pix.service.qrcode.find;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.qrcode.PixQrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindQrCodeService {

    private final PixGateway pixGateway;
    private final FindQrCodeMapper mapper;

    public QrCodeOutput findByIdOrCode(String idOrCode) {
        try {
            return findByCode(idOrCode);
        } catch (final NotFoundException e) {
            return findById(idOrCode);
        }
    }

    private QrCodeOutput findByCode(String qrCode) {
        final PixQrCode pixQrCode = pixGateway.findQrCodeByQrCode(qrCode);
        return mapper.from(pixQrCode);
    }

    private QrCodeOutput findById(String id) {
        final PixQrCode pixQrCode = pixGateway.findQrCodeById(id);
        return mapper.from(pixQrCode);
    }
}
