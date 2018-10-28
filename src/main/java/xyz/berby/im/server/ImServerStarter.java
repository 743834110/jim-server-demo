package xyz.berby.im.server;

import org.jim.common.Const;
import org.jim.common.ImConfig;
import org.jim.server.helper.redis.RedisMessageHelper;
import org.jim.server.listener.ImGroupListener;
import org.jim.server.listener.ImServerAioListener;
import org.tio.core.intf.GroupListener;
import org.tio.core.ssl.SslConfig;
import org.tio.server.AioServer;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/27.
 * @author litianfeng
 * Created on 2018/10/27
 *
 */
public class ImServerStarter {

    private ImServerAioListener imAioListener = null;
    private AioServer aioServer = null;
    private ImConfig imConfig = null;
    private ImServerAioHandler imAioHandler = null;


    public ImServerStarter(ImConfig imConfig, ImServerAioListener imAioListener, ImServerAioHandler imAioHandler){
        this.imConfig = imConfig;
        this.imAioListener = imAioListener;
        this.imAioHandler = imAioHandler;
        init();
    }

    private void init(){
        System.setProperty("tio.default.read.buffer.size", String.valueOf(imConfig.getReadBufferSize()));
        if (this.imAioHandler == null)
            this.imAioHandler = new ImServerAioHandler(imConfig);
        if(imAioListener == null){
            imAioListener = new ImServerAioListener(imConfig);
        }
        GroupListener groupListener = imConfig.getImGroupListener();
        if(groupListener == null){
            imConfig.setImGroupListener(new ImGroupListener());
        }
        ImGroupListener imGroupListener = (ImGroupListener) imConfig.getImGroupListener();
        ImServerGroupContext imServerGroupContext = new ImServerGroupContext(imConfig, imAioHandler, imAioListener);
        imServerGroupContext.setGroupListener(imGroupListener);
        if(imConfig.getMessageHelper() == null){
            imConfig.setMessageHelper(new RedisMessageHelper(imConfig));
        }
        if(Const.ON.equals(imConfig.getIsSSL())){//开启SSL
            SslConfig sslConfig = imConfig.getSslConfig();
            if(sslConfig != null) {
                imServerGroupContext.setSslConfig(sslConfig);
            }
        }
        aioServer = new AioServer(imServerGroupContext);
    }

    void start() throws IOException {
        aioServer.start(this.imConfig.getBindIp(),this.imConfig.getBindPort());
    }

    public void stop(){
        aioServer.stop();
    }
}
