package com.zh.dubbo.utils.dispatcher;
import com.zh.dubbo.entity.resp.Article;
import com.zh.dubbo.entity.resp.NewsMessage;
import com.zh.dubbo.utils.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 包      名：  com.partner.wechat.utils
 * 创 建 人：   寻欢
 * 创建时间：  2016/9/19 15:45
 * 修 改 人：
 * 修改日期：
 *
 * 消息业务处理分发器
 */
public class MsgDispatcher {

	public static String processMessage(Map<String, String> map) {

		String openid=map.get("FromUserName"); //用户 openid
		String mpid=map.get("ToUserName");   //公众号原始 ID


		/**
		 *  文本消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			/*//普通文本消息
			TextMessage txtmsg=new TextMessage();
			txtmsg.setToUserName(openid);
			txtmsg.setFromUserName(mpid);
			txtmsg.setCreateTime(new Date().getTime());
			txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			txtmsg.setContent("你好，这里是出行有我！");
			return MessageUtil.textMessageToXml(txtmsg);*/

			System.out.println("==============这是文本消息！");
		}


		/**
		 *  图片消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			NewsMessage newmsg=new NewsMessage();
			newmsg.setToUserName(openid);
			newmsg.setFromUserName(mpid);
			newmsg.setCreateTime(new Date().getTime());
			newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);


			Article article=new Article();
			article.setDescription("我不是黄图，真的不是。。"); //图文消息的描述
			article.setPicUrl("http://image.tianjimedia.com/uploadImages/2014/354/02/L7S1493N4T94.jpg"); //图文消息图片地址
			article.setTitle("我不是黄图");  //图文消息标题
			article.setUrl("https://www.baidu.com");  //图文 url 链接
			List<Article> list=new ArrayList<Article>();
			list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
			newmsg.setArticleCount(list.size());
			newmsg.setArticles(list);
			return MessageUtil.newsMessageToXml(newmsg);
		}


		/**
		 *  链接消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			System.out.println("==============这是链接消息！");
		}


		/**
		 *  位置消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			System.out.println("==============这是位置消息！");
		}


		/**
		 *  视频消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
			System.out.println("==============这是视频消息！");
		}


		/**
		 *  语音消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			System.out.println("==============这是语音消息！");
		}

		return null;
	}

}
