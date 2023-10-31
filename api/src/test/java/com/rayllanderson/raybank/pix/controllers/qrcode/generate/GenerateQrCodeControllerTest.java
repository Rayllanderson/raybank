package com.rayllanderson.raybank.pix.controllers.qrcode.generate;

import com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.GenerateQrCodeBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class GenerateQrCodeControllerTest extends E2eApiTest {

    private static final String URL = "/api/v1/internal/pix/qrcode";

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldGenerateQrCode() throws Exception {
        final PixKey kaguyaKey = pixKeyCreator.newRandomKeyAndCreateAccount("kaguya");
        final var request = GenerateQrCodeBuilder.buildRequest(BigDecimal.TEN, kaguyaKey.getKey(), "description");

        post(URL, request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(10)))
                .andExpect(jsonPath("$.code", notNullValue()))
                .andExpect(jsonPath("$.description", equalTo("description")));
    }

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldNotGenerateQrCodeWhenCreditKeyNotBelongsToJwtUser() throws Exception {
        accountCreator.newNormalBankAccount("kaguya");
        final PixKey frierenKey = pixKeyCreator.newRandomKeyAndCreateAccount("frieren");
        final var request = GenerateQrCodeBuilder.buildRequest(BigDecimal.TEN, frierenKey.getKey(), "description");

        post(URL, request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(RaybankExceptionReason.PIX_KEY_NOT_FOUND.getCode())));
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }
}