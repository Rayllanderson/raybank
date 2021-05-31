package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.requests.pix.PixPutDto;
import com.rayllanderson.raybank.models.Pix;
import org.springframework.beans.BeanUtils;

public class PixUpdater {

    public static void updatePixKey(PixPutDto source, Pix target) {
        BeanUtils.copyProperties(source, target, "id", "owner");
    }
}
