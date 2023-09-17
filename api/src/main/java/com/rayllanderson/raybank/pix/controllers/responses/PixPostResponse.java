package com.rayllanderson.raybank.pix.controllers.responses;

import com.rayllanderson.raybank.pix.model.Pix;
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
