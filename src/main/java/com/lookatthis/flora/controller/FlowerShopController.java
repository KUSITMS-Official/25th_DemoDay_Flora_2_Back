package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.FlowerShopDto;
import com.lookatthis.flora.dto.ResponseDto;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.service.FlowerShopService;
import com.lookatthis.flora.service.S3Service;
import com.lookatthis.flora.service.S3Uploader;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/flowershop")
@Api(tags = {"FlowerShop API"})
public class FlowerShopController {

    private final UserService userService;
    private final FlowerShopService flowerShopService;

    private final S3Service s3Service;

    // 꽃집 이미지 업로드
    @ApiOperation(value = "꽃집 이미지 업로드")
    @PostMapping("/images")
    public ResponseEntity<Object> imageUpload(@RequestParam(name = "flowerShopId") Long flowerShopId, MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        String imgName = file.getOriginalFilename();

        return ResponseEntity.ok(flowerShopService.setFlowerShopImage(flowerShopId, imgPath, imgName));
    }

    // 꽃집 추가
    @ApiOperation(value = "꽃집 추가")
    @PostMapping("/create")
    public ResponseEntity<Object> createFlowerShop(@RequestBody FlowerShopDto flowerShopDto) throws ParseException {
        return ResponseEntity.ok(flowerShopService.createFlowerShop(flowerShopDto));
    }

    // 전체 꽃집 정보 조회
    @ApiOperation(value = "전체 꽃집")
    @GetMapping("/flowershops")
    public ResponseEntity<? extends ResponseDto> getFlowerShops() {
        List<FlowerShop> flowerShops = flowerShopService.getFlowerShops();
        return ResponseEntity.ok().body(new CommonResponseDto<>(flowerShops));
    }

    // 꽃집 ID로 꽃집 정보 조회
    @ApiOperation(value = "꽃집 ID로 꽃집 정보 조회")
    @GetMapping("/{flowerShopId}")
    public ResponseEntity<? extends ResponseDto> getFlowerShop(@PathVariable Long flowerShopId) {
        Optional<FlowerShop> flowerShop = flowerShopService.getFlowerShop(flowerShopId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(flowerShop));
    }

    // 인기 꽃집 정보 (5개)
    @ApiOperation(value = "인기 꽃집")
    @GetMapping("/hot")
    public ResponseEntity<? extends ResponseDto> getHotFlowerShops() {
        User user = userService.getMyInfo();
        Point point = user.getUserPoint();
        List<FlowerShop> hotFlowerShops = flowerShopService.getHotFlowerShops(point.getX(), point.getY());
        return ResponseEntity.ok().body(new CommonResponseDto<>(hotFlowerShops));
    }

}
