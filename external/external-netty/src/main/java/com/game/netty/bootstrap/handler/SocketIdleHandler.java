/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.game.netty.bootstrap.handler;

import com.game.netty.core.DefaultExternalCoreSetting;
import com.game.netty.session.SocketUserSession;
import com.iohao.game.external.core.ExternalCoreSetting;
import com.iohao.game.external.core.aware.ExternalCoreSettingAware;
import com.iohao.game.external.core.hook.IdleHook;
import com.iohao.game.external.core.hook.internal.IdleProcessSetting;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.message.ExternalMessageCmdCode;
import com.iohao.game.external.core.session.UserSessions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Objects;

/**
 * 心跳相关的 Handler
 *
 * @author 渔民小镇
 * @date 2023-02-18
 */
@ChannelHandler.Sharable
public final class SocketIdleHandler extends ChannelInboundHandlerAdapter
        implements ExternalCoreSettingAware {
    /** 心跳事件回调 */
    IdleHook<IdleStateEvent> idleHook;
    /** true : 响应心跳给客户端 */
    boolean pong;
    UserSessions<ChannelHandlerContext, SocketUserSession> userSessions;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(" Test SocketIdleHandler channelRead");
        ExternalMessage externalMessage = (ExternalMessage) msg;

        // 心跳处理
        int cmdCode = externalMessage.getCmdCode();

        if (cmdCode == ExternalMessageCmdCode.idle) {

            if (this.pong) {
                ctx.writeAndFlush(externalMessage);
            }

            return;
        }

        // 不是心跳请求. 交给下一个业务处理 (handler) , 下一个业务指的是你编排 handler 时的顺序
        ctx.fireChannelRead(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(" Test SocketIdleHandler userEventTriggered");
        if (evt instanceof IdleStateEvent event) {

            boolean close = true;

            var userSession = userSessions.getUserSession(ctx);

            // 执行用户定义的心跳回调事件处理
            if (Objects.nonNull(idleHook)) {
                close = idleHook.callback(userSession, event);
            }

            // close ctx
            if (close) {
                this.userSessions.removeUserSession(userSession);
            }

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setExternalCoreSetting(ExternalCoreSetting externalCoreSetting) {
        System.out.println(" Test SocketIdleHandler setExternalCoreSetting");
        if (Objects.nonNull(this.userSessions)) {
            return;
        }

        DefaultExternalCoreSetting setting = (DefaultExternalCoreSetting) externalCoreSetting;
        IdleProcessSetting idleProcessSetting = setting.getIdleProcessSetting();
        this.idleHook = idleProcessSetting.getIdleHook();
        this.pong = idleProcessSetting.isPong();
        this.userSessions = (UserSessions<ChannelHandlerContext, SocketUserSession>) setting.getUserSessions();
    }
}
