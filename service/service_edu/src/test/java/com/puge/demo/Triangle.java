package com.puge.demo;

import java.util.Arrays;
import java.util.Scanner;

public class Triangle {
    int a,b,c;//三角形三边
    int arr []=new int [3];
    public void infor() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入三角形的三条边");
        for(int i=0;i<arr.length;i++){
            System.out.println("请输入三角形的第"+(i+1)+"条边");
            arr[i]=input.nextInt();
        }
        Arrays.sort(arr);
        a=arr[2];
        b=arr[1];
        c=arr[0];
        if(a + b > c && a + c > b && b + c > a)         //三角形
            if (a * a + b * b == c * c || a * a + c * c == b * b || b * b + c * c == a * a) {
                if (a == b || a == c || b == c) {
                    System.out.println("能构成等腰直角三角形");
                } else {
                    System.out.println("能构成一般直角三角形");
                }
            } else if (a == b && b == c && a == c) {
                System.out.println("能构成等边三角形");
            } else if ((a == b && a != c) || (a == c && a != b) || (b == c && a != c)) {
                System.out.println("能构成等腰三角形");
            } else {
                System.out.println("能构成一般三角形");
            }
        else
            System.out.println("不构成三角形");
    }
    public void triangletype()
    {

    }
}