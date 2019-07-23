package com.whfzit.model;

import java.io.Serializable;

/**
 * test
 * @author 
 */
public class TestEntity implements Serializable {
    private Integer key;

    private String value;

    private static final long serialVersionUID = 1L;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}