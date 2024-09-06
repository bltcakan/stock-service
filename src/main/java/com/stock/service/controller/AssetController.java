package com.stock.service.controller;

import com.stock.service.model.request.DepositMoneyRequest;
import com.stock.service.model.request.WithDrawMoneyRequest;
import com.stock.service.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/deposit")
    public ResponseEntity<Void> depositMoney(@RequestBody DepositMoneyRequest depositMoneyRequest)  {
        assetService.depositMoney(depositMoneyRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawMoney(@RequestBody WithDrawMoneyRequest withDrawMoneyRequest)  {
        assetService.withdrawMoney(withDrawMoneyRequest);
        return ResponseEntity.ok().build();
    }
}
