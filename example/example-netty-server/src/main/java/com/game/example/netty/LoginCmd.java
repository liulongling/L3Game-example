package com.game.example.netty;



/**
 * @author liulongling
 * @date 2023-07-21
 */
public interface LoginCmd {
    int cmd = CmdModule.loginCmd;
    int loginVerify = 1;
}
