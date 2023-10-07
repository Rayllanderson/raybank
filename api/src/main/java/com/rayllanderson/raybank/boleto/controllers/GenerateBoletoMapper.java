package com.rayllanderson.raybank.boleto.controllers;

import com.rayllanderson.raybank.boleto.services.GenerateBoletoInput;
import com.rayllanderson.raybank.boleto.services.GenerateBoletoOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenerateBoletoMapper {

    GenerateBoletoInput from(GenerateBoletoRequest request);
    GenerateBoletoResponse from(GenerateBoletoOutput output);

}
