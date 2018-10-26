package xyz.berby.im.server.packet;

/**
 * 聊天数据类型
 * @author litianfeng
 * Created on 2018/10/19
 */
public enum MessageType {

    /**
     * 文本数据聊天类型
     */
    TEXT(0),

    /**
     * 默认聊天类型
     */
    OTHERS(-1);


    private Integer number;
    MessageType(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public static MessageType valueOf(Integer number) {
        for (MessageType element: MessageType.values()) {
            if (element.getNumber().equals(number))
                return element;
        }

        return null;
    }


}
