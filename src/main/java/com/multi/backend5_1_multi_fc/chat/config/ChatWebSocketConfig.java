package com.multi.backend5_1_multi_fc.chat.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${spring.rabbitmq.host}")
    private String relayHost;
    @Value("${spring.rabbitmq.stomp-port}")
    private int relayPort;
    @Value("${spring.rabbitmq.username}")
    private String relayUsername;
    @Value("${spring.rabbitmq.password}")
    private String relayPassword;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic","/queue","/exchange")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setSystemLogin(relayUsername)
                .setSystemPasscode(relayPassword);
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOriginPatterns("*").withSockJS();
    }
}
