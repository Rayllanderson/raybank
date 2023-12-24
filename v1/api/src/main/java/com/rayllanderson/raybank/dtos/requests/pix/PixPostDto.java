package com.rayllanderson.raybank.dtos.requests.pix;

import com.rayllanderson.raybank.models.Pix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixPostDto {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 99)
    private String key;
    private Long ownerId;

    public static Pix toPix(PixPostDto dto){
        return new ModelMapper().map(dto, Pix.class);
    }
}
