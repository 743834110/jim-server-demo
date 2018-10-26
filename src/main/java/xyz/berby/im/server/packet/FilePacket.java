package xyz.berby.im.server.packet;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

/**
 * 分片文件包
 */
public class FilePacket{

    /**
     * 传输此文件的用户
     */
    private String sessionId;

    /**
     * 文件内容
     */
    private byte[] content;


    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小:最大为Integer.MAX_VALUE
     */
    private Integer size;

    /**
     * 当前读取量
     */
    private Integer currentLoading;


    /**
     * 文件当前的分片索引
     */
    private Integer index;

    /**
     * 文件总分片数量
     */
    private Integer slice;

    public FilePacket() {
    }

    /**
     *
     * @param name
     * @param size
     * @param currentLoading
     */
    public FilePacket(String name, Integer size, Integer currentLoading) {
        this.name = name;
        this.size = size;
        this.currentLoading = currentLoading;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(String content) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        this.content = base64Decoder.decodeBuffer(content.replace("data:;base64,", ""));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrentLoading() {
        return currentLoading;
    }

    public void setCurrentLoading(Integer currentLoading) {
        this.currentLoading = currentLoading;
    }



    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSlice() {
        return slice;
    }

    public void setSlice(Integer slice) {
        this.slice = slice;
    }


    /**
     * 判断此次文件分片，
     * 对于文件系统来说是否是属于新的文件分片的
     * @return
     */
    public boolean isTheNewOne() {
        // 文件分片数量为1时，则表示该文件分片是属于新来的文件的
        if (this.slice == 1)
            return true;
        // 文件分片数量不唯一时，当到达的文件分片索引为1时，则表示这是新来的属于另外的一个的文件分片
        else {
            return this.index == 1;
        }
    }

    /**
     * 判断该报文是否是某文件的最后分片
     * @return
     */
    public boolean isTheLastOne() {
        return this.index.equals(this.slice);
    }
}
