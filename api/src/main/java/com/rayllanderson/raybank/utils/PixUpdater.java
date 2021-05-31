package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.pix.PixPutDto;
import com.rayllanderson.raybank.models.Pix;
import org.springframework.beans.BeanUtils;

public class PixUpdater {

    public static void updatePix(PixPutDto source, Pix target) {
        BeanUtils.copyProperties(source, target, "id", "owner");
    }
}
