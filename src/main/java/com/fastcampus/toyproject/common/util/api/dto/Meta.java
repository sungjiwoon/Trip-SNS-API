package com.fastcampus.toyproject.common.util.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Meta {

    @JsonProperty("total_count")
    private Integer totalCount;
}
