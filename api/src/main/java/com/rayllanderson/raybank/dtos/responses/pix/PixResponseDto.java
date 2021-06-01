package com.rayllanderson.raybank.dtos.responses.pix;

import com.rayllanderson.raybank.models.Pix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PixResponseDto {
    List<PixPostResponse>pixKeys = new ArrayList<>();

    public static PixResponseDto fromPixList(List<Pix> pixKeys){
        PixResponseDto pixDto = new PixResponseDto();
        pixDto.getPixKeys().addAll(pixKeys.stream().map(PixPostResponse::fromPix).collect(Collectors.toList()));
        return pixDto;
    }
}
