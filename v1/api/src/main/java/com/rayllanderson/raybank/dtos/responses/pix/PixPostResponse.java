package com.rayllanderson.raybank.dtos.responses.pix;

import com.rayllanderson.raybank.models.Pix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PixPostResponse {
    private Long id;
    private String key;

    public static PixPostResponse fromPix(Pix source){
        return new ModelMapper().map(source, PixPostResponse.class);
    }
}
