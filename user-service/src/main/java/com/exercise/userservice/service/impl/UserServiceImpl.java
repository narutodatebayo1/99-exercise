package com.exercise.userservice.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.exercise.userservice.dto.CreateUserRequestDto;
import com.exercise.userservice.dto.CreateUserResponseDto;
import com.exercise.userservice.dto.GetUserByIdRequestDto;
import com.exercise.userservice.dto.GetUserByIdResponseDto;
import com.exercise.userservice.dto.GetUsersRequestDto;
import com.exercise.userservice.dto.GetUsersResponseDto;
import com.exercise.userservice.dto.UserDto;
import com.exercise.userservice.entity.User;
import com.exercise.userservice.repository.UserRepository;
import com.exercise.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public GetUsersResponseDto getUsers(GetUsersRequestDto requestDto) {
		PageRequest pageRequest = PageRequest.of(requestDto.getPageNum(),
                requestDto.getPageSize(), Sort.by("createdAt").descending()
        );

		List<User> result;
		if(requestDto.getIds() == null || requestDto.getIds().isEmpty()) {
			result = userRepository.findAll(pageRequest).getContent();
		} else {
			result = userRepository.findByIdIn(requestDto.getIds(), pageRequest);
		}

        List<UserDto> userDtoList = result.stream()
                .map(this::convertUserToUserDto)
                .collect(Collectors.toList());

        GetUsersResponseDto responseDto = new GetUsersResponseDto();
        responseDto.setResult(true);
        responseDto.setUsers(userDtoList);

        return responseDto;
	}

	@Override
	public GetUserByIdResponseDto getUserById(GetUserByIdRequestDto requestDto) {
		Optional<User> result = userRepository.findById(requestDto.getId());
        
        UserDto userDto;
        if(result.isPresent()) {
        	User user = result.get();
        	userDto = convertUserToUserDto(user);
        } else {
        	userDto = null;
        }

        GetUserByIdResponseDto responseDto = new GetUserByIdResponseDto();
        responseDto.setResult(userDto != null);
        responseDto.setUser(userDto);

        return responseDto;
	}

	@Override
	public CreateUserResponseDto createUser(CreateUserRequestDto requestDto) {
		User user = new User();
        user.setName(requestDto.getName());

        final Long timestampInMicroSecond = nowInEpochMicroSecond();
        user.setCreatedAt(timestampInMicroSecond);
        user.setUpdatedAt(timestampInMicroSecond);

        userRepository.save(user);

        CreateUserResponseDto responseDto = new CreateUserResponseDto();
        responseDto.setResult(true);
        responseDto.setUser(convertUserToUserDto(user));

        return responseDto;
	}
	
	private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }

    private Long nowInEpochMicroSecond() {
        return ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now());
    }

}
