package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.responses.user.UserPostResponseDto;
import com.rayllanderson.raybank.dtos.responses.user.UserResponseDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/authenticated")
    public ResponseEntity<UserResponseDto> findAuthenticated(@AuthenticationPrincipal User authenticatedUser){
        return ResponseEntity.ok(UserResponseDto.fromUser(authenticatedUser));
    }
}
