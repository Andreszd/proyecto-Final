
package com.sales.market.enums;

public enum MovementType {
    SALE,
    REMOVED,
    BUY;

    public static MovementType findEnum(String value){
         for (MovementType m : MovementType.values()) {
            if (m.name().equals(value.toUpperCase())) {
                return m;
            }
        }
        return null;
    }
}
