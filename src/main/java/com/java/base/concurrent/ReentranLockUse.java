package com.java.base.concurrent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 细粒度锁
 * Created by yw on 2018/5/2.
 */
public class ReentranLockUse implements Runnable {

    private ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private Player player;
    private AtomicInteger atomicInteger =new AtomicInteger(0);


    public void getCoin(Player player){
        try {
            lock.lock();
            System.out.println(player+"获取唯一一枚硬币，可以玩游戏啦");
            if(!player.getName().equalsIgnoreCase("BOSS")){
                System.out.println("要等老板儿子玩完才能玩，郁闷");
                condition.await();
                System.out.println(player+"玩赛达尔传说中。。。");
            }else{
                System.out.println(player+"老板儿子赶紧挂吧。。。");
                Thread.sleep(2000);
                condition.signalAll();
            }
            Thread.sleep(new Random().nextInt(3000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(player+",Game Over...");
            lock.unlock();
        }
    }
    @Override
    public void run() {
        getCoin(player);
    }

    public static void main(String[] args) {
        ReentranLockUse lockUse = new ReentranLockUse();
        for(int i=0;i<10;i++){
            new Thread(()->{
                lockUse.getCoin(lockUse.new Player("玩家play"+lockUse.atomicInteger.incrementAndGet()));
            }).start();
        }

        new Thread(()->{
            lockUse.getCoin(lockUse.new Player("BOSS"));

        }).start();


    }


    public class Player {
        String name;

        public Player(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
