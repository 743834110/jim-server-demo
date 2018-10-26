package xyz.berby.im.server.command;

import org.jim.common.Const;
import org.jim.common.ImPacket;
import org.jim.common.packets.ChatBody;
import org.jim.common.packets.ChatType;
import org.jim.common.utils.ChatKit;
import org.jim.server.command.handler.processor.chat.MsgQueueRunnable;
import xyz.berby.im.server.packet.MessageType;
import xyz.berby.im.server.service.chat.IChatService;
import xyz.berby.im.server.service.chat.impl.DefaultFileChatServiceImpl;
import xyz.berby.im.server.service.chat.impl.TextChatServiceImpl;
import org.tio.core.ChannelContext;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author litianfeng
 * Created on 2018/10/19
 * 这个大胆推测，除了握手之外，在这一层一下
 * 已经是协议无关了，因此这里想直接调用service层服务
 */
public class ChatReqHandler extends org.jim.server.command.handler.ChatReqHandler {

    /**
     * 聊天类型到聊天服务类的映射类
     * 0:text、1:image、2:voice、3:video、4:music、5:news、-1: others
     * 0 -> TextChatServiceImpl
     * -1 -> DefaultChatServiceImpl
     */
    private Map<Integer, IChatService> chatServiceMap = new HashMap<Integer, IChatService>() {
        {
            this.put(MessageType.TEXT.getNumber(), new TextChatServiceImpl());
            this.put(MessageType.OTHERS.getNumber(), new DefaultFileChatServiceImpl());
        }

        // 当找不到时返回默认file处理服务类
        @Override
        public IChatService get(Object key) {

            IChatService chatService =  super.get(key);
            return chatService == null? super.get(MessageType.OTHERS.getNumber()): chatService;
        }
    };

    @Override
    public ImPacket handler(ImPacket packet, ChannelContext channelContext) throws Exception {
        if (packet.getBody() == null) {
            throw new Exception("body is null");
        }
        ChatBody chatBody = ChatKit.toChatBody(packet.getBody(), channelContext);
        // 聊天数据格式不正确
        if(chatBody == null || chatBody.getChatType() == null){
            return ChatKit.dataInCorrectRespPacket(channelContext);
        }

        // 调用服务层方法，返回修改后的chatBody对象
        Integer messageType = chatBody.getMsgType();
        IChatService chatService = chatServiceMap.get(messageType);
        if (!chatService.validatePackage(chatBody))
            // 聊天数据格式不正确
            return ChatKit.dataInCorrectRespPacket(channelContext);
        chatBody = chatService.handler(this.imConfig, chatBody, channelContext);

        // 异步调用业务处理消息接口:持久化
        Integer chatType = chatBody.getChatType();
        if(ChatType.forNumber(chatType) != null){
            MsgQueueRunnable msgQueueRunnable = (MsgQueueRunnable)channelContext.getAttribute(Const.CHAT_QUEUE);
            msgQueueRunnable.addMsg(packet);
            msgQueueRunnable.getExecutor().execute(msgQueueRunnable);
        }

        return chatService.send(packet.getSynSeq(), chatBody, imConfig, channelContext);
    }
}
