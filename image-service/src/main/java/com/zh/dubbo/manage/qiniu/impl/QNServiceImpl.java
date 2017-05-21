package com.zh.dubbo.manage.qiniu.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zh.dubbo.dao.CommonDao;
import com.zh.dubbo.entity.Config;
import com.zh.dubbo.manage.qiniu.QNService;
import com.zh.dubbo.untils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by 70214 on 2017/5/20.
 */
@Service
public class QNServiceImpl implements QNService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CommonDao commonDao;
    @Override
    public Map<String, Object> upload(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空");
        }
        if(params.get("callback") != null && !"".equals(params.get("callback").toString())){
            //如果有回调函数则设置回调函数地址
        }
        if(params.get("bucketname") == null || "".equals(params.get("bucketname").toString())){
            throw new Exception("上传文件空间名不能为空！");
        }
        if(params.get("filepath") == null || "".equals(params.get("filepath").toString())){
            //文件当前路径包含文件名
            throw new Exception("上传文件路径不能为空！");
        }
        if(params.get("filename") == null || "".equals(params.get("filename").toString())){
            //上传七牛服务器后保存的文件名称
            throw new Exception("文件名称不能为空！");
        }
        //上传文件名称
        String filename = params.get("filename").toString();
        //上传文件路径
        String filepath = params.get("filepath").toString();
        //获取数据库七牛云配置列表
        List<Config> configList = commonDao.getConfList();
        //上传七牛云凭证AK
        String ACCESS_KEY = StringUtils.getConfFromList(configList,"ACCESS_KEY");
        //上传七牛云凭证SK
        String SECRET_KEY = StringUtils.getConfFromList(configList,"SECRET_KEY");
        //上传七牛云空间名
        String bucketname = params.get("bucketname").toString()+"_bucketname";
        bucketname = StringUtils.getConfFromList(configList,bucketname.toUpperCase());
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);

        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        //上传文件
        try {
            //调用put方法上传
//            Response res = uploadManager.put(filepath, null, getUpToken());使用回调函数
            //不使用回调函数的情况
            Response res = uploadManager.put(filepath,filename , getUpToken(auth,bucketname));
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            logger.error("qiniuyun requestInfo ==="+r.toString());
            logger.error("qiniuyun statusCode ==="+r.statusCode);
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
                logger.error("qiniuyun requestInfo bodyString==="+r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
            throw new Exception(r.toString());
        }
        return null;
    }

    @Override
    public String download(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空");
        }
        if(params.get("filename") == null || "".equals(params.get("filename").toString())){
            //上传七牛服务器后保存的文件名称
            throw new Exception("文件名称不能为空！");
        }
        //上传文件名称
        String filename = params.get("filename").toString();
        //获取数据库七牛云配置列表
        List<Config> configList = commonDao.getConfList();
        //下载七牛云凭证AK
        String ACCESS_KEY = StringUtils.getConfFromList(configList,"ACCESS_KEY");
        //下载七牛云凭证SK
        String SECRET_KEY = StringUtils.getConfFromList(configList,"SECRET_KEY");
        //下载七牛云私有空间token过期时间
        String token_time = StringUtils.getConfFromList(configList,"TOKEN_TIME");
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //下载文件
        String downloadRUL = auth.privateDownloadUrl(filename, Long.valueOf(token_time));
        System.out.println(downloadRUL);
        return null;
    }

    /**
     * 设置回调函数和参数
     * @param auth
     * @param bucketname 上传文件空间名称
     * @param callbackUrl 回调地址
     * @param callbackBody 回调参数
     * @return
     */
    private String getUpToken(Auth auth,String bucketname,String callbackUrl ,String callbackBody) {
        return auth.uploadToken(bucketname, null, 3600, new StringMap()
                .put("callbackUrl", callbackUrl)
//                .put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
                .put("callbackBody", callbackBody));
    }

    /**
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     * @param auth
     * @param bucketname 上传文件空间名称
     * @return
     */
    public String getUpToken(Auth auth,String bucketname) {
        return auth.uploadToken(bucketname);
    }

}
