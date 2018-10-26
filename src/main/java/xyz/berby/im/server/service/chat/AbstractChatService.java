package xyz.berby.im.server.service.chat;

import org.jim.common.ImAio;
import org.jim.common.ImConfig;
import org.jim.common.ImPacket;
import org.jim.common.packets.ChatBody;
import org.jim.common.packets.ChatType;
import org.jim.common.packets.Command;
import org.jim.common.packets.RespBody;
import org.jim.common.utils.ChatKit;
import org.tio.core.ChannelContext;

/**
 * Created by Administrator on 2018/10/21.
 * @author litianfeng
 * Created on 2018/10/21
 */
public abstract class AbstractChatService implements IChatService{

    public ImPacket send(Integer synSeq, ChatBody chatBody, ImConfig imConfig, ChannelContext channelContext) throws Exception {

        Integer chatType = chatBody.getChatType();
        ImPacket chatPacket = new ImPacket(Command.COMMAND_CHAT_REQ,new RespBody(Command.COMMAND_CHAT_REQ,chatBody).toByte());
        chatPacket.setSynSeq(synSeq);//设置同步序列号;
        if(ChatType.CHAT_TYPE_PRIVATE.getNumber() == chatType){//私聊
            String toId = chatBody.getTo();
            if(ChatKit.isOnline(toId,imConfig)){
                ImAio.sendToUser(toId, chatPacket);
                return ChatKit.sendSuccessRespPacket(channelContext);//发送成功响应包
            }else{
                return ChatKit.offlineRespPacket(channelContext);//用户不在线响应包
            }
        }else if(ChatType.CHAT_TYPE_PUBLIC.getNumber() == chatType){//群聊
            String group_id = chatBody.getGroup_id();
            ImAio.sendToGroup(group_id, chatPacket);
            return ChatKit.sendSuccessRespPacket(channelContext);//发送成功响应包
        }
        return null;
    }
}
