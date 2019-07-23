package com.whfzit.model;

import java.io.Serializable;

/**
 * cfam_archive
 * @author 
 */
public class ArchiveEntity implements Serializable {
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

    private static final long serialVersionUID = 1L;

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

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
        ArchiveEntity other = (ArchiveEntity) that;
        return (this.getArchiveId() == null ? other.getArchiveId() == null : this.getArchiveId().equals(other.getArchiveId()))
            && (this.getCorpName() == null ? other.getCorpName() == null : this.getCorpName().equals(other.getCorpName()))
            && (this.getArchiveCode() == null ? other.getArchiveCode() == null : this.getArchiveCode().equals(other.getArchiveCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getArchiveId() == null) ? 0 : getArchiveId().hashCode());
        result = prime * result + ((getCorpName() == null) ? 0 : getCorpName().hashCode());
        result = prime * result + ((getArchiveCode() == null) ? 0 : getArchiveCode().hashCode());
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}