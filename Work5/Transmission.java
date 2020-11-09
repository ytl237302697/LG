package stage4.HomeWork.Work5;

import java.io.Serializable;

public class Transmission implements Serializable {

    private static final long serialVersionUID = -5814716593800822421L;

    // 用户名
    public String userName;
    //文件名称
    public String fileName;

    //传输内容
    public String content;

    //消息类型  0 普通内容   1 传输文件  2 下载文件
    public int itemType = 1;

}

