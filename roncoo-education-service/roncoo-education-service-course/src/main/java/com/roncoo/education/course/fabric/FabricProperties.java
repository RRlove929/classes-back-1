package com.roncoo.education.course.fabric;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "fabric")
@Data
public class FabricProperties {
    private String channelName;
    private String chaincodeName;
    private String defaultClient;
    private List<FabricClientConf> clients;


    @Data
    public static class FabricClientConf {
        private String mspId;
        private String peerEndpoint;
        private String overrideAuth;
        private String cryptoPath;
        private String certDirPath;
        private String keyDirPath;
        private String tlsCertPath;

    }
}
