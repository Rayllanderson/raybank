package com.rayllanderson.raybank.pix.service.find;

import com.rayllanderson.raybank.pix.controllers.find.PixResponse;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixReturn;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface FindPixMapper {

    PixOutput from(Pix pix, Collection<PixReturn> returns);

    PixResponse from(PixOutput output);
}
