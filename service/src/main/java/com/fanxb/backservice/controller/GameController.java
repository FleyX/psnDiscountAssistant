package com.fanxb.backservice.controller;

import com.fanxb.backservice.entity.input.GameListParam;
import com.fanxb.backservice.entity.output.Result;
import com.fanxb.backservice.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/19 18:31
 */
@RestController
@RequestMapping("game")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Description:获取游戏个数
     *
     * @return com.fanxb.backservice.entity.output.Result
     * @author fanxb
     * @date 2019/3/21 15:56
     */
    @GetMapping("getGameNum")
    public Result getGameNum(int serverId, String platform, String type, String language, int isCut) {
        return Result.success(gameService.getCount(serverId, platform, type, language, isCut));
    }

    @GetMapping("getGameList")
    public Result getGameList(GameListParam param) {
        return Result.success(gameService.getList(param));
    }

    @GetMapping("getGameDetail")
    public Result getGameDetail(int gameId) {
        return Result.success(this.gameService.getGameDetail(gameId));
    }


    @GetMapping("search")
    public Result fullTextSearch(int serverId, String platform, String language, String searchStr) {
        return Result.success(this.gameService.fullTextSearch(serverId, platform, language, searchStr));
    }

    @GetMapping("getAttentionGameNum")
    public Result getAttentionGame(int serverId, String platform, String language) {
        return Result.success(gameService.getAttentionCount(serverId, platform, language));
    }

    @GetMapping("getAttentionGameList")
    public Result getAttentionGame(GameListParam param) {
        return Result.success(gameService.getAttentionList(param));
    }
}
