package org.jim.server.demo.packet;

import java.nio.ByteBuffer;

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
    private String content;


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
     * 字节缓冲区
     */
    private ByteBuffer byteBuffer;

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
        this.byteBuffer = ByteBuffer.allocateDirect(size);
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public long getCurrentLoading() {
        return currentLoading;
    }

    public void setCurrentLoading(Integer currentLoading) {
        this.currentLoading = currentLoading;
        if (this.byteBuffer == null)
            this.byteBuffer = ByteBuffer.allocateDirect(currentLoading);
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
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
     * 判断改文件分片是否在所属文件中的最后一个分片
     * @return 返回true表示该文件分片属于所属文件中的最后一个分片
     */
    public boolean isLastOne() {

        return this.index.equals(this.slice);
    }
}
