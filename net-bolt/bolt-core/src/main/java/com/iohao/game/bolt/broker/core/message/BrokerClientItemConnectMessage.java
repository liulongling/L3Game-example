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
package com.iohao.game.bolt.broker.core.message;

import java.io.Serial;
import java.io.Serializable;

/**
 * bolt RpcClient.startup 后，需要发送消息才会建立连接
 * <pre>
 *     这里发送一个空消息
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-05-16
 */
public class BrokerClientItemConnectMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1148652635062833923L;
}
