package com.rayllanderson.raybank.boleto.controllers.generate;

import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoInput;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenerateBoletoMapper {

    GenerateBoletoInput from(GenerateBoletoRequest request);
    GenerateBoletoResponse from(GenerateBoletoOutput output);

}
