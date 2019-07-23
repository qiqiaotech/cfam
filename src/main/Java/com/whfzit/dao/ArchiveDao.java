package com.whfzit.dao;

import com.whfzit.model.ArchiveEntity;

public interface ArchiveDao {
    int deleteByPrimaryKey(Integer archiveId);

    int insert(ArchiveEntity record);

    int insertSelective(ArchiveEntity record);

    ArchiveEntity selectByPrimaryKey(Integer archiveId);

    ArchiveEntity selectByArchiveInfo(String corpName, String archiveCode);

    int updateByPrimaryKeySelective(ArchiveEntity record);

    int updateByPrimaryKey(ArchiveEntity record);
}