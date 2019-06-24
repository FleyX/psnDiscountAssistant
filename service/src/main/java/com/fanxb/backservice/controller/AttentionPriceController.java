package com.fanxb.backservice.controller;

import com.fanxb.backservice.entity.AttentionPrice;
import com.fanxb.backservice.entity.output.Result;
import com.fanxb.backservice.service.AttentionPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 类功能简述：
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/4/10 19:36
 */
@RestController
@RequestMapping("/attention_price")
public class AttentionPriceController {

    @Autowired
    private AttentionPriceService attentionPriceService;

    @PutMapping("")
    public Result addOne(@RequestBody AttentionPrice attentionPrice) {
        this.attentionPriceService.addOne(attentionPrice);
        return Result.success("");
    }

    @DeleteMapping("{id}")
    public Result deleteOne(@PathVariable("id") int gameId) {
        this.attentionPriceService.deleteOne(gameId);
        return Result.success("");
    }
}
