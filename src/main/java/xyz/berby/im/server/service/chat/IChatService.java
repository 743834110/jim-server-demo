package xyz.berby.im.server.service.chat;

import org.jim.common.ImConfig;
import org.jim.common.ImPacket;
import org.jim.common.packets.ChatBody;
import org.tio.core.ChannelContext;

/**
 * @author litianfeng
 * Created on 2018/10/17
 */
public interface IChatService {

    /**
     * 处理聊天数据
     * @param imConfig
     * @param chatBody 聊天数据对象
     * @param channelContext 信道上下文
     * @return 返回处理完成聊天数据对象
     */
    public abstract ChatBody handler(ImConfig imConfig, ChatBody chatBody, ChannelContext channelContext);

    /**
     * 验证消息体的正确性
     * @param chatBody 消息体
     * @return true表示 数据包数据正确， 否则数据包格式有误
     */
    public abstract boolean validatePackage(ChatBody chatBody);

    /**
     * 根据不同的聊天数据类型发送不送的响应报文
     * @param synSeq 同步号
     * @param chatBody handle方法返回的对象
     * @param imConfig 系统配置
     * @param channelContext 客户端上下文对象
     * @return
     */
    public abstract ImPacket send(Integer synSeq, ChatBody chatBody, ImConfig imConfig, ChannelContext channelContext) throws Exception;



}
