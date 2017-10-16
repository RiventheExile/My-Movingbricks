package com.my_movingbricks.utlis;

/**
 * abc  工程的常量类
 * Created by 搬砖小能手 on 14/9/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class Constants {


    /**
     * 权限常量相关
     */
    public static final int WRITE_EXTERNAL_STORAGE = 0x01;//允许程序写入外部存储，如SD卡上写文件
    public static final int ACCESS_FINE_LOCATION = 0x02;//获取精确位置
    public static final int ACCESS_COARSE_LOCATION = 0x03;//添加访问手机状态的权限
    public static final int READ_EXTERNAL_STORAGE = 0x04;//允许程序打开内部窗口，不对第三方应用程序开放此权限
    public static final int CALL_PHONE = 0x05;//允许程序从非系统拨号器里输入电话号码
    public static final int CAMERA = 0x06;//允许访问摄像头进行拍照

    /**
     * 缓存时间
     */
    public static final int time = 10800;//3小时
    public static final int timeLong = 259200;//三天
}
