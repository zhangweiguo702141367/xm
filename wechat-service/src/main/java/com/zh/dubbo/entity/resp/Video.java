package com.zh.dubbo.entity.resp;

/**
 * 包      名：  com.partner.wechat.message.resp
 * 创 建 人：   寻欢
 * 创建时间：  2016/9/19 15:56
 * 修 改 人：
 * 修改日期：
 *
 * 视频消息体
 */
public class Video {
	private String MediaId;
	private String Title;
	private String Description;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
