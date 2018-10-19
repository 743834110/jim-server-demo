package org.jim.server.demo.service.chat.impl;

import org.jim.common.packets.ChatBody;
import org.jim.common.packets.ChatType;
import org.jim.common.utils.ChatKit;
import org.jim.server.command.handler.processor.chat.AbstractChatProcessor;
import org.jim.server.demo.packet.FilePacket;
import org.jim.server.demo.packet.MessageType;
import org.jim.server.demo.service.chat.IChatService;
import org.jim.server.demo.util.PersistentKit;
import org.jim.server.helper.redis.RedisMessageHelper;
import org.tio.core.ChannelContext;

/**
 * 默认文件聊天类型的数据服务类
 * 应该屏蔽IO层中发生的存储操作的细节
 */
public class DefaultFileChatServiceImpl implements IChatService {



    @Override
    public ChatBody handler(ChatBody chatBody, ChannelContext channelContext) {

        FilePacket filePacket = chatBody.getExtras().getJSONObject("file").toJavaObject(FilePacket.class);
        String content = chatBody.getContent();
        filePacket.setContent(content);

        String sessionId = null;
        // 这里要区分是私聊信息还是公聊信息
        if (ChatType.CHAT_TYPE_PUBLIC.getNumber() == chatBody.getChatType()) {
            sessionId = chatBody.getGroup_id();
        }
        else {
            sessionId = ChatKit.sessionId(chatBody.getFrom(), chatBody.getTo());
        }
        filePacket.setSessionId(sessionId);
        PersistentKit.persistFile(filePacket);

        return chatBody;
    }

    @Override
    public boolean validatePackage(ChatBody chatBody) {


        return true;
    }
}
