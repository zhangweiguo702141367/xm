package com.zh.dubbo.entity.resp;

/**
 * 包      名：  com.partner.wechat.message.resp
 * 创 建 人：   寻欢
 * 创建时间：  2016/9/19 15:55
 * 修 改 人：
 * 修改日期：
 *
 * 音乐消息消息体
 */
public class Music {
	// 音乐名称
	private String Title;
	// 音乐描述
	private String Description;
	// 音乐链接
	private String MusicUrl;
	// 高质量音乐链接，WIFI 环境优先使用该链接播放音乐
	private String HQMusicUrl;

	private String ThumbMediaId; //缩略图的媒体 id

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

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

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String musicUrl) {
		HQMusicUrl = musicUrl;
	}
}
