package org.jim.server.demo.packet;

import java.nio.ByteBuffer;

/**
 * 分片文件包
 */
public class FilePacket{

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小:最大为Integer.MAX_VALUE
     */
    private int size;

    /**
     * 当前读取量
     */
    private int currentLoaded;

    /**
     * 字节缓冲区
     */
    private ByteBuffer byteBuffer;

    public FilePacket() {
    }

    /**
     *
     * @param name
     * @param size
     * @param currentLoaded
     */
    public FilePacket(String name, int size, int currentLoaded) {
        this.name = name;
        this.size = size;
        this.currentLoaded = currentLoaded;
        this.byteBuffer = ByteBuffer.allocateDirect(size);
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

    public void setSize(int size) {
        this.size = size;
    }

    public long getCurrentLoaded() {
        return currentLoaded;
    }

    public void setCurrentLoaded(int currentLoaded) {
        this.currentLoaded = currentLoaded;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

}
