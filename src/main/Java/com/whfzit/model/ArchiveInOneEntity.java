package com.whfzit.model;

import java.io.Serializable;
import java.util.Date;

/**
 * cfam_archive_detail
 * @author 
 */
public class ArchiveInOneEntity implements Serializable {
    /**
     * 主键
     */
    private Integer archiveId;

    /**
     * 单位名称
     */
    private String corpName;

    /**
     * 档案编码
     */
    private String archiveCode;
    /**
     * 档案明细ID
     */
    private Integer detailId;

    /**
     * 序号
     */
    private Integer voucherSn;

    /**
     * 类别 
     */
    private String voucherType;

    /**
     * 题名
     */
    private String voucherTitle;

    /**
     * 起止时间
     */
    private String voucherDate;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 保管期限
     */
    private String storeLimit;

    /**
     * 备注
     */
    private String remark;

    /**
     * 整理人
     */
    private String collator;

    /**
     * 整理日期
     */
    private String collateDate;

    private static final long serialVersionUID = 1L;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getArchiveCode() {
        return archiveCode;
    }

    public void setArchiveCode(String archiveCode) {
        this.archiveCode = archiveCode;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public Integer getVoucherSn() {
        return voucherSn;
    }

    public void setVoucherSn(Integer voucherSn) {
        this.voucherSn = voucherSn;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherTitle() {
        return voucherTitle;
    }

    public void setVoucherTitle(String voucherTitle) {
        this.voucherTitle = voucherTitle;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStoreLimit() {
        return storeLimit;
    }

    public void setStoreLimit(String storeLimit) {
        this.storeLimit = storeLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCollator() {
        return collator;
    }

    public void setCollator(String collator) {
        this.collator = collator;
    }

    public String getCollateDate() {
        return collateDate;
    }

    public void setCollateDate(String collateDate) {
        this.collateDate = collateDate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ArchiveInOneEntity other = (ArchiveInOneEntity) that;
        return (this.getArchiveId() == null ? other.getArchiveId() == null : this.getArchiveId().equals(other.getArchiveId()))
            && (this.getCorpName() == null ? other.getCorpName() == null : this.getCorpName().equals(other.getCorpName()))
            && (this.getArchiveCode() == null ? other.getArchiveCode() == null : this.getArchiveCode().equals(other.getArchiveCode()))
            && (this.getDetailId() == null ? other.getDetailId() == null : this.getDetailId().equals(other.getDetailId()))
            && (this.getVoucherSn() == null ? other.getVoucherSn() == null : this.getVoucherSn().equals(other.getVoucherSn()))
            && (this.getVoucherType() == null ? other.getVoucherType() == null : this.getVoucherType().equals(other.getVoucherType()))
            && (this.getVoucherTitle() == null ? other.getVoucherTitle() == null : this.getVoucherTitle().equals(other.getVoucherTitle()))
            && (this.getVoucherDate() == null ? other.getVoucherDate() == null : this.getVoucherDate().equals(other.getVoucherDate()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getStoreLimit() == null ? other.getStoreLimit() == null : this.getStoreLimit().equals(other.getStoreLimit()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCollator() == null ? other.getCollator() == null : this.getCollator().equals(other.getCollator()))
            && (this.getCollateDate() == null ? other.getCollateDate() == null : this.getCollateDate().equals(other.getCollateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getArchiveId() == null) ? 0 : getArchiveId().hashCode());
        result = prime * result + ((getCorpName() == null) ? 0 : getCorpName().hashCode());
        result = prime * result + ((getArchiveCode() == null) ? 0 : getArchiveCode().hashCode());
        result = prime * result + ((getDetailId() == null) ? 0 : getDetailId().hashCode());
        result = prime * result + ((getVoucherSn() == null) ? 0 : getVoucherSn().hashCode());
        result = prime * result + ((getVoucherType() == null) ? 0 : getVoucherType().hashCode());
        result = prime * result + ((getVoucherTitle() == null) ? 0 : getVoucherTitle().hashCode());
        result = prime * result + ((getVoucherDate() == null) ? 0 : getVoucherDate().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getStoreLimit() == null) ? 0 : getStoreLimit().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCollator() == null) ? 0 : getCollator().hashCode());
        result = prime * result + ((getCollateDate() == null) ? 0 : getCollateDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", archiveId=").append(archiveId);
        sb.append(", corpName=").append(corpName);
        sb.append(", archiveCode=").append(archiveCode);
        sb.append(", detailId=").append(detailId);
        sb.append(", voucherSn=").append(voucherSn);
        sb.append(", voucherType=").append(voucherType);
        sb.append(", voucherTitle=").append(voucherTitle);
        sb.append(", voucherDate=").append(voucherDate);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", storeLimit=").append(storeLimit);
        sb.append(", remark=").append(remark);
        sb.append(", collator=").append(collator);
        sb.append(", collateDate=").append(collateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}