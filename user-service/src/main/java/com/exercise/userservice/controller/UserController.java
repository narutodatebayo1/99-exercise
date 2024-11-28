package com.exercise.userservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.userservice.dto.CreateUserRequestDto;
import com.exercise.userservice.dto.CreateUserResponseDto;
import com.exercise.userservice.dto.GetUserByIdRequestDto;
import com.exercise.userservice.dto.GetUserByIdResponseDto;
import com.exercise.userservice.dto.GetUsersRequestDto;
import com.exercise.userservice.dto.GetUsersResponseDto;
import com.exercise.userservice.service.UserService;

@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public GetUsersResponseDto getListings(
            @RequestParam(name = "pageNum", defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(name = "ids", required= false) List<Integer> ids
    ) {
        GetUsersRequestDto requestDto = new GetUsersRequestDto();
        requestDto.setPageNum(pageNum - 1);
        requestDto.setPageSize(pageSize);
        requestDto.setIds(ids);

        return userService.getUsers(requestDto);
    }
    
    @GetMapping("/users/{id}")
    public GetUserByIdResponseDto read(@PathVariable("id") Integer id) {
        GetUserByIdRequestDto requestDto = new GetUserByIdRequestDto();
        requestDto.setId(id);

        return userService.getUserById(requestDto);
    }

    @PostMapping("/users")
    public CreateUserResponseDto createListing(@RequestBody @Valid CreateUserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }
}
