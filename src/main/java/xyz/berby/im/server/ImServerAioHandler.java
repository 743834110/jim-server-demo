package xyz.berby.im.server;

import org.jim.common.Const;
import org.jim.common.ImConfig;
import org.jim.common.ImSessionContext;
import org.jim.server.*;
import org.jim.server.command.handler.processor.chat.MsgQueueRunnable;
import org.jim.server.handler.AbProtocolHandler;
import org.jim.server.handler.ProtocolHandlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2018/10/27.
 */
public class ImServerAioHandler implements ServerAioHandler {

    private ImConfig imConfig;

    ImServerAioHandler(ImConfig imConfig) {
        this.imConfig = imConfig;
    }
    /**
     * @see org.tio.core.intf.AioHandler#handler(Packet, ChannelContext)
     *
     * @param packet
     * @return
     * @throws Exception
     * @author: Wchao
     * 2016年11月18日 上午9:37:44
     *
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        ImSessionContext imSessionContext = (ImSessionContext)channelContext.getAttribute();
        AbProtocolHandler handler = (AbProtocolHandler)imSessionContext.getProtocolHandler();
        if(handler != null){
            handler.handler(packet, channelContext);
        }
    }

    /**
     * @see org.tio.core.intf.AioHandler#encode(Packet, GroupContext, ChannelContext)
     *
     * @param packet
     * @return
     * @author: Wchao
     * 2016年11月18日 上午9:37:44
     *
     */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        ImSessionContext imSessionContext = (ImSessionContext)channelContext.getAttribute();
        AbProtocolHandler handler = (AbProtocolHandler)imSessionContext.getProtocolHandler();
        if(handler != null){
            return handler.encode(packet, groupContext, channelContext);
        }
        return null;
    }

    /**
     * @see org.tio.core.intf.AioHandler#decode(ByteBuffer, int, int, int, ChannelContext)
     *
     * @param buffer
     * @return
     * @throws AioDecodeException
     * @author: Wchao
     * 2016年11月18日 上午9:37:44
     *
     */
    @Override
    public Packet decode(ByteBuffer buffer,int limit, int position, int readableLength,ChannelContext channelContext) throws AioDecodeException {
        ImSessionContext imSessionContext = (ImSessionContext)channelContext.getAttribute();
        AbProtocolHandler handler = null;
        if(imSessionContext == null){
            handler = ProtocolHandlerManager.initServerHandlerToChannelContext(buffer, channelContext);
            ImServerGroupContext imGroupContext = (ImServerGroupContext)imConfig.getGroupContext();
            channelContext.setAttribute(Const.CHAT_QUEUE,new MsgQueueRunnable(channelContext,imGroupContext.getTimExecutor()));
        }else{
            handler = (AbProtocolHandler)imSessionContext.getProtocolHandler();
        }
        if(handler != null){
            return handler.decode(buffer, channelContext);
        }else{
            throw new AioDecodeException("不支持的协议类型,无法找到对应的协议解码器!");
        }
    }

    public ImConfig getImConfig() {
        return imConfig;
    }
    public void setImConfig(ImConfig imConfig) {
        this.imConfig = imConfig;
    }

}
