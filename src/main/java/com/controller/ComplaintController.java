package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ComplaintEntity;
import com.service.ComplaintService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ComplaintController - 增强 save 的容错性，确保 complaintNo / createdAt / updatedAt 的处理
 */
@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    /** 后台分页 */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, ComplaintEntity complaint, HttpServletRequest request) {
        EntityWrapper<ComplaintEntity> ew = new EntityWrapper<>();
        PageUtils page = complaintService.queryPage(
                params,
                MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, complaint), params), params)
        );
        return R.ok().put("data", page);
    }

    /** 后台详情 */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ComplaintEntity complaint = complaintService.selectById(id);
        return R.ok().put("data", complaint);
    }

    /** 后台保存（可选） */
    @RequestMapping("/save")
    public R save(@RequestBody ComplaintEntity complaint, HttpServletRequest request) {
        try {
            // 调试输出：检查 session 中是否有登录用户标识（你之前的代码在别处用的是 "username"）
            Object sessionUsername = request.getSession().getAttribute("username");
            System.out.println("DEBUG /complaint/save session.username = " + sessionUsername);

            // 输出头信息，检查前端是否传来了 Authorization / Token
            String authHeader = request.getHeader("Authorization");
            String tokenHeader = request.getHeader("Token");
            System.out.println("DEBUG /complaint/save Authorization = " + authHeader);
            System.out.println("DEBUG /complaint/save Token = " + tokenHeader);

            // 如果没有 complaintNo 则自动生成
            if (complaint.getComplaintNo() == null || complaint.getComplaintNo().trim().isEmpty()) {
                complaint.setComplaintNo("TS" + System.currentTimeMillis());
            }

            complaint.setCreatedAt(new Date());
            complaint.setUpdatedAt(new Date());

            // 这里可以允许前端直接传 userId（注意类型转换）
            complaintService.insert(complaint);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存投诉异常：" + e.getMessage());
        }
    }

    /** 后台修改（可选） */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ComplaintEntity complaint, HttpServletRequest request) {
        complaint.setUpdatedAt(new Date());
        complaintService.updateById(complaint);
        return R.ok();
    }

    /** 删除（和 NewsController 风格一致：/delete body=Long[]） */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        complaintService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /** 通过并结案 */
    @PostMapping("/approve")
    @Transactional
    public R approve(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        Long handlerAdminId = body.get("handlerAdminId") == null ? null : Long.valueOf(body.get("handlerAdminId").toString());
        String handleSummary = body.get("handleSummary") == null ? null : body.get("handleSummary").toString();

        ComplaintEntity c = new ComplaintEntity();
        c.setId(id);
        c.setStatus(2);
        c.setIsProcessed(1);
        c.setIsApproved(1);
        c.setHandlerAdminId(handlerAdminId);
        c.setHandleSummary(handleSummary);
        c.setRejectReason(null);
        c.setHandledAt(new Date());
        c.setUpdatedAt(new Date());

        complaintService.updateById(c);
        return R.ok();
    }

    /** 驳回 */
    @PostMapping("/reject")
    @Transactional
    public R reject(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        Long handlerAdminId = body.get("handlerAdminId") == null ? null : Long.valueOf(body.get("handlerAdminId").toString());
        String rejectReason = body.get("rejectReason") == null ? null : body.get("rejectReason").toString();

        ComplaintEntity c = new ComplaintEntity();
        c.setId(id);
        c.setStatus(3);
        c.setIsProcessed(1);
        c.setIsApproved(0);
        c.setHandlerAdminId(handlerAdminId);
        c.setRejectReason(rejectReason);
        c.setHandledAt(new Date());
        c.setUpdatedAt(new Date());

        complaintService.updateById(c);
        return R.ok();
    }

    /** 学生端查询自己投诉记录 */
    @GetMapping("/student/list")
    public R studentComplaints(HttpServletRequest request,
                               @RequestParam(value = "user_id", required = false) String userIdParam) {
        String username = null;
        Object uObj = request.getSession().getAttribute("username");
        if (uObj != null) username = uObj.toString();

        // 如果 session 中没有，从请求参数或 Header 中尝试读
        if ((username == null || username.isEmpty()) && userIdParam != null && !userIdParam.isEmpty()) {
            username = userIdParam;
        }

        // 如果仍然没有，尝试从 Authorization token（如果你有 JWT）解析（伪代码，需你实现）
        // if (username == null) { username = AuthUtil.getUsernameFromRequest(request); }

        if (username == null || username.isEmpty()) {
            return R.error("未登录，无法查询投诉记录");
        }

        EntityWrapper<ComplaintEntity> ew = new EntityWrapper<>();
        ew.eq("user_id", username);
        List<ComplaintEntity> list = complaintService.selectList(ew);
        return R.ok().put("data", list);
    }



}