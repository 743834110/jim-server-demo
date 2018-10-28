/**
 * 
 */
package xyz.berby.im.server;

import org.apache.commons.lang3.StringUtils;
import org.jim.common.Const;
import org.jim.common.ImConfig;
import org.jim.common.packets.Command;
import org.jim.server.ImServerStarter;
import org.jim.server.command.CommandManager;
import org.jim.server.command.handler.processor.login.LoginProcessorIntf;
import org.jim.server.handler.ImServerAioHandler;
import org.jim.server.listener.ImServerAioListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.berby.im.ImDataBaseAutoConfigure;
import xyz.berby.im.server.config.PropertyImConfigBuilder;
import xyz.berby.im.server.listener.ImDemoGroupListener;
import org.tio.core.ssl.SslConfig;
import com.jfinal.kit.PropKit;
import xyz.berby.im.server.service.LoginServiceProcessor;

import java.io.IOException;

/**
 * IM服务端启动类;
 * @author WChao
 * @date 2018-04-05 23:50:25
 */

@EnableAutoConfiguration(exclude = ImServerAutoConfigure.class)
public class ImServerAutoConfigure {

	@Bean
	public ImConfig imConfig() throws Exception {
		ImConfig imConfig = new PropertyImConfigBuilder("jim.properties")
				.build();
		initSsl(imConfig);

		//　设置群组监听器，非必须，根据需要自己选择性实现;
		imConfig.setImGroupListener(new ImDemoGroupListener());
		ImServerStarter imServerStarter = new ImServerStarter(imConfig, new ImServerAioListener(imConfig));
		imServerStarter.start();

		return imConfig;
	}

	/**
	 * 登录细节处理类
	 * @return LoginProcessorIntf
	 */
	@Bean
	public LoginProcessorIntf loginServiceProcessor() {
		LoginProcessorIntf processorIntf = new LoginServiceProcessor();
		CommandManager.getCommand(Command.COMMAND_LOGIN_REQ).addProcessor(processorIntf);
		return processorIntf;
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


	public static void main(String[] args)throws Exception{

		long first = System.currentTimeMillis();
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImServerAutoConfigure.class);
		System.out.println("加载spring容器耗时：" + (System.currentTimeMillis() - first) + "ms");
	}


}
