package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.responses.user.UserResponseDto;
import com.rayllanderson.raybank.services.FindAllUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class FindAllUsersController {

    private final FindAllUsersService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }
}
