package com.whfzit.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ExcelTotalHeaderEntity extends HashMap<String, ExcelTotalHeaderEntity.ExcelTotalColumnInfo> {
    public static final String[] Columns = {"册数", "档案编号", "单位名称", "序号", "类别", "题名", "起止时间", "保管期限", "备注", "整理人", "整理时间"};
    public static final String[] fieldName = {"voucherCount", "ArchiveCode", "corpName", "voucherSn", "voucherType", "voucherTitle", "voucherDate", "storeLimit", "remark", "collator", "collateDate"};
    class ExcelTotalColumnInfo {
        public String ColumnTitle;
        public int ColumnIndex;
        public ExcelTotalColumnInfo(String title, int index) {
            this.ColumnIndex = index;
            this.ColumnTitle = title;
        }
    }
    public ExcelTotalHeaderEntity() {
        for(int i =0; i<Columns.length; i ++) {
            this.put(fieldName[i], new ExcelTotalColumnInfo(Columns[i], 0));
        }
    }

    public void setColumnIndex(String title, int i) {

    }

    public void getColumnInfo(String title) {
        for(String key : this.keySet()) {
            if (((ExcelTotalColumnInfo)this.get(key)).ColumnTitle.equals(title)) {

                break;
            }
        }
    }
}
