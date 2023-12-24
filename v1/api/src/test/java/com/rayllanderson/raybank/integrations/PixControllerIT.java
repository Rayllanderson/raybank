package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.dtos.requests.pix.PixPostDto;
import com.rayllanderson.raybank.dtos.requests.pix.PixPutDto;
import com.rayllanderson.raybank.dtos.responses.pix.PixPostResponse;
import com.rayllanderson.raybank.dtos.responses.pix.PixResponseDto;
import com.rayllanderson.raybank.utils.PixCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PixControllerIT extends BaseBankOperation {

    final String API_URL = "/api/v1/users/authenticated/pix";

    @Test
    void save_RegisterPixKey_WhenSuccessful() {
        PixPostDto pixToBeSaved = PixCreator.createPixPixPostDto();
        ResponseEntity<PixPostResponse> response = post(API_URL, pixToBeSaved, PixPostResponse.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void save_NonRegisterPixKey_WhenIsLessThan5() {
        PixPostDto pixToBeSaved = PixCreator.createPixPixPostDto();
        pixToBeSaved.setKey("1234");
        ResponseEntity<PixPostResponse> response = post(API_URL, pixToBeSaved, PixPostResponse.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void save_NonRegisterPixKey_WhenKeyIsAlreadyInUse() {
        registerAPix(); //key é a mesma do PixCreator.createPixPixPostDto();
        PixPostDto pixToBeSaved = PixCreator.createPixPixPostDto();
        ResponseEntity<PixPostResponse> response = post(API_URL, pixToBeSaved, PixPostResponse.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findAll_ReturnsAListOfPix_WhenSuccessful() {
        registerAPix();
        var response = rest.exchange(API_URL, HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<List<PixResponseDto>>(){
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void findAll_ReturnsEmptyList_WhenUserHasNoPix() {
        var response = rest.exchange(API_URL, HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<List<PixResponseDto>>(){
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isEmpty();
        Assertions.assertThat(response.getBody()).hasSize(0);
    }

    @Test
    void update_UpdatesKey_WhenSuccessful() {
        PixPostResponse oldKey = registerAPix();
        Long keyId = oldKey.getId();
        PixPutDto newKey = new PixPutDto(keyId, "kkk@gmail.com", null);

        ResponseEntity<Void> response = put(API_URL, newKey, Void.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void update_NotUpdateKey_WhenKeyIsInvalid() {
        PixPostResponse oldKey = registerAPix();
        Long keyId = oldKey.getId();
        PixPutDto newKey = new PixPutDto(keyId, "1234", null); //size: mínimo 5

        ResponseEntity<Void> response = put(API_URL, newKey, Void.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void update_NotUpdateKey_WhenKeyIsAlreadyExists() {
        PixPostResponse oldKey = registerAPix();
        Long keyId = oldKey.getId();
        PixPutDto newKey = new PixPutDto(keyId, oldKey.getKey(), null);

        ResponseEntity<Void> response = put(API_URL, newKey, Void.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void update_NotUpdateKey_WhenKeyNotExists() {
        Long keyId = 98989565L;
        PixPutDto newKey = new PixPutDto(keyId, "whatever there", null);

        ResponseEntity<Void> response = put(API_URL, newKey, Void.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void delete_DeleteKey_WhenSuccessful() {
        PixPostResponse oldKey = registerAPix();
        Long keyId = oldKey.getId();

        ResponseEntity<Void> response = delete(API_URL +"/"+ keyId, Void.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void delete_NotDeleteKey_WhenKeyNotExists() {
        long keyId = 59444L;

        ResponseEntity<Void> response = delete(API_URL +"/"+ keyId, Void.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}