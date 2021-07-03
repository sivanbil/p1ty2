package org.example.dinner.oauth2.server.config;

import org.example.dinner.commons.model.domain.SignInIdentity;
import org.example.dinner.oauth2.server.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private ClientOAuth2DataConfiguration clientOAuth2DataConfiguration;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisTokenStore redisTokenStore;

    @Resource
    private UserService userService;
    /**
     * 配置令牌端点安全约束
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许访问token的公钥，默认/oauth/token_key是受保护的
        security.tokenKeyAccess("permitAll()")
                // 允许检查token的状态,默认/oauth/check_token是受保护的
                .checkTokenAccess("permitAll()");
    }

    /**
     * 配置授权模型
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientOAuth2DataConfiguration.getClientId())
                .secret(passwordEncoder.encode(clientOAuth2DataConfiguration.getSecret()))
                .authorizedGrantTypes(clientOAuth2DataConfiguration.getGrantTypes())
                .accessTokenValiditySeconds(clientOAuth2DataConfiguration.getTokenValidityTime())
                .refreshTokenValiditySeconds(clientOAuth2DataConfiguration.getRefreshTokenValidityTime())
                // 作用范围
                .scopes(clientOAuth2DataConfiguration.getScopes());
    }

    /**
     * 配置授权以及令牌访问端点和令牌服务的
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .tokenStore(redisTokenStore)
                .tokenEnhancer((accessToken, authentication) -> {
                    // 获取
                    SignInIdentity signInIdentity = (SignInIdentity) authentication.getPrincipal();
                    DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
                    LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                    map.put("nickname", signInIdentity.getNickname());
                    map.put("avatar", signInIdentity.getAvatar());
                    token.setAdditionalInformation(map);

                    return token;
                });
    }
}
