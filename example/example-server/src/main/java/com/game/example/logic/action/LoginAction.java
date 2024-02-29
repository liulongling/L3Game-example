package com.game.example.logic.action;

import com.game.common.cmd.LoginCmd;
import com.game.common.utils.DateUtils;
import com.game.example.db.master.domain.RegisterPlayer;
import com.game.example.db.master.service.IRegisterPlayerService;
import com.game.example.db.master.service.impl.RegisterPlayerServiceImpl;
import com.game.example.db.user.domain.Player;
import com.game.example.db.user.mapper.PlayerMapper;
import com.game.example.jproto.LoginVerify;
import com.game.example.jproto.RegisterInfo;
import com.game.example.jproto.UserInfo;
import com.game.example.logic.BeanContext;
import com.game.example.redis.RedissonManager;
import com.game.example.redis.lock.DistributedLock;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@ActionController(LoginCmd.cmd)
public class LoginAction {
    @Autowired
    private IRegisterPlayerService registerPlayerService = BeanContext.getApplicationContext().getBean(RegisterPlayerServiceImpl.class);
    @Autowired
    private PlayerMapper playerMapper = BeanContext.getApplicationContext().getBean(PlayerMapper.class);

    /**
     * 登录验证
     *
     * @param registerInfo 注册信息
     * @param flowContext  f
     * @return 用户信息
     */
    @ActionMethod(LoginCmd.register)
    public RegisterInfo register(RegisterInfo registerInfo, FlowContext flowContext) throws InterruptedException {
        //检查平台 名字是否合法
        DistributedLock distributedLock = RedissonManager.getInstance().distributedLock();
        distributedLock.tryLockAndExecute("register:lock",10,1,TimeUnit.SECONDS, () -> {
            System.out.println("线程：" + Thread.currentThread().getName() + "拿到锁了");
        });

        log.info("registerInfo {} ", registerInfo);
        RegisterPlayer registerPlayer = new RegisterPlayer();
        registerPlayer.setPlatformId(registerInfo.platformId);
        registerPlayer.setChannelId(registerInfo.channelId);
        registerPlayer.setPlatformUid(registerInfo.platformUid);
        registerPlayerService.insertRegisterPlayer(registerPlayer);

        Player player = new Player();
        player.setUid(registerPlayer.getUid());
        player.setName(registerInfo.name);
        player.setChannelId(registerInfo.getChannelId());
        player.setPlatformId(registerInfo.getPlatformId());
        player.setPlatformUid(registerInfo.getPlatformUid());
        player.setLevel(1);
        player.setToken("222");
        player.setLastLoginTime(null);
        player.setLastOffTime(null);
        player.setCreateTime(DateUtils.getNowDate());
        player.setUpdateTime(DateUtils.getNowDate());
        playerMapper.insert(player);

        return registerInfo;
    }


    /**
     * 登录验证
     *
     * @param loginVerify 登录验证pb
     * @param flowContext f
     * @return 用户信息
     */
    @ActionMethod(LoginCmd.loginVerify)
    public UserInfo loginVerify(LoginVerify loginVerify, FlowContext flowContext) {
        log.info("loginVerify {} ", loginVerify);

        RegisterPlayer registerPlayer = new RegisterPlayer();
        registerPlayer.setPlatformId(loginVerify.platformId);
        registerPlayer.setPlatformUid(loginVerify.platformUid);
        registerPlayer.setChannelId(loginVerify.channelId);

        List<RegisterPlayer> registerPlayers = registerPlayerService.selectRegisterPlayerList(registerPlayer);
        if(registerPlayers == null || registerPlayers.isEmpty()){
            //登录失败
        }
        registerPlayer = registerPlayers.get(0);


        Player player = playerMapper.selectPlayerByUid(registerPlayer.getUid());
        UserInfo userInfo = new UserInfo();
        userInfo.id = player.getUid();
        userInfo.name = player.getName();
        // 登录的关键代码
        // 具体可参考 https://www.yuque.com/iohao/game/tywkqv
        boolean success = UserIdSettingKit.settingUserId(flowContext, player.getUid());

        return userInfo;
    }


}
