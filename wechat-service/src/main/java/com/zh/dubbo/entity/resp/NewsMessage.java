package com.zh.dubbo.entity.resp;

import java.util.List;

/**
 * 包      名：  com.partner.wechat.message.resp
 * 创 建 人：   寻欢
 * 创建时间：  2016/9/19 15:53
 * 修 改 人：
 * 修改日期：
 *
 * 多图文消息
 */
public class NewsMessage extends BaseMessage {
	// 图文消息个数，限制为 10 条以内
	private int ArticleCount;
	// 多条图文消息信息，默认第一个 item 为大图
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
