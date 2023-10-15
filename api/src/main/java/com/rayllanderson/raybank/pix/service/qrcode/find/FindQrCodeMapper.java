package com.rayllanderson.raybank.pix.service.qrcode.find;

import com.rayllanderson.raybank.pix.controllers.qrcode.find.QrCodeResponse;
import com.rayllanderson.raybank.pix.model.PixQrCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FindQrCodeMapper {
    @Mapping(target = "creditKey", source = "credit.key")
    QrCodeOutput from(PixQrCode qrCode);
    QrCodeResponse from(QrCodeOutput qrCode);
}
