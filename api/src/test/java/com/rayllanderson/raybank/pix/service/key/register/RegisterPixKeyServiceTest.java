package com.rayllanderson.raybank.pix.service.key.register;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.PixKey;
import com.rayllanderson.raybank.pix.model.PixKeyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterPixKeyServiceTest {

    @Mock
    private PixGateway pixGateway;

    @InjectMocks
    private RegisterPixKeyService service;

    @ParameterizedTest
    @CsvSource({
            "(11) 99832-0749, 11998320749",
            "(11) 998320749, 11998320749",
            "(11)998320749, 11998320749",
            "11998320749, 11998320749",
    })
    void shouldRegisterPhoneKey(String formmated, String expectedUnformatted) {
        when(pixGateway.existsByKey(anyString())).thenReturn(false);
        when(pixGateway.countKeysByAccountId(anyString())).thenReturn(0);
        final var key = new RegisterPixKeyInput(formmated, PixKeyType.PHONE, "bkId");

        service.register(key);

        verify(pixGateway).existsByKey(expectedUnformatted);
        verify(pixGateway).save(any(PixKey.class));
    }

    @ParameterizedTest
    @CsvSource({
            "048.238.850-15, 04823885015",
            "69000804051, 69000804051"
    })
    void shouldRegisterCPFKey(String formmated, String expectedUnformatted) {
        when(pixGateway.existsByKey(anyString())).thenReturn(false);
        when(pixGateway.countKeysByAccountId(anyString())).thenReturn(0);
        final var key = new RegisterPixKeyInput(formmated, PixKeyType.CPF, "bkId");

        service.register(key);

        verify(pixGateway).existsByKey(expectedUnformatted);
        verify(pixGateway).save(any(PixKey.class));
    }

    @Test
    void shouldRegisterEmailKey() {
        when(pixGateway.existsByKey(anyString())).thenReturn(false);
        when(pixGateway.countKeysByAccountId(anyString())).thenReturn(0);
        final var key = new RegisterPixKeyInput("kaguya@sama.com", PixKeyType.EMAIL, "bkId");

        service.register(key);

        verify(pixGateway).existsByKey("kaguya@sama.com");
        verify(pixGateway).save(any(PixKey.class));
    }

    @Test
    void shouldRegisterRandomKey() {
        when(pixGateway.existsByKey(anyString())).thenReturn(false);
        when(pixGateway.countKeysByAccountId(anyString())).thenReturn(0);
        final var key = new RegisterPixKeyInput(null, PixKeyType.RANDOM, "bkId");

        service.register(key);

        verify(pixGateway).existsByKey(argThat(randomKey -> randomKey.length() == 36));
        verify(pixGateway).save(any(PixKey.class));
    }


    @Test
    void shouldIgnoreKeyAtInputWhenKeyTypeIsRandom() {
        when(pixGateway.existsByKey(anyString())).thenReturn(false);
        when(pixGateway.countKeysByAccountId(anyString())).thenReturn(0);
        final var key = new RegisterPixKeyInput("fc10b881-d9a0-4ab1-a6fd-a102db188f49", PixKeyType.RANDOM, "bkId");

        service.register(key);

        verify(pixGateway).existsByKey(argThat(randomKey -> !randomKey.equals("fc10b881-d9a0-4ab1-a6fd-a102db188f49")));
        verify(pixGateway).save(any(PixKey.class));
    }

    @Test
    void shouldThrowUnprocessableEntityExceptionWhenKeyAlreadyExists() {
        when(pixGateway.countKeysByAccountId(anyString())).thenReturn(0);
        when(pixGateway.existsByKey(anyString())).thenReturn(true);
        final var key = new RegisterPixKeyInput("kaguya@sama.com", PixKeyType.EMAIL, "bkId");

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> service.register(key))
                .withMessage("Chave Pix kaguya@sama.com já cadastrada");

        verify(pixGateway).existsByKey(anyString());
        verify(pixGateway, never()).save(any());
    }

    @Test
    void shouldThrowUnprocessableEntityExceptionWhenKeyIsInvalid() {
        final var key = new RegisterPixKeyInput("kaguya@sama", PixKeyType.EMAIL, "bkId");

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> service.register(key))
                .withMessage("Chave Pix kaguya@sama não é valida");

        verify(pixGateway, never()).existsByKey(anyString());
        verify(pixGateway, never()).save(any());
    }

    @Test
    void shouldThrowUnprocessableEntityExceptionWhenAccountHas5Keys() {
        when(pixGateway.countKeysByAccountId(anyString())).thenReturn(5);
        final var key = new RegisterPixKeyInput("kaguya@sama.com", PixKeyType.EMAIL, "bkId");

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> service.register(key))
                .withMessage("Número máximo (5) de chaves excedido.");

        verify(pixGateway, never()).existsByKey(anyString());
        verify(pixGateway, never()).save(any());
    }
}