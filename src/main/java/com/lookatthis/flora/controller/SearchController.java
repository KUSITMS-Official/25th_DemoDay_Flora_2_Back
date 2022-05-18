package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.ResponseDto;
import com.lookatthis.flora.model.*;
import com.lookatthis.flora.service.SearchService;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
@Api(tags = {"Search API"})
public class SearchController {

    private final UserService userService;
    private final SearchService searchService;

    // 꽃 저장
    @ApiOperation(value = "꽃 저장")
    @PostMapping("/flower")
    public ResponseEntity<?extends ResponseDto> createFlower(@RequestParam(name = "flowerName") String flowerName) {
        return ResponseEntity.ok().body(new CommonResponseDto<>(searchService.createFlower(flowerName)));
    }

    // 검색
    @ApiOperation(value = "검색(꽃 상품, 꽃 가게)")
    @GetMapping("/")
    public ResponseEntity<?extends ResponseDto> getSearch(@RequestParam(name = "search") String search,
                                                                    @RequestParam(name = "distance", required = false) Double distance,
                                                                    @RequestParam(name = "color", required = false) Color color,
                                                                    @RequestParam(name = "startprice", required = false) Integer startPrice,
                                                                    @RequestParam(name = "endprice", required = false) Integer endPrice,
                                                                    @RequestParam(name = "sort", required = false) String sort) {
        User user = userService.getMyInfo();
        String[] words = search.split(" ");
        List<Portfolio> portfolios;
        if(words[1].equals("꽃집"))
            portfolios = searchService.getSearchAddressPortfolios(user, search, words[0], distance, color, startPrice, endPrice, sort);
        else
            portfolios = searchService.getSearchFlowerPortfolios(user, search, words[0], distance, color, startPrice, endPrice, sort);
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    // 검색어 조회
    @ApiOperation(value = "검색어 조회")
    @GetMapping("/word")
    public ResponseEntity<?extends ResponseDto> getSearchWords() {
        User user = userService.getMyInfo();
        List<Search> searchs = searchService.getSearchWords(user);
        return ResponseEntity.ok().body(new CommonResponseDto<>(searchs));
    }

    // 검색어 전체 삭제
    @ApiOperation(value = "검색어 전체 삭제")
    @GetMapping("/delete")
    public ResponseEntity deleteAll() {
        User user = userService.getMyInfo();
        searchService.deleteAll(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 특정 검색어 삭제
    @ApiOperation(value = "특정 검색어 삭제")
    @GetMapping("/delete/{search}")
    public ResponseEntity delete(@PathVariable String search) {
        User user = userService.getMyInfo();
        searchService.delete(user, search);
        return new ResponseEntity(HttpStatus.OK);
    }
}
