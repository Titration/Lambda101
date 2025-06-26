package StreamDemo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {
        // 1.创建流 -> 2.中间操作 -> 3.终结操作
        /*
            注意事项：
            - 惰性求值 （如果没有终结操作，中间操作是不会执行的）
            - 流是一次性的（一旦一个流对象经过一个终结操作之后，这个流不能再被使用）
            - 不会影响原数据 （正常情况下不影响原来集合的元素）
        */
        List<Author> authors = getAuthors();
        test01(authors);
        // 1.创建流 - 单列集合 - CollectionObject.stream()
        Stream<Author> stream = authors.stream();
        // 1.创建流 - 数组 - Arrays.stream(), Stream.of()
        test02();
        // 1.创建流 - 双列集合 (转换成单列集合再创建)
        test03();

        // 2.中间操作 - filter, map, distinct,
        test04(); // filter 对流中的元素进行条件过滤，符合过滤条件的才能继续留在流中。
        test05(); // map 可以把流中的元素进行计算或转换。
        test06(); // distinct, 依赖Object.equals()方法来判断是否相同,所以需要注意重写equals方法
        test07(); // sorted, 如果调用空参sorted()方法，需要流中的元素实现Comparable接口; 如果是有参的，则不需要
        test08(); // limit
        test09(); // skip
        test10(); // flatMap 可以把一个对象转换成多个对象作为流中的元素
        test11(); // flatMap

        // 3.终结操作
        test12(); // forEach
        test13(); // count
        test14(); // max, min
        test15(); // collect, to List
        test16(); // collect, to Set
        test17(); // collect, to Map
        test18(); // anyMatch / allMatch / noneMatch
        test19(); // allmatch
        test20(); // noneMatch
        test21(); // findAny / findFirst
        test22(); // findFirst
        test23(); // reduce 归并，对流中的数据以指定的计算方式计算出一个结果
        test24(); // reduce(T identity, BinaryOperator<T> accumulator) 两个参数
        test25(); // reduce(T identity, BinaryOperator<T> accumulator) 两个参数
        test26(); // reduce(BinaryOperator<T> accumulator) 一个参数
    }
    private static void test26() {
        // 使用reduce求所有作者年龄的最小值
        List<Author> authors = getAuthors();
        Optional<Integer> minAge = authors.stream()
                .map(author -> author.getAge())
                .reduce((result, element) -> result > element ? element : result);
        minAge.ifPresent(age -> System.out.println(age));
    }
    private static void test25() {
        // 使用reduce求所有作者年龄的最小值
        List<Author> authors = getAuthors();
        Integer minAge = authors.stream()
                .map(author -> author.getAge())
                .reduce(Integer.MAX_VALUE, (result, element) -> result > element ? element : result);
        System.out.println(minAge);
    }
    private static void test24() {
        // 使用reduce求所有作者年龄的最大值
        List<Author> authors = getAuthors();
        Integer maxAge = authors.stream()
                .map(author -> author.getAge())
                .reduce(Integer.MIN_VALUE, (result, element) -> result < element ? element : result);
        System.out.println(maxAge);
    }
    private static void test23() {
        // 使用reduce求所有作者年龄的和
        List<Author> authors = getAuthors();
        Integer sum = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce(0, (result, element) -> result + element);
        System.out.println(sum);
    }
    private static void test22() {
        // 获取年龄最小作家，并输出作家的名字
        List<Author> authors = getAuthors();
        Optional<Author> minAgeAuthor = authors.stream()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .findFirst();
        minAgeAuthor.ifPresent(author -> System.out.println(author.getName()));
    }
    private static void test21() {
        // 获取任意一个年龄大于60的作家，如果存在就输出作家的名字
        List<Author> authors = getAuthors();
        Optional<Author> optionalAuthor = authors.stream()
                .filter(author -> author.getAge() > 60)
                .findAny();
        optionalAuthor.ifPresent(author -> System.out.println(author.getName()));
    }
    private static void test20() {
        // 判断是否作家都没有超过100岁
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .noneMatch(author -> author.getAge() > 100);
        System.out.println(flag);
    }
    private static void test19() {
        // 判断是否所有的作家都是成年人
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .allMatch(author -> author.getAge() >= 18);
        System.out.println(flag);
    }
    private static void test18() {
        // 判断是否有年龄在60岁以上的作家
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .anyMatch(author -> author.getAge() > 60);
        System.out.println(flag);
    }
    private static void test17() {
        // 获取一个Map集合，map的key为作者名，value为List<Book>
        List<Author> authors = getAuthors();
        Map<String, List<Book>> map = authors.stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));
        System.out.println(map);
    }
    private static void test16() {
        // 获取一个所有书名的Set集合
        List<Author> authors = getAuthors();
        Set<Book> bookSet = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toSet());
        System.out.println(bookSet);
    }
    private static void test15() {
        // 获取一个存放所有作者名字的List集合
        List<Author> authors = getAuthors();
        List<String> nameList = authors.stream()
                .map(author -> author.getName())
                .collect(Collectors.toList());
        System.out.println(nameList);
    }
    private static void test14() {
        // 分别获取这些作家的所出书籍的最高分和最低分并打印
        List<Author> authors = getAuthors();
        Optional<Integer> max = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getScore())
                .max((o1, o2) -> o1 - o2);
        Optional<Integer> min = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getScore())
                .min((o1, o2) -> o1 - o2);

        System.out.println(max.get());
        System.out.println(min.get());
    }
    private static void test13() {
        // 打印这些作家的所出书籍的数目，注意删除重复元素
        List<Author> authors = getAuthors();
        long count = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .count();
        System.out.println(count);
    }
    private static void test12() {
        // 输出所有作家的名字
        List<Author> authors = getAuthors();
        authors.stream()
                .map(author -> author.getName())
                .distinct()
                .forEach(name -> System.out.println(name));
    }
    private static void test11() {
        // 打印现有数据的所有分类。要求对分类进行去重。不能出现这种格式：[],[]
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .flatMap(book -> Arrays.stream(book.getCategory().split(", ")))
                .distinct()
                .forEach(category -> System.out.println(category));
    }
    private static void test10() {
        // 打印所有书籍的名字。要求对重复的元素进行去重
        List<Author> authors = getAuthors();
//        authors.stream()
//                .flatMap(new Function<Author, Stream<Book>>() {
//                    @Override
//                    public Stream<Book> apply(Author author) {
//                        return author.getBooks().stream();
//                    }
//                })
//                .distinct()
//                .forEach(book -> System.out.println(book.getName()));
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }
    private static void test09() {
        // 打印除了年龄最大的作家外的其他作家，要求不能有重复元素，并且按照年龄降序排序
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted()
                .skip(1)
                .forEach(author -> System.out.println(author.getName()));
    }
    private static void test08() {
        // 对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素，然后打印其中年龄最大的两个作家的姓名。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .limit(2)
                .forEach(author -> System.out.println(author.getName()));
    }
    private static void test07() {
        // 对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
//                .sorted() // 空参
                .sorted((o1, o2) -> o2.getAge() - o1.getAge()) // 有参 descending
                .forEach(author -> System.out.println(author.getAge()));

    }
    private static void test06() {
        // 打印所有作家的姓名，并且要求其中不能有重复元素。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .forEach(author -> System.out.println(author.getName()));
    }
    private static void test05() {
        // 打印所有作家的年龄+10岁
        List<Author> authors = getAuthors();
        authors.stream()
                .map(author -> author.getAge())
                .map(age -> age+10)
                .forEach(s-> System.out.println(s));
    }
    private static void test04() {
        // 打印所有姓名长度大于1的作家的姓名
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(author -> author.getName().length() > 1)
                .forEach(author -> System.out.println(author.getName()));
    }
    private static void test03() {
        Map<String, Integer> map = new HashMap<>();
        map.put("鲁迅", 55);
        map.put("老舍", 64);
        map.put("钱钟书", 68);

        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Stream<Map.Entry<String, Integer>> stream = entrySet.stream();
        stream.filter(entry -> entry.getValue() > 60)
                .forEach(entry -> System.out.println(entry.getKey() + "===" + entry.getValue()));
    }
    private static void test02() {
        Integer[] arr = {1,2,3,4,5};
//        Stream<Integer> stream = Arrays.stream(arr);
        Stream<Integer> stream = Stream.of(arr);
        stream.distinct()
                .filter(integer -> integer > 2)
                .forEach(integer -> System.out.println(integer));
    }

    private static void test01(List<Author> authors) {
        // 打印所有年龄小于65的作家的名字，并且要注意去重
        authors.stream() // Convert Collection to Stream. Debug -> Trace Current Stream Chain to review process
                .distinct()
                .filter(author -> author.getAge() < 65)
                .forEach(author -> System.out.println(author.getName()));
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
