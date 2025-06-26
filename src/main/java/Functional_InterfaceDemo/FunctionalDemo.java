package Functional_InterfaceDemo;

import StreamDemo.Author;
import StreamDemo.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class FunctionalDemo {
    public static void main(String[] args) {
        // .and()
//        testAnd();
//        printNum(value -> value % 2 == 0, value -> value > 4);
        // .or()
//        testOr();
         testNegate();

    }

    private static void testNegate() {
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getAge() > 60;
                    }
                }.negate())
                .forEach(author -> System.out.println(author.getAge()));
    }

    private static void testOr() {
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getAge() > 60;
                    }
                }.or(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getName().length() < 2;
                    }
                })).forEach(author -> System.out.println(author.getName()));
    }

    private static void printNum(IntPredicate predicate1, IntPredicate predicate2) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            if (predicate1.and(predicate2).test(i)) {
                System.out.printf("%d\s", i);
            }
        }
        System.out.println();
    }
    private static void testAnd() {
        // 打印作家年龄大于50 并且 姓名长度大于1的作家
        // 其实并不好用，一般不再filter里面用，而是自定义function的时候用
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getAge() > 50;
                    }
                }.and(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getName().length() > 1;
                    }
                })).forEach(author -> System.out.println(author.getAge() + ":::" + author.getName()));
    }
    private static List<Author> getAuthors() {
        // Data Initialization
        Author author1 = new Author(1L, "鲁迅", 55, "A pioneer who wielded his pen like a scalpel to dissect society’s soul", null);
        Author author2 = new Author(2L, "老舍", 64, "A voice for the streets, bringing Beijing's commoners to literary life", null);
        Author author3 = new Author(3L, "钱钟书", 68, "A scholar of wit and wisdom, bridging Chinese tradition and Western thought", null);
        Author author4 = new Author(3L, "钱钟书", 68, "A scholar of wit and wisdom, bridging Chinese tradition and Western thought", null);

        // Book lists
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        // Books by Lu Xun
        books1.add(new Book(1L, "呐喊 (Call to Arms)", "Realism, Critique", 100, "Fierce critiques of feudalism and numbness in Chinese society"));
        books1.add(new Book(2L, "阿Q正传 (The True Story of Ah Q)", "Satire, Psychology", 1, "A tragic mirror of the national character through humor and irony"));

        // Books by Lao She
        books2.add(new Book(3L, "骆驼祥子 (Rickshaw Boy)", "Realism, Urban Life", 92, "The fall of an idealistic man under the wheels of a corrupt system"));
        books2.add(new Book(4L, "茶馆 (Teahouse)", "Drama, History", 89, "A fading teahouse witnessing a crumbling empire and moral disarray"));
        books2.add(new Book(4L, "茶馆 (Teahouse)", "Drama, History", 89, "A fading teahouse witnessing a crumbling empire and moral disarray"));
        books2.add(new Book(5L, "四世同堂 (Four Generations Under One Roof)", "War, Family", 94, "Family bonds tested in wartime Beijing"));

        // Books by Qian Zhongshu
        books3.add(new Book(8L, "写在人生边上 (Written at the Edge of Life)", "Essays, Philosophy", 90, "A witty collection of essays blending humor with deep reflection on life, literature, and human nature"));
        books3.add(new Book(6L, "围城 (Fortress Besieged)", "Satire, Marriage", 97, "Love, academia, and society’s absurdities in Republican China"));
        books3.add(new Book(6L, "围城 (Fortress Besieged)", "Satire, Marriage", 97, "Love, academia, and society’s absurdities in Republican China"));

        author1.setBooks(books1);     // Lu Xun
        author2.setBooks(books2);     // Lao She
        author3.setBooks(books3);     // Qian Zhongshu
        author4.setBooks(books3);     // Qian Zhongshu

        List<Author> authorList = new ArrayList<>(Arrays.asList(author1, author2, author3, author4));
        return authorList;
    }
}
