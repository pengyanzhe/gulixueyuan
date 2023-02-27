package com.puge.demo.eduservice.controller;


import com.puge.commonutils.R;
import com.puge.demo.eduservice.client.VodClient;
import com.puge.demo.eduservice.entity.EduVideo;
import com.puge.demo.eduservice.service.EduVideoService;
import com.puge.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author pyz
 * @since 2022-10-01
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {

        //根据小节id获取视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeAlyVideo(videoSourceId);
        } else {
            throw new GuliException();
        }
        videoService.removeById(id);
        return R.ok();
    }


}

