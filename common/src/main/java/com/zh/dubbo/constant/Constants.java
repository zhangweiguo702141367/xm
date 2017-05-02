package com.zh.dubbo.constant;

/**
 * Created by 70214 on 2017/4/25.
 */
public class Constants {
    /** TOP默认时间格式 **/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** TOP Date默认时区 **/
    public static final String DATE_TIMEZONE = "GMT+8";

    /** UTF-8字符集 **/
    public static final String CHARSET_UTF8 = "UTF-8";

    /** GBK字符集 **/
    public static final String CHARSET_GBK = "GBK";

    /** TOP JSON 应格式 */
    public static final String FORMAT_JSON = "json";
    /** TOP XML 应格式 */
    public static final String FORMAT_XML = "xml";

    /** MD5签名方式 */
    public static final String SIGN_METHOD_MD5 = "md5";
    /** HMAC签名方式 */
    public static final String SIGN_METHOD_HMAC = "hmac";

    /** 响应编码 */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_ENCODING_GZIP = "gzip";

    /** 默认媒体类型 **/
    public static final String MIME_TYPE_DEFAULT = "application/octet-stream";

    /** 默认流式读取缓冲区大小 **/
    public static final int READ_BUFFER_SIZE = 1024 * 4;
}
