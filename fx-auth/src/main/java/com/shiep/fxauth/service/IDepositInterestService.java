package com.shiep.fxauth.service;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author: 倪明辉
 * @date: 2019/5/2 9:36
 * @description: 定时计算存款利息
 */
public interface IDepositInterestService {
    /**
     * description: 计算活期利息(每月的10号)
     *
     * @param
     * @return void
     */
    @Scheduled(cron = "0 0 0 10 * ?")
    void calculateCurrentInterest();
}
