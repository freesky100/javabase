package com.java.base.gof.callback;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 * Created by yw on 2018/5/15.
 */
@Data
@NoArgsConstructor
public class EventContext {


    List<Ilistener> list = new ArrayList<>(10);

    public void add(Ilistener ilistener){
        list.add(ilistener);
    }

    public void sendNotify(Event event){
        for (int i = 0; i < list.size(); i++) {
            Ilistener ilistener = list.get(i);
            ilistener.listen(event);

        }
    }


}