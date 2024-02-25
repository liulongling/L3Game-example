package com.game.netty.core;

import com.iohao.game.bolt.broker.core.aware.BrokerClientAware;
import com.iohao.game.bolt.broker.core.aware.CmdRegionsAware;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.common.kit.attr.AttrOptions;
import com.iohao.game.core.common.cmd.CmdRegions;
import com.iohao.game.core.common.cmd.DefaultCmdRegions;
import com.iohao.game.external.core.ExternalCoreSetting;
import com.iohao.game.external.core.aware.ExternalCoreSettingAware;
import com.iohao.game.external.core.aware.UserSessionsAware;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.hook.UserHook;
import com.iohao.game.external.core.hook.internal.IdleProcessSetting;
import com.iohao.game.external.core.micro.MicroBootstrap;
import com.iohao.game.external.core.micro.MicroBootstrapFlow;
import com.iohao.game.external.core.session.UserSessions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jctools.maps.NonBlockingHashSet;

import java.util.Objects;
import java.util.Set;


/**
 * ExternalCoreSetting
 *
 * @author 渔民小镇
 * @date 2023-02-19
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class DefaultExternalCoreSetting implements ExternalCoreSetting {
    /** 动态属性 */
    final AttrOptions options = new AttrOptions();
    final CmdRegions cmdRegions = new DefaultCmdRegions();
    /** 目前 ioGame 没有自己的容器管理 IOC/AOP，先用这个变量顶着 */
    final Set<Object> injectObject = new NonBlockingHashSet<>();
    /** 真实玩家连接的端口 */
    @Setter
    int externalCorePort;
    /** 连接方式：默认为 Websocket */
    @Setter
    ExternalJoinEnum externalJoinEnum = ExternalJoinEnum.WEBSOCKET;
    /** 游戏对外服-与真实玩家连接的服务器 */
    MicroBootstrap microBootstrap;
    /** 与真实玩家连接服务器的启动流程 */
    com.iohao.game.external.core.micro.MicroBootstrapFlow<?> microBootstrapFlow;
    /** 心跳相关的设置 */
    IdleProcessSetting idleProcessSetting;
    /** 用户（玩家）session 管理器 */
    UserSessions<?, ?> userSessions;
    /** UserHook 钩子接口，上线时、下线时会触发 */
    UserHook userHook;
    /** 与 Broker（游戏网关）通信的 client */
    @Setter
    BrokerClient brokerClient;

    public void inject() {
        this.injectObject.forEach(this::aware);

        // 心跳 hook 特殊处理
        if (Objects.nonNull(this.idleProcessSetting)) {
            this.aware(this.idleProcessSetting.getIdleHook());
        }
    }

    @Override
    public void aware(Object o) {
        if (o instanceof UserSessionsAware aware) {
            aware.setUserSessions(this.userSessions);
        }

        if (o instanceof BrokerClientAware aware) {
            aware.setBrokerClient(this.brokerClient);
        }

        if (o instanceof CmdRegionsAware aware) {
            aware.setCmdRegions(this.cmdRegions);
        }

        if (o instanceof ExternalCoreSettingAware aware) {
            aware.setExternalCoreSetting(this);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> com.iohao.game.external.core.micro.MicroBootstrapFlow<T> getMicroBootstrapFlow() {
        return (com.iohao.game.external.core.micro.MicroBootstrapFlow<T>) microBootstrapFlow;
    }

    public void setMicroBootstrap(MicroBootstrap microBootstrap) {
        this.microBootstrap = microBootstrap;
        this.injectObject.add(this.microBootstrap);
    }

    public void setMicroBootstrapFlow(MicroBootstrapFlow<?> microBootstrapFlow) {
        this.microBootstrapFlow = microBootstrapFlow;
        this.injectObject.add(this.microBootstrapFlow);
    }

    public void setIdleProcessSetting(IdleProcessSetting idleProcessSetting) {
        this.idleProcessSetting = idleProcessSetting;
        this.injectObject.add(this.idleProcessSetting);
    }

    public void setUserSessions(UserSessions<?, ?> userSessions) {
        this.userSessions = userSessions;
        this.injectObject.add(this.userSessions);
    }

    public void setUserHook(UserHook userHook) {
        this.userHook = userHook;
        this.injectObject.add(this.userHook);
    }
}
