import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Smoothie {

    private static final Pattern pattern = Pattern.compile("^([\\w\\s]+)((\\,\\-[\\w\\s]+)*(\\,)?)?$");
    private static final Map<String, List<String>> menu = new HashMap<>();

    static {
        menu.put("Classic", Arrays.asList("strawberry", "banana", "pineapple", "mango", "peach", "honey"));
        menu.put("Freezie", Arrays.asList("blackberry", "blueberry", "black currant", "grape juice", "frozen yogurt"));
        menu.put("Greenie", Arrays.asList("green apple", "lime", "avocado", "spinach", "ice", "apple juice"));
        menu.put("Just Desserts", Arrays.asList("banana", "ice cream", "chocolate", "peanut", "cherry"));
    }

    public static String ingredients(String order) {
        Optional.ofNullable(order).orElseThrow(() -> new IllegalArgumentException("Order cannot be null"));

        final Matcher matcher = pattern.matcher(order);
        if (!matcher.find()) throw new IllegalArgumentException("Invalid order: " + order);

        final String menuItem = matcher.group(1);
        final List<String> ingredients = Optional.ofNullable(menu.get(menuItem))
                .orElseThrow(() -> new IllegalArgumentException("There is no such item: " + menuItem));

        final List<String> toRemove = Arrays.asList(matcher.group(2).replaceAll(",", "").split("-"));

        return ingredients.stream()
                .filter(not(toRemove::contains))
                .sorted()
                .collect(Collectors.joining(","));
    }

    // Java 11 has Predicate::not
    private static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }

}