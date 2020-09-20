package com.kakaobank.location.api


import com.kakaobank.location.service.LocationSearchService
import com.kakaobank.location.util.Constants
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
class SearchControllerTest extends Specification {
    def locationService = Mock(LocationSearchService.class)
    def sut = new SearchController(locationService)
    def mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new ControllerAdvice())
                .build()
    }

    def "SearchLocationKeyword, success"() {
        given:
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(Constants.API_ROOT + "/keyword")
                .queryParam("keyword", "서울역")
                .queryParam("page", 1)
                .queryParam("rowsPerPage", 15)

        expect:
        MvcResult mvcResult = mockMvc.perform(get(uriComponentsBuilder.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
    }

    def "SearchLocationKeyword, 키워드 null"() {
        given:
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(Constants.API_ROOT + "/keyword")
                .queryParam("page", 1)
                .queryParam("rowsPerPage", 15)

        expect:
        MvcResult mvcResult = mockMvc.perform(get(uriComponentsBuilder.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
    }

    def "SearchLocationKeyword, page null"() {
        given:
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(Constants.API_ROOT + "/keyword")
                .queryParam("keyword", "서울역")
                .queryParam("rowsPerPage", 15)

        expect:
        MvcResult mvcResult = mockMvc.perform(get(uriComponentsBuilder.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
    }

    def "SearchLocationKeyword, rowsPerpage null"() {
        given:
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(Constants.API_ROOT + "/keyword")
                .queryParam("keyword", "서울역")
                .queryParam("rowsPerPage", 15)

        expect:
        MvcResult mvcResult = mockMvc.perform(get(uriComponentsBuilder.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
    }

    def "SearchLocationKeyword, page min check"() {
        given:
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(Constants.API_ROOT + "/keyword")
                .queryParam("keyword", "서울역")
                .queryParam("page", 0)
                .queryParam("rowsPerPage", 15)

        expect:
        MvcResult mvcResult = mockMvc.perform(get(uriComponentsBuilder.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
    }

    def "SearchLocationKeyword, page max check"() {
        given:
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(Constants.API_ROOT + "/keyword")
                .queryParam("keyword", "서울역")
                .queryParam("page", 46)
                .queryParam("rowsPerPage", 15)

        expect:
        MvcResult mvcResult = mockMvc.perform(get(uriComponentsBuilder.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
    }

    def "SearchLocationKeyword, rowsPerPage min check"() {
        given:
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(Constants.API_ROOT + "/keyword")
                .queryParam("keyword", "서울역")
                .queryParam("page", 1)
                .queryParam("rowsPerPage", 0)

        expect:
        MvcResult mvcResult = mockMvc.perform(get(uriComponentsBuilder.build().toUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
    }
}
