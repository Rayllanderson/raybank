package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.pix.PixPostDto;
import com.rayllanderson.raybank.dtos.responses.pix.PixPostResponse;
import com.rayllanderson.raybank.dtos.responses.pix.PixResponseDto;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.PixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/authenticated/pix")
@RequiredArgsConstructor
public class PixController {

    private final PixService pixService;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<PixPostResponse> save(@RequestBody PixPostDto pixPostDto){
        pixPostDto.setOwner(userRepository.findById(1L).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(pixService.register(pixPostDto));
    }

    @GetMapping
    public ResponseEntity<PixResponseDto> findAll(){
        return ResponseEntity.ok(pixService.findAllFromUser(userRepository.findById(1L).get()));
    }

}
