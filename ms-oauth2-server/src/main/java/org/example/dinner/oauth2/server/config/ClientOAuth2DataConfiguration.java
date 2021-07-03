package org.example.dinner.oauth2.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client.oauth2")
@Data
public class ClientOAuth2DataConfiguration {

    // 客户端标识ID
    private String clientId;

    // 客户端安全码
    private String secret;

    // 授权类型
    private String[] grantTypes;

    // token有效期
    private Integer tokenValidityTime;

    // refresh-token有效期
    private Integer refreshTokenValidityTime;

    // 访问权限
    private String[] scopes;
}
