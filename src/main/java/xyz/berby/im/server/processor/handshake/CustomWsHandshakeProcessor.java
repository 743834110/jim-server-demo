/**
 * 
 */
package xyz.berby.im.server.processor.handshake;

import org.jim.common.ImAio;
import org.jim.common.ImPacket;
import org.jim.common.ImStatus;
import org.jim.common.packets.ChatBody;
import org.jim.common.packets.Command;
import org.jim.common.packets.RespBody;
import org.jim.common.utils.ChatKit;
import org.jim.server.command.handler.processor.handshake.WsHandshakeProcessor;
import org.tio.core.ChannelContext;
import xyz.berby.im.entity.ServerConfig;
import xyz.berby.im.server.util.CustomKit;
import xyz.berby.im.service.ServerConfigService;
import xyz.berby.im.vo.Pager;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author litianfeng
 * Created on 2018/10/27
 * 握手成功后
 * 发送密钥到客户端当中
 * 以完成登录信息加密操作
 */
public class CustomWsHandshakeProcessor extends WsHandshakeProcessor{

	@Resource
	private ServerConfigService serverConfigService;

	private final static String PUBLIC_KEY_BASE64 = "publicKeyBase64";

	@Override
	public void onAfterHandshaked(ImPacket packet, ChannelContext channelContext) throws Exception {

		Pager<ServerConfig> pager = new Pager<>(new ServerConfig(PUBLIC_KEY_BASE64));
		String value = this.serverConfigService
				.queryByPager(pager).getResult().get(0).getMappingValue();
		Map<String, Object> map = new HashMap<>();
		map.put(PUBLIC_KEY_BASE64, value);

		RespBody respBody = new RespBody(Command.COMMAND_HANDSHAKE_RESP, map);
		ImPacket imPacket = new ImPacket(respBody.toByte());
		ImAio.send(channelContext, imPacket);

	}

}
