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
        // id AUTO_INCREMENT，不要手动 setId
        complaint.setCreatedAt(new Date());
        complaint.setUpdatedAt(new Date());
        complaintService.insert(complaint);
        return R.ok();
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
//    @IgnoreAuth // 如果你的系统登录后才可访问，去掉这个注解
    @GetMapping("/student/list")
    public R studentComplaints(HttpServletRequest request) {
        // 假设学号存在于 session 或 localStorage, 这里代码用 session
        String username = null;
        Object uObj = request.getSession().getAttribute("username");
        if (uObj != null) username = uObj.toString();

        // 如无session，则前端传给后端（推荐GET参数或POST body），如：/student/list?user_id=20230003
        if (username == null || username.isEmpty()) {
            return R.error("未登录，无法查询投诉记录");
        }

        // user_id对应学号
        EntityWrapper<ComplaintEntity> ew = new EntityWrapper<>();
        ew.eq("user_id", username);

        // 实际开发建议分页，如果需要可以加 .orderBy("created_at", false)
        List<ComplaintEntity> list = complaintService.selectList(ew);
        return R.ok().put("data", list);
    }

}
