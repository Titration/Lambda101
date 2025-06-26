package MethodReferenceDemo;

import StreamDemo.Author;
import StreamDemo.Book;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class MethodReferenceDemo {
    /*
        基本格式：
        类名或者对象名::方法名

        引用（Class）类的静态方法
        格式：类名::方法名
        使用前提：
        方法体中只有一行代码，并且这行代码调用了某个类的静态方法，并且重写的抽象方法中所有的参数都传入了静态方法中（按照顺序传入）

        引用（Object）对象的实例方法
        格式：对象名::方法名
        使用前提：
        方法体中只有一行代码，并且这行代码调用了对象的某个成员方法，并且重写的抽象方法中所有的参数都传入了成员方法中（按照顺序传入）

        引用（Class）类的实例方法
        格式：类名::方法名
        使用前提：
        方法体中只有一行代码，并且这行代码调用了第一个参数的成员方法，并且重写的抽象方法中所有的参数都传入了成员方法中（按照顺序传入）

        构造器引用
        格式：类名::new
        方法体中只有一行代码，并且这行代码调用了某个类的构造方法，并且重写的抽象方法中所有的参数都传入了构造方法中（按照顺序）
     */
    public static void main(String[] args) {
        List<Author> authors = getAuthors();

        // 引用（Class）类的静态方法
        authors.stream()
                .map(author -> author.getAge())
                .map(String::valueOf); // .map(age -> String.valueOf(age));

        // 引用（Object）对象的实例方法
        StringBuilder sb = new StringBuilder();
        authors.stream()
                .map(author -> author.getName())
                .forEach(sb::append); // .forEach(name -> sb.append(name));

        // 引用（Class）类的实例方法
        subAuthorName("test", String::substring);
//        subAuthorName("test", new UseString() {
//            @Override
//            public String use(String str, int start, int length) {
//                return str.substring(start, length);
//            }
//        });

        // 构造器引用
        authors.stream()
                .map(author -> author.getName()) // 可以转换
                .map(StringBuilder::new)
                .map(strBuilder -> strBuilder.append("Constructor Reference").toString()) // 有toString(), 所以不是一行代码，不能转换
                .forEach(str -> System.out.println(str)); // 对象是System.out 方法是println
    }
    interface UseString {
        String use(String str, int start, int length);
    }
    public static String subAuthorName(String str, UseString useString) {
        int start = 0;
        int length = 1;
        return useString.use(str, start, length);
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
