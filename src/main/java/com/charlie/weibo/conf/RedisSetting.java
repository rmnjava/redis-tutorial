package com.charlie.weibo.conf;

import com.charlie.weibo.utils.SerializeUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dhy on 17-3-24.
 * Redis配置文件, SpringBoot 风格
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisSetting {
    private String host;
    private int port;
    private int database;
    private String password;
    private int timeout;

    private Sentinel sentinel;
    private Pool pool;

    public static class Pool {
        private boolean blockWhenExhausted;
        private int maxIdle;
        private int maxTotal;
        private int minEvictableIdleTimeMillis;

        public boolean isBlockWhenExhausted() {
            return blockWhenExhausted;
        }

        public void setBlockWhenExhausted(boolean blockWhenExhausted) {
            this.blockWhenExhausted = blockWhenExhausted;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }
    }

    public static class Sentinel {
        private String master;
        private Set<String> nodes;

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public Set<String> getNodes() {
            return nodes;
        }

        public void setNodes(String nodes) {
            if (this.nodes == null) {
                this.nodes = new HashSet<>();
            }
            Collections.addAll(this.nodes, nodes.split(","));
        }
    }

    public static class Cluster {
        private int maxRedirects;
        private Set<String> nodes;

        public int getMaxRedirects() {
            return maxRedirects;
        }

        public void setMaxRedirects(int maxRedirects) {
            this.maxRedirects = maxRedirects;
        }

        public Set<String> getNodes() {
            return nodes;
        }

        public void setNodes(String nodes) {
            if (this.nodes == null) {
                this.nodes = new HashSet<>();
            }
            Collections.addAll(this.nodes, nodes.split(","));
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Sentinel getSentinel() {
        return sentinel;
    }

    public void setSentinel(Sentinel sentinel) {
        this.sentinel = sentinel;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }
}
