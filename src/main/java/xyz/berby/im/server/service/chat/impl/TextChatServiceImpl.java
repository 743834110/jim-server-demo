package xyz.berby.im.server.service.chat.impl;

import org.jim.common.ImConfig;
import org.jim.common.packets.ChatBody;
import xyz.berby.im.server.service.chat.AbstractChatService;
import org.tio.core.ChannelContext;

/**
 * 文本类型聊天数据处理类
 */
public class TextChatServiceImpl extends AbstractChatService {

    @Override
    public ChatBody handler(ImConfig imConfig, ChatBody chatBody, ChannelContext channelContext) {
        return chatBody;
    }

    @Override
    public boolean validatePackage(ChatBody chatBody) {
        return true;
    }

}
