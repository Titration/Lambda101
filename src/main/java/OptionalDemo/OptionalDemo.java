package OptionalDemo;

import StreamDemo.Author;
import StreamDemo.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        // Optional.ofNullable() + ifPresent()
        Author author = getAuthor();
        Optional<Author> authorOptional = Optional.ofNullable(author);
        authorOptional.ifPresent(author1 -> System.out.println(author1.getName()));
        Optional<Author> authorOptional_1 = getAuthorOptional();
        authorOptional_1.ifPresent(author1 -> System.out.println(author1.getName()));

        // Optional.get() 不安全不建议使用, if null，throw NoSuchElementException
        Optional<Author> nullAuthor = null;
//        nullAuthor.get(); // throw NoSuchElementException
        // Optional.orElseGet()
        Optional<Author> authorOptional_2 = getAuthorOptional();
        Author authorOrElseGet = authorOptional_2.orElseGet(() -> new Author(1L, "鲁迅", 55, "A pioneer who wielded his pen like a scalpel to dissect society’s soul", null));
        System.out.println(authorOrElseGet.getName());
        // Optional.orElseThrow()
        Optional<Author> authorOptional_3 = getAuthorOptional();
        try {
            Author dataIsNull = authorOptional_3.orElseThrow(() -> new RuntimeException("data is null"));
            System.out.println(dataIsNull);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // Optional.filter()
        testFilter();
        // Optional.isPresent()
        testIsPresent();
        // Optional.map()
        testMap();
    }

    private static void testMap() {
        Optional<Author> authorOptional = getAuthorOptional();
        authorOptional.map(author -> author.getBooks())
                .ifPresent(books -> System.out.println(books));
    }

    private static void testIsPresent() {
        Optional<Author> authorOptional = getAuthorOptional();
        if (authorOptional.isPresent()) {
            System.out.println(authorOptional.get().getAge());
        }
    }

    private static void testFilter() {
        Optional<Author> authorOptional = getAuthorOptional();
        authorOptional.filter(author -> author.getAge() > 50).ifPresent(author -> System.out.println(author.getName()));
    }

    public static Optional<Author> getAuthorOptional() {
        Author author = new Author(1L, "鲁迅", 55, "A pioneer who wielded his pen like a scalpel to dissect society’s soul", null);
        List<Book> books1 = new ArrayList<>();
        books1.add(new Book(1L, "呐喊 (Call to Arms)", "Realism, Critique", 100, "Fierce critiques of feudalism and numbness in Chinese society"));
        author.setBooks(books1);
        // option 1
//        return author == null ? Optional.empty() : Optional.of(author); // we need to return a Optional Object
        // option 2
        return Optional.ofNullable(author);
    }
    public static Author getAuthor() {
        Author author = new Author(1L, "鲁迅", 55, "A pioneer who wielded his pen like a scalpel to dissect society’s soul", null);
        return author;
    }
}
