package LambdaDemo;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class LambdaDemo01 {
    public static void main(String[] args) {
//         Example 1:
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello!");
//            }
//        }).start();
//        new Thread(() -> System.out.println("Hello, Lambda!")).start();

//         Example 2:
//        int i = calculateNum(new IntBinaryOperator() {
//            @Override
//            public int applyAsInt(int left, int right) {
//                return left + right;
//            }
//        });
//        int i_lambda = calculateNum((left, right) -> left + right);
//        System.out.println(i);
//        System.out.println(i_lambda);

//         Example 3:
//        printNum(new IntPredicate() {
//            @Override
//            public boolean test(int value) {
//                return value%2==0;
//            }
//        });
//        printNum(value -> value%2==0);

//         Example 4:
//        Integer num = typeConver(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return Integer.valueOf(s);
//            }
//        });
//        Integer num_lambda = typeConver(s -> Integer.valueOf(s));
//        System.out.println(num);
//        System.out.println(num_lambda);

//         Example 5:
//        String result = typeConver(new Function<String, String>() {
//            @Override
//            public String apply(String s) {
//                return s + " is good";
//            }
//        });
//        String result_lambda = typeConver(s -> s + " is lambda");
//        System.out.println(result);
//        System.out.println(result_lambda);

//         Example 6:
//        foreachArr(new IntConsumer() {
//            @Override
//            public void accept(int value) {
//                System.out.printf("%d\s", value);
//            }
//        });
//        foreachArr(value -> System.out.printf("%d\s", value));
    }

    public static int calculateNum(IntBinaryOperator operator) {
        int a = 10;
        int b = 20;
        return operator.applyAsInt(a, b);
    }
    public static void printNum(IntPredicate predicate) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            if (predicate.test(i)) {
                System.out.printf("%d\s", i);
            }
        }
        System.out.println();
    }
    public static <R> R typeConver(Function<String, R> function) {
        String str = "1235";
        R result = function.apply(str);
        return result;
    }
    public static void foreachArr(IntConsumer consumer) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            consumer.accept(i);
        }
    }
}
