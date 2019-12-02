package com.suave.newworld.common;

/**
 * @Author: Suave
 * @Date: 2019-11-28 21:20
 * @Desc: Redis键
 */
public enum RedisKeyConst {
    USER_TOKEN("USER_TOKEN"),
    USER_INFO("USER_INFO");

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
