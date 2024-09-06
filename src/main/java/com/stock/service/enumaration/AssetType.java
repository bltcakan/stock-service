package com.stock.service.enumaration;

public enum AssetType {
    TRY, GBP, USD, EUR;

    public static boolean isValidType(AssetType type) {
        for (AssetType assetType : AssetType.values()) {
            if (assetType.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
