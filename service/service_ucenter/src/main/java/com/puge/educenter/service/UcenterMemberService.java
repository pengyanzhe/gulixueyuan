package com.puge.educenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.puge.educenter.entity.UcenterMember;
import com.puge.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author pyz
 * @since 2022-10-18
 */
public interface UcenterMemberService extends IService<UcenterMember> {


    /**
     * 登录的方法
     * @param member
     * @return
     */
    String login(UcenterMember member);

    /**
     * 注册的方法
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据openid判断
     * @param openid
     * @return
     */
    UcenterMember getOpenIdMember(String openid);

}
