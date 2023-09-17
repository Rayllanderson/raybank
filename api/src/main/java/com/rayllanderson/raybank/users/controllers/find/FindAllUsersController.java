package com.rayllanderson.raybank.users.controllers.find;

import com.rayllanderson.raybank.users.services.find.FindAllUsersService;
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
