package xyz.berby.im.server;

import org.jim.common.ImConfig;

public class ServerConfig extends ImConfig {
    /**
     * Base64编码私钥
     */
    private String privateKeyBase64;
    /**
     * Base64编码
     */
    private String publicKeyBase64;


    public String getPrivateKeyBase64() {
        return privateKeyBase64;
    }

    public void setPrivateKeyBase64(String privateKeyBase64) {
        this.privateKeyBase64 = privateKeyBase64;
    }

    public String getPublicKeyBase64() {
        return publicKeyBase64;
    }

    public void setPublicKeyBase64(String publicKeyBase64) {
        this.publicKeyBase64 = publicKeyBase64;
    }
}
