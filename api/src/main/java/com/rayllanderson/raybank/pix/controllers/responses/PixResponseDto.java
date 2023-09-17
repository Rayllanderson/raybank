package com.rayllanderson.raybank.pix.controllers.responses;

import com.rayllanderson.raybank.pix.model.Pix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PixResponseDto {
    private Long id;
    private String pixKeys;

    public static PixResponseDto fromPix(Pix pix){
        PixResponseDto pixDto = new PixResponseDto();
        pixDto.setPixKeys(pix.getKey());
        pixDto.setId(pix.getId());
        return pixDto;
    }
}
