package com.rayllanderson.raybank.users.controllers.register;

import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "register")
@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class RegisterUserController {

    private final RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid final RegisterUserFromKeycloackRequest request) {
        if (isRegister(request)) {
            log.info("received register request from realmId:{} and userId: {}", request.realmId(), request.userId());
            return ResponseEntity.status(HttpStatus.CREATED).body(registerUserService.register(request.toUserInput()));
        }
        if (isCreate(request)) {
            log.info("received create request from realmId:{} and userId: {}", request.realmId(), request.userId());
            return ResponseEntity.status(HttpStatus.CREATED).body(registerUserService.preRegisterEstablishemnt(request.toEstablismentInput()));
        }
        return ResponseEntity.ok().build();
    }

    private static boolean isRegister(RegisterUserFromKeycloackRequest request) {
        return request.type().equalsIgnoreCase("REGISTER");
    }

    private static boolean isCreate(RegisterUserFromKeycloackRequest request) {
        return request.type().equalsIgnoreCase("CREATE") && request.resourcePath() != null;
    }
}
