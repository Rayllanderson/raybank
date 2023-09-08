package com.rayllanderson.raybank.dtos.requests.pix;

import com.rayllanderson.raybank.models.Pix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixPutDto {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 99)
    private String key;
    private String ownerId;

    public static Pix toPix(PixPutDto dto){
        return new ModelMapper().map(dto, Pix.class);
    }
}
