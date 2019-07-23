package com.whfzit.service;

import com.whfzit.dao.ArchiveDao;
import com.whfzit.dao.ArchiveDetailDao;
import com.whfzit.model.ArchiveDetailEntity;
import com.whfzit.model.ArchiveEntity;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static final String sWebAppPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    public static final String ExcelDir = "\\files";

    public static final String[] Columns = {"册数", "档案编号", "单位名称", "序号", "类别", "题名", "起止时间", "保管期限", "备注", "整理人", "整理时间"};
    public static final String[] Fields = {"voucherCount", "archiveCode", "corpName", "voucherSn", "voucherType", "voucherTitle", "voucherDate", "storeLimit", "remark", "collator", "collateDate"};

    private static final int HEADER_COUNT = 2;
    private static final int VALID_DATA_COLUMN_CONTENT_COUNT = 6;
    private HashMap<Integer, String> totalHeader = new HashMap<Integer, String>();

    @Autowired
    ArchiveDao archiveDao;

    @Autowired
    ArchiveDetailDao archiveDetailDao;

    public static void main(String[] args) {
        ExcelService es = new ExcelService();
        es.processDir("");
    }

    public int processDir(String dir) {
        int result = 0;
        File fileDir = new File(dir);
        logger.info("工作根目录: " + fileDir.getPath());
        if(fileDir.exists()) {
            File[] files = fileDir.listFiles();
            if(files != null && files.length > 0) {
                for(File file : files) {
                    if (file.isDirectory()) {
                        processFileType(file.getPath());
                        logger.info("子目录：" + file.getPath());
                        processDir(file.getPath());
                    }
                    else {
                        int handover = file.getPath().contains("已移交") ? 1: 0;      //1为已移交，0为未移交
                        logger.info("子文件：" + file.getPath() + " //handover: " + handover);
                        if (file.getPath().contains("汇总")) {
                            processTotalExcel(file, file.getName().substring(file.getName().lastIndexOf(".")));
                        }
                        else {
                            //processDetailExcel(file.getPath(), handover);
                        }
                    }
                }
            }
        }
        return result;
    }

    private void processFileType(String dir) {
        boolean hasSum = false;
        File directory = new File(dir);
        for (File file : directory.listFiles()) {
            if (!file.isDirectory()) {
                if (file.getPath().contains("汇总")) {
                    hasSum = true;
                    break;
                }
            }
        }
        if (hasSum) {
            for (File file : directory.listFiles()) {
                if (!file.isDirectory()) {
                    if (!file.getPath().contains("汇总")) {
                        logger.info("删除文件：" + file.getPath());
                        file.delete();
                    }
                }
            }
        }
    }

    public int processDetailExcel(String fileName, int handover) {
        int result = 0;
        int archive_id = -1;
        try {
            FileInputStream in = new FileInputStream(fileName);
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(in);
                Sheet sheet = workbook.getSheetAt(0);
                int rowCount = sheet.getPhysicalNumberOfRows();
                for (int i = 0; i < rowCount; i++) {
                    Row row = sheet.getRow(i);

                    int colCount = row.getPhysicalNumberOfCells();
                    for (int j = 0; j< colCount; j ++) {
                        logger.info(row.getCell(j).getStringCellValue());
                    }
                }
            }
            finally {
                in.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public HashMap<String, String> processTotalExcel(File file, String ext) {
        HashMap<String, String> result = new HashMap<>();
        int archiveID = 0;
        String prevCorpName = "";
        String prevArchiveCode = "";
        int logRecordCount = 0;
        try {
            FileInputStream in = new FileInputStream(file);
            try {
                Sheet sheet = null;
                if (ext.trim().toLowerCase().equals(".xls")) {
                    HSSFWorkbook hWorkbook = new HSSFWorkbook(in);
                    sheet = hWorkbook.getSheetAt(0);
                }
                else if (ext.trim().toLowerCase().equals(".xlsx")) {
                    XSSFWorkbook xWorkbook = new XSSFWorkbook(in);
                    sheet = xWorkbook.getSheetAt(0);
                }
                int rowCount = sheet.getPhysicalNumberOfRows();
                ArrayList<ArchiveDetailEntity> list = new ArrayList<>();
                for (int i = 0; i < rowCount; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null){
                        //logger.error("忽略第" + i + "行，数据空白");
                        continue;
                    }

                    Cell cell = row.getCell(0);
                    if (cell == null){
                        //logger.error("忽略第" + i + "行,第1列数据空白");
                        continue;
                    }

                    CellType cellType = cell.getCellTypeEnum();
                    if (cellType == null) {
                        //logger.error("忽略第" + i + "行,第1列数据类型错误");
                        continue;
                    }

                    if ((i<HEADER_COUNT) && (cellType.equals(CellType.STRING))) {
                        if (isHeadLineRow(row)) continue;                    //表头行,忽略
                        String title = row.getCell(0).getStringCellValue().trim();
                        if (getColumnIndex(title) >= 0) {
                            initTotalHeader(row);
                            continue;
                        }
                    }

                    String corpName = row.getCell(getHeaderIndex("corpName")).getStringCellValue();
                    if ((corpName == null) || corpName.isEmpty()) {
                        //logger.error("单位名称为空行，数据行不是档案记录，忽略");
                        continue;
                    }
                    String archiveCode = row.getCell(getHeaderIndex("archiveCode")).getStringCellValue();
                    if ((archiveID <=0) || (!prevCorpName.equals(corpName)) || (!prevArchiveCode.equals(archiveCode))) {
                        archiveID = getMainId(corpName, archiveCode);
                        if (archiveID <= 0) {
                            ArchiveEntity archiveEntity = new ArchiveEntity();
                            archiveEntity.setArchiveCode(archiveCode);
                            archiveEntity.setCorpName(corpName);
                            int success = archiveDao.insert(archiveEntity);
                            if (success == 1) archiveID = getMainId(corpName, archiveCode);
                            else throw new Exception("主档案创建错误");
                            //logger.info("archiveID: " +archiveID + ", corpName:" + corpName + ", archiveCode:" + archiveCode);
                        }
                        prevArchiveCode = archiveCode;
                        prevCorpName = corpName;
                    }
                    ArchiveDetailEntity entity = createDetailRecord(i, row);

                    entity.setArchiveId(archiveID);
                    //entity.setHandover(handover);

                    archiveDetailDao.insert(entity);
                    //logger.info(entity.toString());
                    logRecordCount ++;
                }
            }
            finally {
                //logger.info("文件：" + file.getName() + "导入完成");
                in.close();
                result.put("code", "0");
                result.put("msg", "文件导入成功");
            }
        }
        catch(Exception e) {
            logger.error("数据导入错误");
            result.put("code", "1");
            result.put("msg", "文件导入失败，失败原因：<br>" + e.toString());
            e.printStackTrace();
        }
        return result;
    }

    public ArchiveDetailEntity createDetailRecord(int index, Row row) {

        ArchiveDetailEntity entity = new ArchiveDetailEntity();
        for(int j=0; j < row.getPhysicalNumberOfCells(); j++) {
            Cell cell = row.getCell(j);

            CellType cellType = null;
            if (cell == null) {
                //logger.info("第" + index + "行中第"+ j +  "单元格为NULL，忽略此格");
                continue;
            }
            else
                cellType = row.getCell(j).getCellTypeEnum();

            Object cellValue = null;
            switch (cellType) {
                case STRING:
                    cellValue = row.getCell(j).getStringCellValue();
                    break;

                case NUMERIC:
                    cellValue = (int)row.getCell(j).getNumericCellValue();
                    break;

            }
            if (cellValue == null) {
                //logger.info("第" + index + "行中第"+ j +  "列值为空，忽略列");
                continue;
            }
            try {
                switch (totalHeader.get(j)) {
//                case "voucherCount":
//                    break;
//                case "ArchiveCode":
//                    break;
//                case "corpName":
//                    break;
                    case "voucherSn":
                        if (cellValue instanceof Integer) entity.setVoucherSn((Integer) cellValue);
                        else entity.setVoucherSn(Integer.parseInt((String) cellValue));
                        break;
                    case "voucherType":
                        entity.setVoucherType((String) cellValue);
                        break;
                    case "voucherTitle":
                        entity.setVoucherTitle((String) cellValue);
                        break;
                    case "voucherDate":
                        String d = (String) cellValue;
                        entity.setVoucherDate(d);
                        entity.setStartDate(parseDate(d, 0));
                        entity.setEndDate(parseDate(d, 1));
                        break;
                    case "storeLimit":
                        String value = "";
                        if (cellValue instanceof Integer) value = ((Integer)cellValue).toString();
                        else value = (String) cellValue;
                        entity.setStoreLimit(value);
                        break;
                    case "remark":
                        entity.setRemark((String) cellValue);
                        break;
                    case "collator":
                        entity.setCollator((String) cellValue);
                        break;
                    case "collateDate":
                        if (cellValue instanceof Integer) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date dt = HSSFDateUtil.getJavaDate(((Integer) cellValue).doubleValue());
                            entity.setCollateDate(sdf.format(dt));

                        }
                        else entity.setCollateDate((String) cellValue);
                        break;
                }
            }
            catch(ClassCastException e) {
                logger.error("转换错误：(第" + index + "行第" + j + "列)");
                e.printStackTrace();
                throw e;
            }
            //logger.info("row:" + i + "col:" + j + row.getCell(j).getStringCellValue());
        }
        return entity;

    }

    public int getMainId(String corpName, String archiveCode) {
        int result = 0;
        ArchiveEntity archiveEntity = archiveDao.selectByArchiveInfo(corpName, archiveCode);
        if (archiveEntity != null) {
            result = archiveEntity.getArchiveId();
        };
        return result;
    }

    private void initTotalHeader(Row row) {
        totalHeader.clear();
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            String title = row.getCell(i).getStringCellValue().trim();
            totalHeader.put(i, Fields[getColumnIndex(title)]);
            //logger.info(totalHeader.toString());
        }
    }

    private int getHeaderIndex(String field) {                //动态生成的表头索引，根据文件生成，可能少部分列内容
        int result = -1;
        Set<Integer> es = totalHeader.keySet();
        for(int i : es) {
            if (totalHeader.get(i).equals(field)) {
                result = i;
                break;
            }
        }

        return  result;
    }

    private int getColumnIndex(String title) {                 //静态字典表头索引，包含所有列
        int result = -1;
        for(int i =0; i < Columns.length; i++) {
            if(Columns[i].equals(title)) {
                result = i;
                break;
            }
        }
        return result;
    }

    private String parseDate(String date, int type) {
        String result = "";
        if ((date != null) && (!date.isEmpty())) {
            String[] ss = date.split("-");
            if (ss.length >type)
              result =ss[type];
        }
        return  result;
    }

    private boolean isHeadLineRow(Row row) {
        boolean result = false;
        int emptyColCount = 0;
        for(int i = 0; i < row.getPhysicalNumberOfCells(); i ++) {

            if (null == row.getCell(i) || row.getCell(i).getCellTypeEnum() == null) {
                emptyColCount ++;
            }
            else {
                Cell cell = row.getCell(i);
                CellType type = cell.getCellTypeEnum();
                switch (type) {
                    case STRING:
                        if ((cell.getStringCellValue() == null) || (cell.getStringCellValue().isEmpty())) emptyColCount ++;
                        break;
                    case NUMERIC:
                        if (cell.getNumericCellValue() <0) emptyColCount ++;
                        break;
                    case BLANK:
                        emptyColCount ++;
                        break;
                }

            }
        }
        if (emptyColCount >VALID_DATA_COLUMN_CONTENT_COUNT) result = true;
        return result;
    }


}
