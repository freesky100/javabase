package com.java.base.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yw on 2018/5/2.
 */
public class SemaphoreLock implements Runnable{


    private Semaphore semaphore = new Semaphore(2);
    private List<Car> list = new ArrayList<>(2);
    private ReentrantLock lock = new ReentrantLock(false);
    private User user;

    public SemaphoreLock() {
        list.add(new Car("奔驰拖拉机",1));
        list.add(new Car("宝马收割机",2));
    }

    public void getCar(User user){

            try {
                semaphore.acquire();
                System.out.println(user.getName()+"想获取车辆");
                Car car = getResource();
                System.out.println(user.getName()+"得到车辆："+car+"吃土中....");
                Thread.sleep(new Random().nextInt(5000));
                car.setUsed(false);
                System.out.println(user.getName()+"已用完车辆，归还"+car);
            } catch (InterruptedException e) {
                e.printStackTrace();
           }finally {
                semaphore.release();
            }
    }

    private Car getResource() {
        Car car =null;

        try {
            lock.lock();
            for(int i =0;i<list.size();i++){
                if(list.get(i).isUsed()==false){
                   car = list.get(i);
                   car.setUsed(true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return car;
    }

    public static void main(String[] args) {
        SemaphoreLock lock = new SemaphoreLock();
        User user;
        new Thread(()->{
            lock.getCar((new User("张三")));
        }).start();
        new Thread(()->{
            lock.getCar((new User("王五")));
        }).start();
        new Thread(()->{
            lock.getCar((new User("赵六")));
        }).start();
        new Thread(()->{
            lock.getCar((new User("李四")));
        }).start();
        new Thread(()->{
            lock.getCar((new User("秦爸")));
        }).start();
    }

    @Override
    public void run() {
        getCar(user);
    }
}

class User{
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


class Car {
    private String name;
    private int id;
    private boolean isUsed =false;

    public Car(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
}