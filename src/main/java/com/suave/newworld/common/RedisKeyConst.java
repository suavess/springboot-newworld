package com.suave.newworld.common;

/**
 * @Author: Suave
 * @Date: 2019-11-28 21:20
 * @Desc:
 */
public enum RedisKeyConst {
    USER("USER");

    RedisKeyConst(String key) {
        this.key = key;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
