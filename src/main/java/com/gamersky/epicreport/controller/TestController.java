package com.gamersky.epicreport.controller;

import com.gamersky.epicreport.dto.JsonResult;
import com.gamersky.epicreport.dto.UserEpicYearReportData;
import com.gamersky.epicreport.entity.TestUser;
import com.gamersky.epicreport.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 2022年报epic数据接口
 *
 * @author : wangdongyang
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
public class TestController {


    private static final Map<Integer, TestUser> GLOBAL_USERS = new ConcurrentHashMap<>();

    static {
        GLOBAL_USERS.put(1, new TestUser().setUserId(1).setUserName("张三").setUserAvatar("https://www.baidu.com/img/bd_logo1.png"));
        GLOBAL_USERS.put(2, new TestUser().setUserId(2).setUserName("张三2").setUserAvatar("https://www.baidu.com/img/bd_logo1.png"));
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/getUser")
    public JsonResult<TestUser> getTestUserInfo(@RequestParam Integer userId) {
        if (userId == null) {
            throw new ServiceException("invalid userId");
        }
        return JsonResult.ok(GLOBAL_USERS.get(userId));
    }

    @ApiOperation("增加用户信息")
    @PostMapping("/addUser")
    public JsonResult<TestUser> addUser(@RequestBody TestUser req) {
        if (!req.isValid()){
            throw new ServiceException("请求参数不正确");
        }
        GLOBAL_USERS.put(req.getUserId(), req);
        return JsonResult.ok(req);
    }

    @ApiOperation("删除用户信息")
    @DeleteMapping("/deleteUser")
    public JsonResult<TestUser> deleteUser(@RequestBody TestUser req) {
        if (!req.isValid()){
            throw new ServiceException("请求参数不正确");
        }
        TestUser user = GLOBAL_USERS.get(req.getUserId());
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        return JsonResult.ok(user);
    }


}
