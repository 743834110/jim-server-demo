package org.jim.server.demo.service.chat.impl;

import org.jim.common.packets.ChatBody;
import org.jim.server.demo.packet.FilePacket;
import org.jim.server.demo.service.chat.IChatService;
import org.tio.core.ChannelContext;

/**
 * 默认文件聊天类型的数据服务类
 */
public class DefaultFileChatServiceImpl implements IChatService {



    @Override
    public ChatBody handler(ChatBody chatBody, ChannelContext channelContext) {

        FilePacket filePacket = chatBody.getExtras().toJavaObject(FilePacket.class);
        return chatBody;
    }

    @Override
    public boolean validatePackage(ChatBody chatBody) {


        return true;
    }
}
