package com.whfzit.service;

import com.whfzit.dao.ArchiveInOneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class IndexService {
    @Autowired
    ArchiveInOneDao archiveInOneDao;

    public HashMap<String, Object> selectAll(HashMap<String, Object> params) {
        String page = (String)params.get("page");
        String limit = (String)params.get("limit");
        if ((page != null) && (limit != null)) {
            int p = Integer.parseInt(page)<0 ? 0: Integer.parseInt(page);
            int l = Integer.parseInt(limit);
            int offset = (p-1) * l;
            params.put("offset", offset);
            params.put("limit", l);
        }
        else {
            params.put("limit", 10);
            params.put("offset", 0);
        }
        List dataList = archiveInOneDao.selectAll(params);
        HashMap<String, Object> result = new HashMap<>();
        result.put("dataList", dataList);
        Integer recordCount = archiveInOneDao.selectTotal(params);
        result.put("pageCount", Math.ceil(recordCount/Double.parseDouble(params.get("limit").toString())));
        return result;


    }
}
