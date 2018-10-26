package xyz.berby.im.server.runnable;

import org.jim.common.ImPacket;
import org.jim.common.utils.ChatKit;
import org.jim.server.command.handler.processor.chat.MsgQueueRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.thread.pool.AbstractQueueRunnable;

import java.util.AbstractQueue;
import java.util.concurrent.Executor;

/**
 * 可执行对象
 * 以持久化消息
 * Created on 2018/10/19
 */
public class ExternalQueueRunnable extends AbstractQueueRunnable<ImPacket> {

    private Logger log = LoggerFactory.getLogger(MsgQueueRunnable.class);

    private ChannelContext channelContext = null;



    /**
     * @param executor
     * @param channelContext
     * @author litianfeng
     */
    public ExternalQueueRunnable(Executor executor, ChannelContext channelContext) {
        super(executor);
        this.channelContext = channelContext;
    }

    @Override
    public void runTask() {

    }
}
