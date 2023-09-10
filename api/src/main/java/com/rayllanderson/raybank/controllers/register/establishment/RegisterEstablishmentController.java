package com.rayllanderson.raybank.controllers.register.establishment;

import com.rayllanderson.raybank.services.register.RegisterUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/establishments")
@RequiredArgsConstructor
public class RegisterEstablishmentController {

    private final RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterEstablishmentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUserService.register(request.toInput()));
    }
}