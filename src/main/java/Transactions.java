import java.util.*;

public class Transactions {

    public static List<String> findRejectedTransactions(List<String> transactions, int creditLimit) {
        // public method, so checking input
        Optional.ofNullable(transactions).orElseThrow(() -> new IllegalArgumentException("Transactions list cannot be null"));

        final Map<String, Integer> sumByConsumer = new HashMap<>();
        final List<String> rejectedTransactions = new ArrayList<>();

        try {
            transactions.stream().forEachOrdered(t -> {
                // to be immutable, lambda expressions cannot assign to outside variables
                final String[] transaction = t.split(",");
                final String consumer = String.format("%s%s%s", transaction[0], transaction[1], transaction[2]);
                int value = Integer.parseInt(transaction[3]);

                //  not using dispensable blocks to reduce stack size and increase performance
                if (sumByConsumer.containsKey(consumer)) value += sumByConsumer.get(consumer);

                if (value > creditLimit) rejectedTransactions.add(transaction[4]);
                else sumByConsumer.put(consumer, value); // only sum if transaction proceeded
            });
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            // more specific cause
            throw new IllegalArgumentException("Invalid transaction format", e);
        }

        return rejectedTransactions;
    }

}