package com.rayllanderson.raybank.pix.service.find;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPixService {

    private final PixGateway pixGateway;
    private final FindPixMapper mapper;

    public PixOutput findById(String pixId) {
        final Pix pix = pixGateway.findPixById(pixId);
        final List<PixReturn> returns = pixGateway.findAllPixReturnByPixId(pixId);
        return mapper.from(pix, returns);
    }
}
