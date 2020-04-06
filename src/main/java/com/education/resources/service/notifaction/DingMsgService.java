package com.education.resources.service.notifaction;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.education.resources.bean.notication.DingMsgForm;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DingMsgService {

    public void sendMsg(DingMsgForm dingding) {
        DingTalkClient client = new DefaultDingTalkClient(dingding.getWebhook());
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        if (dingding.getDingType() == DingMsgForm.DingType.TEXT) {
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(dingding.getKeyWord() + "    " + dingding.getContent());
            request.setText(text);
            String mobiles = dingding.getMobiles();
            if (!StringUtils.isEmpty(mobiles)){
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                String[] split = mobiles.split(",");//以逗号分割
                at.setAtMobiles(Arrays.asList(split));
                request.setAt(at);
            }
        } else if (dingding.getDingType() == DingMsgForm.DingType.LINK) {
            request.setMsgtype("link");
            OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
            link.setMessageUrl(dingding.getMessageUrl());
            link.setPicUrl(dingding.getPicUrl());
            link.setTitle(dingding.getKeyWord() + "    " + dingding.getTitle());
            link.setText(dingding.getText());
            request.setLink(link);

        } else if (dingding.getDingType() == DingMsgForm.DingType.MARKDOWN) {
                request.setMsgtype("markdown");
                OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
                markdown.setTitle(dingding.getKeyWord() + "    " + dingding.getTitle());
                markdown.setText(dingding.getText());
                request.setMarkdown(markdown);

        } else if (dingding.getDingType() == DingMsgForm.DingType.ACTIONCARD) {
            request.setMsgtype("actionCard");
            OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
            actioncard.setTitle(dingding.getKeyWord() + "  " + dingding.getTitle());
            actioncard.setText(dingding.getText());
            actioncard.setHideAvatar("0");
            actioncard.setBtnOrientation("0");
            actioncard.setSingleTitle("阅读全文");
            actioncard.setSingleURL(dingding.getMessageUrl());
            request.setActionCard(actioncard);
        } else if (dingding.getDingType() == DingMsgForm.DingType.FEEDCARD) {
            request.setMsgtype("feedCard");
            OapiRobotSendRequest.Feedcard feedcard = new OapiRobotSendRequest.Feedcard();
            List list = new ArrayList();
            List<DingMsgForm.Link> linkList = dingding.getLinks();
            for (DingMsgForm.Link lk : linkList) {
                OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
                link.setTitle(lk.getTitle());
                link.setPicUrl(lk.getPicUrl());
                link.setMessageUrl(lk.getMessageUrl());
                list.add(link);
            }
            feedcard.setLinks(list);
            request.setFeedCard(feedcard);
        }

        try {
            OapiRobotSendResponse response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

}
