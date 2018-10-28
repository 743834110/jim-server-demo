package xyz.berby.im.server.config;

import com.jfinal.kit.PropKit;
import org.jim.common.ImConfig;
import org.jim.common.config.ImConfigBuilder;
import org.jim.common.http.HttpConfig;
import org.jim.common.ws.WsServerConfig;

/**
 * Created by Administrator on 2018/10/26.
 */
public class PropertyImConfigBuilder extends ImConfigBuilder {

    public PropertyImConfigBuilder(String file) {
        PropKit.use(file);
    }

    @Override
    public ImConfigBuilder configHttp(HttpConfig httpConfig) {
        String pageRoot = PropKit.get("jim.http.page");//html/css/js等的根目录，支持classpath:，也支持绝对路径
        String[] scanPackages = PropKit.get("jim.http.scan.packages").split(",");//j-im mvc需要扫描的根目录包
        httpConfig.setBindPort((PropKit.getInt("jim.port")));
        httpConfig.setPageRoot(pageRoot);//设置web访问路径;
        httpConfig.setMaxLiveTimeOfStaticRes(PropKit.getInt("jim.http.max.live.time"));//不缓存资源;
        httpConfig.setScanPackages(scanPackages);//设置j-im mvc扫描目录;
        return this;
    }

    @Override
    public ImConfigBuilder configWs(WsServerConfig wsServerConfig) {

        return this;
    }


    public ImConfig build() {
        super.build();
        this.setBindIp(PropKit.get("jim.bind.ip"));
        this.setBindPort(PropKit.getInt("jim.port"));
        this.setHeartbeatTimeout(PropKit.getLong("jim.heartbeat.timeout"));
        this.setIsStore(PropKit.get("jim.store"));
        this.setIsCluster(PropKit.get("jim.cluster"));
        this.setIsSSL(PropKit.get("jim.ssl"));
        return conf;
    }

}
