package com.ireland.ager.trade.controller;

import com.ireland.ager.account.service.AuthServiceImpl;
import com.ireland.ager.main.common.CommonResult;
import com.ireland.ager.main.common.service.ResponseService;
import com.ireland.ager.trade.service.TradeServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : TradeController
 * @Description : 거래 도메인에 대한 컨트롤러
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = {"*"}, maxAge = 6000)
@RequestMapping("/api/trade")
public class TradeController {
    private final TradeServiceImpl tradeService;
    private final ResponseService responseService;

    /**
     * @Method : setStatus
     * @Description : 상품 거래 상태 수정
     * @Parameter : [accessToken, roomId, status]
     * @Return : ResponseEntity<CommonResult>
     **/
    @PostMapping("/{roomId}")
    @ApiOperation(value = "제품 거래 상태 변경")
    public ResponseEntity<CommonResult> setStatus(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "채팅방 아이디", required = true)
            @PathVariable Long roomId,
            @ApiParam(value = "제품 상태", required = true)
            @RequestParam String status) {
        String[] splitToken = accessToken.split(" ");
        tradeService.isUpdated(roomId, splitToken[1], status);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.CREATED);
    }
}
