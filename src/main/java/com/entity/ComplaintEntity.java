package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("complaint")
public class ComplaintEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO) // 你表是 AUTO_INCREMENT
    private Long id;

    private String complaintNo;
    private Long userId;
    private Long roomId;
    private Long seatId;
    private Integer category;
    private String evidenceUrls; // TEXT 存 JSON 数组字符串
    private String detail;

    private Integer status;       // 0待处理1处理中2已结案3已驳回
    private Integer isProcessed;  // 0/1
    private Integer isApproved;   // 0/1
    private Long handlerAdminId;
    private String handleSummary;
    private String rejectReason;

    private Date createdAt;
    private Date updatedAt;
    private Date handledAt;

    // ====== getter/setter ======
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getComplaintNo() { return complaintNo; }
    public void setComplaintNo(String complaintNo) { this.complaintNo = complaintNo; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public Integer getCategory() { return category; }
    public void setCategory(Integer category) { this.category = category; }

    public String getEvidenceUrls() { return evidenceUrls; }
    public void setEvidenceUrls(String evidenceUrls) { this.evidenceUrls = evidenceUrls; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getIsProcessed() { return isProcessed; }
    public void setIsProcessed(Integer isProcessed) { this.isProcessed = isProcessed; }

    public Integer getIsApproved() { return isApproved; }
    public void setIsApproved(Integer isApproved) { this.isApproved = isApproved; }

    public Long getHandlerAdminId() { return handlerAdminId; }
    public void setHandlerAdminId(Long handlerAdminId) { this.handlerAdminId = handlerAdminId; }

    public String getHandleSummary() { return handleSummary; }
    public void setHandleSummary(String handleSummary) { this.handleSummary = handleSummary; }

    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Date getHandledAt() { return handledAt; }
    public void setHandledAt(Date handledAt) { this.handledAt = handledAt; }
}
