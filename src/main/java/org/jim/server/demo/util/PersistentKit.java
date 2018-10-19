package org.jim.server.demo.util;

import org.jim.server.demo.packet.FilePacket;

import java.io.File;

/**
 * 文件持久化工具类
 * @author litianfeng
 * Created on 2018/10/19
 */
public class PersistentKit {

    /**
     * 系统上传目录
     */
    private static final File UPLOAD_FILE ;
    static {

        String uploadDir = System.getProperty("user.dir") + "/upload";

        File file = new File(uploadDir);
        if (!file.exists()) {
            file.mkdir();
        }
        UPLOAD_FILE = file;
    }
    private PersistentKit() {}

    /**
     * 持久化文件
     * 注意不用用户会话之间的文件存储问题
     * 注意同一文件不同批次上传的处理
     * <i>报文可能会乱序到达</i>
     *
     * @param filePacket 文件包
     */
    public static void persistFile(FilePacket filePacket) {

        String subDir = filePacket.getSessionId() + "/" + filePacket.getName();
        File file = new File(UPLOAD_FILE, subDir);
        // 判断(该文件是否存在 + 文件的最后一个分片是否已经到达)
        boolean exists = file.exists();
        boolean lastOne = filePacket.isLastOne();
        // 存在,但不是最后一个
        if (exists && !lastOne) {

        }
        // 存在,最后一个
        else if (exists) {

        }

    }




}
