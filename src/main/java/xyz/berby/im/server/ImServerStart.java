/**
 * 
 */
package xyz.berby.im.server;

import org.apache.commons.lang3.StringUtils;
import org.jim.common.Const;
import org.jim.common.ImConfig;
import org.jim.common.config.PropertyImConfigBuilder;
import org.jim.common.packets.Command;
import org.jim.server.ImServerStarter;
import org.jim.server.command.CommandManager;
import org.jim.server.command.handler.HandshakeReqHandler;
import org.jim.server.command.handler.LoginReqHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.ui.context.support.UiApplicationContextUtils;
import xyz.berby.im.server.processor.DemoWsHandshakeProcessor;
import xyz.berby.im.server.listener.ImDemoGroupListener;
import xyz.berby.im.server.service.LoginServiceProcessor;
import org.jim.server.listener.ImServerAioListener;
import org.tio.core.ChannelContext;
import org.tio.core.ssl.SslConfig;
import com.jfinal.kit.PropKit;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * IM服务端DEMO演示启动类;
 * @author WChao
 * @date 2018-04-05 23:50:25
 */

@Configuration
@ImportResource("classpath:spring-im.xml")
public class ImServerStart {




	public static void main(String[] args)throws Exception{

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImServerStart.class);


		ImConfig imConfig = new PropertyImConfigBuilder("jim.properties")
				.build();
		imConfig.setIsSSL("on");

		initSsl(imConfig);

		//　设置群组监听器，非必须，根据需要自己选择性实现;
		imConfig.setImGroupListener(new ImDemoGroupListener());
		ImServerStarter imServerStarter = new ImServerStarter(imConfig, new ImServerAioListener(imConfig) {
			@Override
			public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) {
			}
		});

		imServerStarter.start();

	}

	/**
	 * 开启SSL
	 * @param imConfig
	 * @throws Exception
	 */
	private static void initSsl(ImConfig imConfig) throws Exception {
		//开启SSL
		if(Const.ON.equals(imConfig.getIsSSL())){
			String keyStorePath = PropKit.get("jim.key.store.path");
			String keyStoreFile = keyStorePath;
			String trustStoreFile = keyStorePath;
			String keyStorePwd = PropKit.get("jim.key.store.pwd");
			if (StringUtils.isNotBlank(keyStoreFile) && StringUtils.isNotBlank(trustStoreFile)) {
				SslConfig sslConfig = SslConfig.forServer(keyStoreFile, trustStoreFile, keyStorePwd);
				imConfig.setSslConfig(sslConfig);
			}
		}
	}


}
