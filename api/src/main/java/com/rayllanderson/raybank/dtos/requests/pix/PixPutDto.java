package com.rayllanderson.raybank.dtos.requests.pix;

import com.rayllanderson.raybank.models.Pix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixPutDto {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    private String key;
    private Long ownerId;

    public static Pix toPix(PixPutDto dto){
        return new ModelMapper().map(dto, Pix.class);
    }
}
