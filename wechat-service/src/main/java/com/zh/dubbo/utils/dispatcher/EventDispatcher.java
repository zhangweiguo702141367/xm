package com.zh.dubbo.utils.dispatcher;


import com.zh.dubbo.entity.resp.TextMessage;
import com.zh.dubbo.utils.MessageUtil;

import java.util.Date;
import java.util.Map;

/**
 * 包      名：  com.partner.wechat.utils
 * 创 建 人：   寻欢
 * 创建时间：  2016/9/19 15:46
 * 修 改 人：
 * 修改日期：
 *
 * 事件消息业务分发器
 */
public class EventDispatcher {

	public static String processEvent(Map<String, String> map) {
		String openid=map.get("FromUserName"); //用户 openid
		String mpid=map.get("ToUserName");   //公众号原始 ID


		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
			//普通文本消息
			TextMessage txtmsg=new TextMessage();
			txtmsg.setToUserName(openid);
			txtmsg.setFromUserName(mpid);
			txtmsg.setCreateTime(new Date().getTime());
			txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			txtmsg.setContent("你好，欢迎关注我嗷！！");
			return MessageUtil.textMessageToXml(txtmsg);
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
			System.out.println("==============这是取消关注事件！");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
			System.out.println("==============这是扫描二维码事件！");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
			System.out.println("==============这是位置上报事件！");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
			System.out.println("==============这是自定义菜单点击事件！");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单 View 事件
			System.out.println("==============这是自定义菜单 View 事件！");
		}

		return null;
	}
}
