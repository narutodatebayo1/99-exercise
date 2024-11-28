package com.exercise.publicapi.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exercise.publicapi.dto.CreateListingRequestDto;
import com.exercise.publicapi.dto.CreateListingResponseDto;
import com.exercise.publicapi.dto.CreateUserRequestDto;
import com.exercise.publicapi.dto.CreateUserResponseDto;
import com.exercise.publicapi.dto.GetListingsRequestDto;
import com.exercise.publicapi.dto.GetListingsResponseDto;
import com.exercise.publicapi.dto.GetListingsWithUserResponseDto;
import com.exercise.publicapi.dto.GetUsersRequestDto;
import com.exercise.publicapi.dto.GetUsersResponseDto;
import com.exercise.publicapi.dto.ListingDto;
import com.exercise.publicapi.dto.ListingWithUserDto;
import com.exercise.publicapi.dto.UserDto;
import com.exercise.publicapi.service.PublicApiService;

@Service
public class PublicApiServiceImpl implements PublicApiService {

	@Override
	public GetListingsWithUserResponseDto getListings(GetListingsRequestDto requestDto) {

		String url = "http://localhost:8081/listings";
		url += "?pageSize=" + requestDto.getPageSize();
		if(requestDto.getPageNum() > 0) url += "&pageNum=" + requestDto.getPageNum();
		if(requestDto.getUserId() != null) url += "&userId=" + requestDto.getUserId();
		
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		ResponseEntity<GetListingsResponseDto> res = restTemplate.getForEntity(url, GetListingsResponseDto.class);
		
		List<ListingWithUserDto> listListingWithUser = joinListingWithUser(res.getBody().getListings());
		
		GetListingsWithUserResponseDto getListingsWithUserResponseDto = new GetListingsWithUserResponseDto();
		getListingsWithUserResponseDto.setResult(res.getBody().isResult());
		getListingsWithUserResponseDto.setListings(listListingWithUser);
	
		return getListingsWithUserResponseDto;

	}

	@Override
	public CreateUserResponseDto createUser(CreateUserRequestDto requestDto) {
		
		String url = "http://localhost:8083/users";
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		ResponseEntity<CreateUserResponseDto> res = restTemplate.postForEntity(url, requestDto, CreateUserResponseDto.class);
		return res.getBody();
		
	}

	@Override
	public CreateListingResponseDto createListing(CreateListingRequestDto requestDto) {

		String url = "http://localhost:8081/listings";
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		ResponseEntity<CreateListingResponseDto> res = restTemplate.postForEntity(url, requestDto, CreateListingResponseDto.class);
		return res.getBody();
		
	}
	
	public GetUsersResponseDto getUsers(GetUsersRequestDto requestDto) {
		
		// change [1, 2, 3] to "1,2,3"
		String idsInString = requestDto.getIds()
				.stream().map(String::valueOf)
				.collect(Collectors.joining(","));
		
		String url = "http://localhost:8083/users?pageSize=" + requestDto.getPageSize() + "&ids=" + idsInString;
		
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		ResponseEntity<GetUsersResponseDto> res = restTemplate.getForEntity(url, GetUsersResponseDto.class);
		return res.getBody();
	}
	
	public List<ListingWithUserDto> joinListingWithUser(List<ListingDto> listListingDto) {
		
		List<Integer> listUserId = listListingDto.stream()
                .map(ListingDto::getUserId)
                .distinct()
                .collect(Collectors.toList());
		
		Integer count = listUserId.size();
		
		GetUsersRequestDto getUsersRequestDto = new GetUsersRequestDto();
		getUsersRequestDto.setIds(listUserId);
		getUsersRequestDto.setPageSize(count);
		
		GetUsersResponseDto getUsersResponseDto = getUsers(getUsersRequestDto);
		
		Map<Integer, UserDto> userMap = getUsersResponseDto.getUsers().stream().collect(Collectors.toMap(UserDto::getId, user -> user));
		
		
		List<ListingWithUserDto> listListingWithUser = new ArrayList<>();
		for(ListingDto listing : listListingDto) {
			ListingWithUserDto temp = new ListingWithUserDto();
			temp.setId(listing.getId());
			temp.setListingType(listing.getListingType());
			temp.setPrice(listing.getPrice());
			temp.setCreatedAt(listing.getCreatedAt());
			temp.setUpdatedAt(listing.getUpdatedAt());
			temp.setUser(userMap.get(listing.getUserId()));
			listListingWithUser.add(temp);
		}
		
		return listListingWithUser;
		
	}

}
