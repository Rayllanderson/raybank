package com.rayllanderson.raybank.dtos.pix;

import com.rayllanderson.raybank.models.Pix;
import com.rayllanderson.raybank.models.User;
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
public class PixPostDto {
    @NotNull
    @NotEmpty
    private String key;
    private User owner;

    public static Pix toPix(PixPostDto dto){
        return new ModelMapper().map(dto, Pix.class);
    }
}
