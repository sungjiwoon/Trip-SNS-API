package com.fastcampus.toyproject.common.util.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("x")
    private double longitude;

    @JsonProperty("y")
    private double latitude;

    @JsonProperty("place_url")
    private String placeUrl;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("distance")
    private double distance;

}
