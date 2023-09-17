package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.pix.controllers.requests.PixPutDto;
import com.rayllanderson.raybank.pix.model.Pix;
import org.springframework.beans.BeanUtils;

public class PixUpdater {

    public static void updatePixKey(PixPutDto source, Pix target) {
        BeanUtils.copyProperties(source, target, "id", "owner");
    }
}
