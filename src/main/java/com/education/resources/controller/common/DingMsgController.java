package com.education.resources.controller.common;


import com.education.resources.bean.notication.DingMsgForm;
import com.education.resources.service.notifaction.DingMsgService;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager/dingding")
//@Api(tags = "钉钉")
public class DingMsgController {

   DingMsgService dingMsgService=new DingMsgService();

//    @ApiOperation(value = "发送钉钉消息", notes = "")
    @PostMapping("/sendDingMsg")
    public API sendMsg(@RequestBody DingMsgForm dingding){
        dingMsgService.sendMsg(dingding);
        return API.ok();
    }


}
