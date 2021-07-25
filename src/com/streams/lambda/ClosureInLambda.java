package com.streams.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClosureInLambda {

    static int global = 0;
    public static void main(String[] args) {

        testLambdaCapturingVariable();

    }

    private static void testLambdaCapturingVariable() {

        int count = 0;
// closure is same as capturing variables inside lambda
// now we want to use this variable inside lambda expression
// but to use this variable there are rules we have to follow


// Rule 1 : if its a local variable(in our case count is local variable) then the variable has to be effectively final
// that is you cannot increase the variable :: System.out.println(count++)
        new Thread(()-> System.out.println(count));

// Rule 2 : you cannot also increase the variable anywhere else if its used inside lambda expression
       // count++;

// Rule 3 : there is a trick in which you can increase the variable
        List<Integer> trick = new ArrayList<>();
        trick.add(count);

        new Thread(()->{
            int tempCount = trick.get(0);
            tempCount++;
            trick.set(0, tempCount);
        }).start();
// this did not set count to 1 ... don't know why ??
        System.out.println("After trick count value is " + count);

// Rule 4 : but you can access and increase GLOBAL variables
        new Thread(() -> System.out.println(global++));

//Rule 5 : local variables cannot be shadowed but global variables can be
        for (int i = 0; i < 2; i++) {
            //int count = 7;
            int global = 78;
        }
// it also cannot be used as lambda parameter or redeclare inside lambda body
       // Consumer<Integer> consumer = (count)-> System.out.println(count);
       // Consumer<Integer> consumer = (count2)-> {int count = 4;};

// Rule 6 : but inside anonymous class implementation redeclare is ok but you cannot ONLY increase it like lambda
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int count = 5;
                        count++;
                    }
                }
        ).start();






    }
}
