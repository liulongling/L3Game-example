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
package com.iohao.game.bolt.broker.server.aware;

import com.iohao.game.bolt.broker.server.BrokerServer;

/**
 * BrokerServerAware
 * <pre>
 *     设置与 broker（游戏网关）server
 *
 *     只要 bolt 处理器（逻辑处理器和连接器）实现了该接口，框架会调用 setBrokerServer 方法并赋值
 *
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-05-16
 */
public interface BrokerServerAware {
    /**
     * set BrokerServer
     *
     * @param brokerServer brokerServer
     */
    void setBrokerServer(BrokerServer brokerServer);
}
