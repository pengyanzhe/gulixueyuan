package com.puge.demo;

import java.util.Scanner;

public class test1 {
    public static void main(String[] args) {
        double a, b, c;
        Scanner sc = new Scanner(System.in);
        a = sc.nextDouble();
        b = sc.nextDouble();
        c = sc.nextDouble();

        if (a > 0 && b > 0 && c > 0) {
            if (a + b > c && a + c > b && b + c > a) {
                System.out.println("能构成三角形");
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
            } else {
                System.out.println("不能构成三角形");
            }
        } else {
            System.out.println("能构成三角形");
        }
    }
}
