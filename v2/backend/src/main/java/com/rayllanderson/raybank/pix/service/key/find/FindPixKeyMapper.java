package com.rayllanderson.raybank.pix.service.key.find;

import com.rayllanderson.raybank.pix.controllers.key.find.FindPixKeyResponse;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FindPixKeyMapper {
    FindPixKeyOutput from(PixKey key);
    FindPixKeyResponse from(FindPixKeyOutput key);
    List<FindPixKeyResponse> from(List<FindPixKeyOutput> keys);
}
