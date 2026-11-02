package com.example2.demo2;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

@Service 
public class GbizInfoService {
    private final RestClient restClient;

    public GbizInfoService(@Value("${gbizinfo.api-token}") String apiToken){
        this.restClient = RestClient.builder()
            .baseUrl("https://api.info.gbiz.go.jp/hojin/v2")
            .defaultHeader("X-hojinInfo-api-token",apiToken)
            .build();  
    }

    // 企業名から法人番号を取得
    public List<CompanySearchResponse> searchCompany(String companyName){
        System.out.println("gBizINFO検索開始：" + companyName);
        GbizInfoResponse response = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/hojin")
                .queryParam("name",companyName)
                .build())
            .retrieve()
            .body(GbizInfoResponse.class);

        if (response == null || response.getHojinInfos() == null){
            return List.of();
        }
        
        return response.getHojinInfos();
    }

    // // 法人番号から詳細取得
    // public String getCompanyDetail(String corporateNumber){
    //     return restClient.get()
    //         .uri("/hojin/{corporateNumber}",corporateNumber)
    //         .retrieve()
    //         .body(String.class);
    // }
}
