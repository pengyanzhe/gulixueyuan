package com.puge.educms.controller;


import com.puge.commonutils.R;
import com.puge.educms.entity.CrmBanner;
import com.puge.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台banner显示 前端控制器
 * </p>
 *
 * @author pyz
 * @since 2022-10-15
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有banner
     * @return
     */
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }

}

