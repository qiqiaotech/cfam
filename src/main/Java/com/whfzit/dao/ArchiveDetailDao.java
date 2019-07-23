package com.whfzit.dao;

import com.whfzit.model.ArchiveDetailEntity;

public interface ArchiveDetailDao {
    int deleteByPrimaryKey(Integer detailId);

    int insert(ArchiveDetailEntity record);

    int insertSelective(ArchiveDetailEntity record);

    ArchiveDetailEntity selectByPrimaryKey(Integer detailId);

    int updateByPrimaryKeySelective(ArchiveDetailEntity record);

    int updateByPrimaryKey(ArchiveDetailEntity record);
}