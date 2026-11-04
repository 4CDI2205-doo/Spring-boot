package com.example2.demo2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanySearchResponse {

    @JsonProperty("corporate_number")
    private String corporate_number;

    private String name;
    private String location;

    private Integer employee_number;
}
