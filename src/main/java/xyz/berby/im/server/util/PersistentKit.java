package xyz.berby.im.server.util;

import xyz.berby.im.server.packet.FilePacket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
     * @return 存在与该系统中的文件对象
     */
    public static File persistFile(FilePacket filePacket) throws IOException {

        File file = MatchingFileGenerator.generateNewFile(filePacket, UPLOAD_FILE);
        file.createNewFile();
        FileChannel fileChannel = new FileOutputStream(file, true).getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(filePacket.getCurrentLoading());
        buffer.put(filePacket.getContent());
        buffer.flip();
        while (buffer.hasRemaining()) {
            fileChannel.write(buffer);
        }
        buffer.clear();
        fileChannel.close();
        return file;
    }


    public static void main(String[] args) throws IOException {
        FilePacket filePacket = new FilePacket();
        filePacket.setName("ee(4).txt");
        filePacket.setSessionId("4845f5df545f5d5");
        PersistentKit.persistFile(filePacket);
    }

}
