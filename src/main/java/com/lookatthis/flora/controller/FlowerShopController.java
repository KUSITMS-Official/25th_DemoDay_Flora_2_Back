package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.FlowerShopDto;
import com.lookatthis.flora.dto.ResponseDto;
import com.lookatthis.flora.dto.UserDto;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.service.FlowerShopService;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/flowershop")
@Api(tags = {"FlowerShop API"})
public class FlowerShopController {

    private final UserService userService;
    private final FlowerShopService flowerShopService;

    // 꽃집 추가
    @ApiOperation(value = "꽃집 추가")
    @PostMapping("/create")
    public ResponseEntity<Object> createFlowerShop(@RequestBody FlowerShopDto flowerShopDto) throws ParseException {
        return ResponseEntity.ok(flowerShopService.createFlowerShop(flowerShopDto));
    }

    // 전체 꽃집 정보
    @ApiOperation(value = "전체 꽃집")
    @GetMapping("/flowershops")
    public ResponseEntity<? extends ResponseDto> getFlowerShops() {
        List<FlowerShop> flowerShops = flowerShopService.getFlowerShops();
        return ResponseEntity.ok().body(new CommonResponseDto<>(flowerShops));
    }

    // 꽃집 정보
    @ApiOperation(value = "꽃집")
    @GetMapping("/{flowerShopId}")
    public ResponseEntity<? extends ResponseDto> getFlowerShop(@PathVariable Long flowerShopId) {
        Optional<FlowerShop> flowerShop = flowerShopService.getFlowerShop(flowerShopId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(flowerShop));
    }

    // 인기 꽃집 정보
    @ApiOperation(value = "인기 꽃집")
    @GetMapping("/hot")
    public ResponseEntity<? extends ResponseDto> getNearFlowerShop() {
        User user = userService.getMyInfo();
        Point point = user.getUserPoint();
        List<FlowerShop> flowerShops = flowerShopService.getNearByFlowerShops(point.getX(), point.getY(), 0.5,5);
        return ResponseEntity.ok().body(new CommonResponseDto<>(flowerShops));
    }

}
