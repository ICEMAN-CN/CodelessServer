package com.gamersky.epicreport.controller;

import com.gamersky.epicreport.dto.JsonResult;
import com.gamersky.epicreport.dto.UserEpicYearReportData;
import com.gamersky.epicreport.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 2022年报epic数据接口
 *
 * @author : wangdongyang
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/reportData")
@Api(tags = "2022年报epic数据接口")
public class ReportController {

    @ApiOperation("获取用户角色和权限信息")
    @GetMapping("/userId")
    public JsonResult<UserEpicYearReportData> getUserYearReportData() {
        UserEpicYearReportData data = new UserEpicYearReportData();
        return JsonResult.ok(data);
    }


}
