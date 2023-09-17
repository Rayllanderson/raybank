package com.rayllanderson.raybank.users.controllers.register;

import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class RegisterUserController {

    private final RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUserService.register(request.toInput()));
    }
}
