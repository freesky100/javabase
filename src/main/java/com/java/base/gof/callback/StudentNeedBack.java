package com.java.base.gof.callback;

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
public class StudentNeedBack implements ICallback2 {

    private Teacher teacher;

    public StudentNeedBack(Teacher teacher) {
        this.teacher = teacher;
    }

    public void ask(String question,Teacher teacher){
        System.out.println("学生向老师："+teacher.getName()+"问问题，"+question);
        teacher.response(this);
    }

    @Override
    public String call(String res) {
        System.out.println("学生收到回复结果："+res);
        return res;
    }


    public static void main(String[] args) {

        Teacher teacher = new Teacher("大张");
        StudentNeedBack student = new StudentNeedBack(teacher);
        student.ask("1+1=？",teacher);



    }
}
