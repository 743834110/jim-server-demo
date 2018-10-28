/**
 * 
 */
package xyz.berby.im.server.processor.handshake;

import com.jfinal.kit.PropKit;
import org.jim.common.ImAio;
import org.jim.common.ImPacket;
import org.jim.common.ImStatus;
import org.jim.common.Protocol;
import org.jim.common.http.HttpConst;
import org.jim.common.http.HttpRequest;
import org.jim.common.packets.ChatBody;
import org.jim.common.packets.Command;
import org.jim.common.packets.LoginReqBody;
import org.jim.common.packets.RespBody;
import org.jim.common.utils.ChatKit;
import org.jim.common.utils.ImKit;
import org.jim.common.utils.JsonKit;
import org.jim.common.ws.WsSessionContext;
import org.jim.server.ImServerGroupContext;
import org.jim.server.command.CommandManager;
import org.jim.server.command.handler.LoginReqHandler;
import org.jim.server.command.handler.processor.handshake.WsHandshakeProcessor;
import org.tio.core.ChannelContext;

import java.util.Date;

/**
 * @author litianfeng
 * Created on 2018/10/27
 * 握手成功后
 * 发送密钥到客户端当中
 * 以完成登录信息加密操作
 */
public class DemoWsHandshakeProcessor extends WsHandshakeProcessor{


	@Override
	public void onAfterHandshaked(ImPacket packet, ChannelContext channelContext) throws Exception {

	}

}
