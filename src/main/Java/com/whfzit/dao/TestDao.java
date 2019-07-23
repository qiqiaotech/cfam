package com.whfzit.dao;

import com.whfzit.model.TestEntity;

import java.util.List;

public interface TestDao {
    int deleteByPrimaryKey(Integer key);

    int insert(TestEntity record);

    int insertSelective(TestEntity record);

    TestEntity selectByPrimaryKey(Integer key);

    List<TestEntity> selectAll();

    int updateByPrimaryKeySelective(TestEntity record);

    int updateByPrimaryKey(TestEntity record);
}