package com.exercise.publicapi.dto;

import java.util.List;

public class GetListingsWithUserResponseDto {

	private boolean result;
    private List<ListingWithUserDto> listings;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<ListingWithUserDto> getListings() {
        return listings;
    }

    public void setListings(List<ListingWithUserDto> listings) {
        this.listings = listings;
    }
}
