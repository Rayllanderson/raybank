package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.user.UserPostResponseDto;
import com.rayllanderson.raybank.dtos.responses.user.UserResponseDto;
import com.rayllanderson.raybank.services.UserFinderService;
import com.rayllanderson.raybank.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final UserFinderService userFinderService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserPostResponseDto> register(@RequestBody UserPostDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userDto));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BankAccountDto> findById(@PathVariable Long id){
//        return ResponseEntity.ok(BankAccountDto.fromBankAccount(userFinderService.findById(id).getBankAccount()));
//    }
}
