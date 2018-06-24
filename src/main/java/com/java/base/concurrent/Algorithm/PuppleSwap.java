package com.java.base.concurrent.Algorithm;

/**
 * Created by yw on 2018/4/27.
 */
public class PuppleSwap {

    public static void main(String[] args) {
        int[] arrBubble = {1,3,5,2,8,4};
        int[] arrSel ={1,3,5,2,8,4};
        int[] arrIns ={1,3,5,2,8,4};

        int temp =0;
        //冒泡  循环遍历数组每次找出最大的一个
        boolean flag = true;
        for (int i = 0; i < arrBubble.length-1; i++) {
            flag = true;
            for (int j = 0; j < arrBubble.length-1-i; j++) {
                if(arrBubble[j]>arrBubble[j+1]){
                    temp = arrBubble[j];
                    arrBubble[j]=arrBubble[j+1];
                    arrBubble[j+1]=temp;
                    flag =false;
                }
            }
            if(flag){
                System.out.println("有序数组 i="+i);
                break;
            }
            }
        for (int j = 0; j < arrBubble.length; j++) {
            System.out.println("冒泡："+arrBubble[j]);
        }
        //选择排序，保存角标
        int min;

        for (int i = 0; i < arrSel.length-1; i++) {
            min = i;
            for (int j = i; j < arrSel.length-1; j++) {
                if(arrSel[min]>arrSel[j+1]){
                    min = j+1;
                }
            }
            temp = arrSel[i];
            arrSel[i]=arrSel[min];
            arrSel[min] = temp;
        }

        for (int j = 0; j < arrSel.length; j++) {
            System.out.println("选择："+arrSel[j]);
        }


        temp=0;
        //3：插入排序
        int ins = 0;
        for(int i=0;i<arrIns.length;i++){
            ins = arrIns[i];
            for (int j = 0; j < i; j++) {
                if(ins < arrIns[j] ){
                    temp = arrIns[j];
                    arrIns[j]=ins;
                    arrIns[j+1]=temp;
                    break;
                }
            }
        }
        for (int j = 0; j < arrIns.length; j++) {
            System.out.println("插入："+arrIns[j]);
        }

    }

}
