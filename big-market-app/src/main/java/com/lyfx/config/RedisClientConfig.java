package com.lyfx.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Yangfeixaio Liu
 * @time 3/5/2024 下午8:27
 * @description Redis 客户端使用Redisson
 */
@Configuration
@EnableConfigurationProperties(RedisClientConfigProperties.class)
public class RedisClientConfig {
    @Bean("redissonClient")
    public RedissonClient redissonClient(ConfigurableApplicationContext context, RedisClientConfigProperties properties) {
        Config config = new Config();
        
        config.useSingleServer()
                .setAddress("redis://"+properties.getHost()+":"+properties.getPort())
                .setConnectionPoolSize(properties.getPoolSize())
                .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                .setConnectTimeout(properties.getConnectTimeout())
                .setRetryAttempts(properties.getRetryAttempts())
                .setRetryInterval(properties.getRetryInterval())
                .setPingConnectionInterval(properties.getPingInterval())
                .setKeepAlive(properties.isKeepAlive());
        config.setCodec(new RedisCodec());
        
        return Redisson.create(config);
    }
    
    static class RedisCodec extends BaseCodec {
        private final Encoder encoder = in -> {
            ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
            try {
                ByteBufOutputStream os = new ByteBufOutputStream(out);
                JSON.writeTo(os, in, JSONWriter.Feature.WriteClassName);
                return os.buffer();
            } catch (Exception e) {
                out.release();
                throw new IOException(e);
            }
        };
        private final Decoder<Object> decoder = (buf, state) -> JSON.parseObject(new ByteBufInputStream(buf),
                Object.class, JSONReader.Feature.SupportAutoType);
        @Override
        public Decoder<Object> getValueDecoder() {
            return decoder;
        }
        
        @Override
        public Encoder getValueEncoder() {
            return encoder;
        }
    }
}
