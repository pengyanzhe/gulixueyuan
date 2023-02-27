package com.puge.educms.service;

import com.puge.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author pyz
 * @since 2022-10-15
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有banner
     * @return banner集合
     */
    List<CrmBanner> selectAllBanner();

}
