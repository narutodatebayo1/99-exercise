package com.exercise.publicapi.service;

import com.exercise.publicapi.dto.CreateListingRequestDto;
import com.exercise.publicapi.dto.CreateListingResponseDto;
import com.exercise.publicapi.dto.CreateUserRequestDto;
import com.exercise.publicapi.dto.CreateUserResponseDto;
import com.exercise.publicapi.dto.GetListingsRequestDto;
import com.exercise.publicapi.dto.GetListingsWithUserResponseDto;

public interface PublicApiService {

	GetListingsWithUserResponseDto getListings(GetListingsRequestDto requestDto);
	CreateUserResponseDto createUser(CreateUserRequestDto requestDto);
	CreateListingResponseDto createListing(CreateListingRequestDto requestDto);
	
}
