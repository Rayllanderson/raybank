package com.rayllanderson.raybank.dtos.pix;

import com.rayllanderson.raybank.models.Pix;
import com.rayllanderson.raybank.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixPostDto {
    private String key;
    private User owner;

    public static Pix toPix(PixPostDto dto){
        return new ModelMapper().map(dto, Pix.class);
    }
}