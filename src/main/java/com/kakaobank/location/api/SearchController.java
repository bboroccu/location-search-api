package com.kakaobank.location.api;

import com.kakaobank.location.endpoint.SearchKeywordRequest;
import com.kakaobank.location.service.LocationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.kakaobank.location.util.Constants.API_ROOT;

@RestController
@RequestMapping(API_ROOT)
public class SearchController {
    private final LocationSearchService locationSearchService;

    @Autowired
    public SearchController(LocationSearchService locationSearchService) {
        this.locationSearchService = locationSearchService;
    }

    @GetMapping("/keyword")
    public ResponseEntity searchLocationKeyword(
            @Valid SearchKeywordRequest searchKeywordRequest
            ) {
        return ResponseEntity.ok(locationSearchService.searchLocation(searchKeywordRequest));
    }
}
