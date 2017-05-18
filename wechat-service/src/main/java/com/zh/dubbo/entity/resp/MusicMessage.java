package com.zh.dubbo.entity.resp;

/**
 * 包      名：  com.partner.wechat.message.resp
 * 创 建 人：   寻欢
 * 创建时间：  2016/9/19 15:55
 * 修 改 人：
 * 修改日期：
 *
 * 音乐消息
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
