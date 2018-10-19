package org.jim.server.demo.service.chat;

import org.jim.common.packets.ChatBody;
import org.tio.core.ChannelContext;

/**
 * @author litianfeng
 * Created on 2018/10/17
 */
public interface IChatService {

    /**
     * 处理聊天数据
     * @param chatBody 聊天数据对象
     * @param channelContext 信道上下文
     * @return 返回处理完成聊天数据对象
     */
    public abstract ChatBody handler(ChatBody chatBody, ChannelContext channelContext);

    /**
     * 验证消息体的正确性
     * @param chatBody 消息体
     * @return true表示 数据包数据正确， 否则数据包格式有误
     */
    public abstract boolean validatePackage(ChatBody chatBody);

}
