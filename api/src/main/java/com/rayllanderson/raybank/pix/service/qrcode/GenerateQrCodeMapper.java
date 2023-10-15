package com.rayllanderson.raybank.pix.service.qrcode;

import com.rayllanderson.raybank.pix.controllers.qrcode.GenerateQrCodeResponse;
import com.rayllanderson.raybank.pix.model.PixQrCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenerateQrCodeMapper {
    GenerateQrCodeOutput from(PixQrCode qrCode);
    GenerateQrCodeResponse from(GenerateQrCodeOutput qrCode);
}
