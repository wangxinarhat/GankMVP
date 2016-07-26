package com.wangxinarhat.gankmvp.interfaces;

/**
 * Created by wang on 2016/7/25.
 */
public enum ItemType {


    ITEM_TYPE_GIRL,
    ITEM_TYPE_NORMAL,
    ITEM_TYPE_CATEGOTY;

    public static ItemType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }
}
