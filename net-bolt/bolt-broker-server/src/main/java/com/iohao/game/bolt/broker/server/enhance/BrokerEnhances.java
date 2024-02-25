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
package com.iohao.game.bolt.broker.server.enhance;

import com.iohao.game.bolt.broker.server.BrokerServerBuilder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashSet;

import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author 渔民小镇
 * @date 2023-06-16
 */
@Slf4j
@UtilityClass
public class BrokerEnhances {
    final Set<BrokerEnhance> enhanceSet = new NonBlockingHashSet<>();

    static {
        ServiceLoader.load(BrokerEnhance.class).forEach(BrokerEnhances::add);
    }

    void add(BrokerEnhance enhance) {
        enhanceSet.add(enhance);
    }

    public void enhance(BrokerServerBuilder builder) {
        for (BrokerEnhance enhance : enhanceSet) {
            enhance.enhance(builder);
        }
    }
}
