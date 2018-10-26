package xyz.berby.im.server.util;

import org.jim.common.ImPacket;
import org.jim.common.ImStatus;
import org.jim.common.packets.Command;
import org.jim.common.packets.RespBody;
import org.jim.common.utils.ImKit;
import org.tio.core.ChannelContext;

/**
 * Created by Administrator on 2018/10/21.
 * 发送聊天报文工具包
 */
public class CustomChatKit{

    public static ImPacket sendSuccessRespPacket(ChannelContext channelContext, Object data) throws Exception{
        RespBody chatDataInCorrectRespPacket = new RespBody(Command.COMMAND_CHAT_RESP, ImStatus.C10000);
        chatDataInCorrectRespPacket.setData(data);
        ImPacket respPacket = ImKit.ConvertRespPacket(chatDataInCorrectRespPacket, channelContext);
        respPacket.setStatus(ImStatus.C10000);
        return respPacket;
    }
}
