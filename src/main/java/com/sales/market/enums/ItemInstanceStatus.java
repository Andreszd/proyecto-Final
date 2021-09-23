/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.enums;

public enum ItemInstanceStatus {
    SOLD,
    AVAILABLE,
    SCREWED;

    public static ItemInstanceStatus findEnum(String value){
        for (ItemInstanceStatus m : ItemInstanceStatus.values()) {
            if (m.name().equals(value.toUpperCase().trim())) {
                return m;
            }
        }
        System.out.println("not found : " + value);
        return null;
    }
}
