package com.stock.service.repository;

import com.stock.service.enumaration.AssetType;
import com.stock.service.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByCustomerIdAndAssetType(Long customerId, AssetType assetType);
}
