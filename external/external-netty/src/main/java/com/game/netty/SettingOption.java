package com.game.netty;


import com.game.netty.bootstrap.handler.SocketIdleHandler;
import com.game.netty.bootstrap.handler.SocketRequestBrokerHandler;
import com.game.netty.handler.SocketCmdAccessAuthHandler;
import com.game.netty.handler.SocketUserSessionHandler;
import com.iohao.game.common.kit.attr.AttrOption;

/**
 * @author 渔民小镇
 * @date 2024-02-03
 */
public interface SettingOption {
    AttrOption<SocketUserSessionHandler> socketUserSessionHandler =
            AttrOption.valueOf("SocketUserSessionHandler");

    AttrOption<SocketCmdAccessAuthHandler> socketCmdAccessAuthHandler =
            AttrOption.valueOf("SocketCmdAccessAuthHandler");

    AttrOption<SocketRequestBrokerHandler> socketRequestBrokerHandler =
            AttrOption.valueOf("SocketRequestBrokerHandler");

    AttrOption<SocketIdleHandler> socketIdleHandler = AttrOption.valueOf("SocketIdleHandler");

}
