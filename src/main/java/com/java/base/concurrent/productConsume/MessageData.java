package com.java.base.concurrent.productConsume;

/**
 * Created by yw on 2018/4/26.
 */
public class MessageData {

    private int id;
    private String name;

    public MessageData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public MessageData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
