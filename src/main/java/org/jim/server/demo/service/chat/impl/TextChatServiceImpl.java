package org.jim.server.demo.service.chat.impl;

import org.jim.common.ImConfig;
import org.jim.common.ImPacket;
import org.jim.common.packets.ChatBody;
import org.jim.server.demo.service.chat.AbstractChatService;
import org.jim.server.demo.service.chat.IChatService;
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
