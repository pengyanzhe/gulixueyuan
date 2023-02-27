package com.puge.educenter.controller;


import com.puge.commonutils.JwtUtils;
import com.puge.commonutils.R;
import com.puge.educenter.entity.UcenterMember;
import com.puge.educenter.entity.vo.RegisterVo;
import com.puge.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author pyz
 * @since 2022-10-18
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     * @param member
     * @return
     */
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    /**
     * 注册
     * @param registerVo
     * @return
     */
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
}

