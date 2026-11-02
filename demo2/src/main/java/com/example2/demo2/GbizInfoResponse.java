package com.example2.demo2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public class GbizInfoResponse {
    @JsonProperty("hojin-infos")
    private List<CompanySearchResponse> hojinInfos;
}
