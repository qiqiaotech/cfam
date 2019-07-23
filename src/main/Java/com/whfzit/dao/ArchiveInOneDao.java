package com.whfzit.dao;

import com.whfzit.model.ArchiveInOneEntity;

import java.util.HashMap;
import java.util.List;

public interface ArchiveInOneDao {


    ArchiveInOneEntity selectByPrimaryKey(Integer archiveId);

    List<ArchiveInOneEntity> selectAll(HashMap params);

    Integer selectTotal(HashMap params);

    List<ArchiveInOneEntity> selectByParams(HashMap<String, String> params);

}