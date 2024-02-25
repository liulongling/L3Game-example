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
package com.iohao.game.external.core.aware;

import com.iohao.game.external.core.session.UserSessions;

/**
 * UserSessions Aware
 *
 * @author 渔民小镇
 * @date 2023-02-19
 */
public interface UserSessionsAware {
    /**
     * 框架会调用此方法，将 UserSessions 对象传入
     *
     * @param userSessions userSessions
     */
    void setUserSessions(UserSessions<?, ?> userSessions);
}
