package com.rayllanderson.raybank.pix.service.qrcode.generate;

import com.rayllanderson.raybank.pix.controllers.qrcode.generate.GenerateQrCodeResponse;
import com.rayllanderson.raybank.pix.model.qrcode.PixQrCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenerateQrCodeMapper {
    GenerateQrCodeOutput from(PixQrCode qrCode);
    GenerateQrCodeResponse from(GenerateQrCodeOutput qrCode);
}
