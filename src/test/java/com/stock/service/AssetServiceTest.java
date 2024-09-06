package com.stock.service;

import com.stock.service.enumaration.AssetType;
import com.stock.service.model.Asset;
import com.stock.service.model.dto.UserDetailsDto;
import com.stock.service.model.request.DepositMoneyRequest;
import com.stock.service.model.request.WithDrawMoneyRequest;
import com.stock.service.repository.AssetRepository;
import com.stock.service.service.CustomUserDetailsService;
import com.stock.service.service.impl.AssetServiceImpl;
import com.stock.service.verification.AssetValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest  {
    @Mock
    private AssetRepository assetRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private AssetValidationService assetValidationService;

    @InjectMocks
    private AssetServiceImpl assetService;

    private UserDetailsDto userDetailsDto;
    private Asset asset;

    @BeforeEach
    void setUp() {
        userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(1L);

        asset = new Asset();
        asset.setSize(BigDecimal.TEN);
        asset.setUsableSize(BigDecimal.TEN);
    }


    @Test
    void testGetAssetByCustomerIdAndAssetType() {
        when(assetRepository.findByCustomerIdAndAssetType(1L, AssetType.TRY)).thenReturn(Optional.of(asset));

        Asset result = assetService.getAssetByCustomerIdAndAssetType(1L, AssetType.TRY);

        assertEquals(asset, result);
    }


    @Test
    void testUpdateAsset() {
        assetService.updateAsset(asset);

        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    void testDepositMoney() {
        DepositMoneyRequest request = new DepositMoneyRequest();
        request.setAmount(BigDecimal.ONE);
        request.setAssetType(AssetType.GBP);

        when(customUserDetailsService.getCurrentUserDetails()).thenReturn(userDetailsDto);
        when(assetRepository.findByCustomerIdAndAssetType(1L, AssetType.GBP)).thenReturn(Optional.of(asset));

        assetService.depositMoney(request);

        verify(assetValidationService, times(1)).validateAssetType(AssetType.GBP);
        verify(assetRepository, times(1)).save(asset);
        assertEquals(BigDecimal.valueOf(11), asset.getSize());
        assertEquals(BigDecimal.valueOf(11), asset.getUsableSize());
    }

    @Test
    void testWithdrawMoney() {
        WithDrawMoneyRequest request = new WithDrawMoneyRequest();
        request.setAmount(BigDecimal.ONE);
        request.setAssetType(AssetType.GBP);
        request.setIban("IBAN123");

        when(customUserDetailsService.getCurrentUserDetails()).thenReturn(userDetailsDto);
        when(assetRepository.findByCustomerIdAndAssetType(1L, AssetType.GBP)).thenReturn(Optional.of(asset));

        assetService.withdrawMoney(request);

        verify(assetValidationService, times(1)).validateAssetType(AssetType.GBP);
        verify(assetValidationService, times(1)).validateIban("IBAN123");
        verify(assetValidationService, times(1)).validateBalance(1L, BigDecimal.ONE, BigDecimal.ONE, AssetType.GBP);
        verify(assetRepository, times(1)).save(asset);
        assertEquals(BigDecimal.valueOf(9), asset.getSize());
        assertEquals(BigDecimal.valueOf(9), asset.getUsableSize());
    }
}
