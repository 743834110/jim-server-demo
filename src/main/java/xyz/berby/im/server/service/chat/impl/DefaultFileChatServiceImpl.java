package xyz.berby.im.server.service.chat.impl;

import com.alibaba.fastjson.JSONObject;
import org.jim.common.ImAio;
import org.jim.common.ImConfig;
import org.jim.common.ImPacket;
import org.jim.common.packets.ChatBody;
import org.jim.common.packets.ChatType;
import org.jim.common.packets.Command;
import org.jim.common.packets.RespBody;
import org.jim.common.utils.ChatKit;
import xyz.berby.im.server.packet.FilePacket;
import xyz.berby.im.server.service.chat.AbstractChatService;
import xyz.berby.im.server.util.CustomKit;
import xyz.berby.im.server.util.PersistentKit;
import org.tio.core.ChannelContext;

import java.io.File;
import java.io.IOException;

/**
 * 默认文件聊天类型的数据服务类
 * 应该屏蔽IO层中发生的存储操作的细节
 */
public class DefaultFileChatServiceImpl extends AbstractChatService{

    @Override
    public ChatBody handler(ImConfig imConfig, ChatBody chatBody, ChannelContext channelContext) {

        JSONObject fileJsonObject = chatBody.getExtras().getJSONObject("file");
        FilePacket filePacket = fileJsonObject.toJavaObject(FilePacket.class);
        String sessionId = null;
        // 这里要区分是私聊信息还是公聊信息
        if (ChatType.CHAT_TYPE_PUBLIC.getNumber() == chatBody.getChatType()) {
            sessionId = chatBody.getGroup_id();
        }
        else {
            sessionId = ChatKit.sessionId(chatBody.getFrom(), chatBody.getTo());
        }
        filePacket.setSessionId(sessionId);

        try {
            String content = chatBody.getContent();
            filePacket.setContent(content);
            File file = PersistentKit.persistFile(filePacket);
            // 替换content的内容为确切地存在与im系统中的URL
            // 替换文件名为存在与用户独立空间中文件名
            chatBody.setContent("https://www.baidu.com");
            fileJsonObject.put("name", file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chatBody;
    }

    @Override
    public boolean validatePackage(ChatBody chatBody) {
        return true;
    }

    /**
     *
     * @param synSeq 同步序号
     * @param chatBody
     * @param imConfig
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public ImPacket send(Integer synSeq, ChatBody chatBody, ImConfig imConfig, ChannelContext channelContext) throws Exception {

        Integer chatType = chatBody.getChatType();
        ImPacket chatPacket = new ImPacket(Command.COMMAND_CHAT_REQ,new RespBody(Command.COMMAND_CHAT_REQ,chatBody).toByte());
        chatPacket.setSynSeq(synSeq);//设置同步序列号;
        FilePacket filePacket = chatBody.getExtras().getJSONObject("file").toJavaObject(FilePacket.class);
        // 最后一个文件分片，通知自己和其他通道接受信息
        if (filePacket.isTheLastOne()) {

            if(ChatType.CHAT_TYPE_PRIVATE.getNumber() == chatType){//私聊
                String toId = chatBody.getTo();
                if(ChatKit.isOnline(toId,imConfig)){
                    ImAio.sendToUser(toId, chatPacket);
                    return CustomKit.sendChatSuccessRespPacket(channelContext, chatBody);//发送成功响应包
                }else{
                    // 暂定, 为了看到结果
                    return CustomKit.sendChatSuccessRespPacket(channelContext, chatBody);//用户不在线响应包
                }
            }else if(ChatType.CHAT_TYPE_PUBLIC.getNumber() == chatType){//群聊
                String group_id = chatBody.getGroup_id();
                ImAio.sendToGroup(group_id, chatPacket);
                return CustomKit.sendChatSuccessRespPacket(channelContext, channelContext);//发送成功响应包
            }
            // 未知聊天类型，返回null
            return null;
        }
        // 并非最后文件分片,进行进度通知
        else {
            return CustomKit.sendChatSuccessRespPacket(channelContext, chatBody);
        }

    }
}
