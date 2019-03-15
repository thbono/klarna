import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransactionsSampleTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfThereIsNoList() {
        Transactions.findRejectedTransactions(Arrays.asList("John,Doe,john@doe.com,200f,TR0001"), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfThereIsInvalidTransaction() {
        Transactions.findRejectedTransactions(null, 0);
    }

    @Test
    public void shouldReturnEmptyListIfThereIsNoTransactions() {
        assertThat(Transactions.findRejectedTransactions(new ArrayList<>(), 0).size(), is(0));
    }

    @Test
    public void shouldReturnEmptyListIfThereIsATransactionWithinCreditLimit() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,200,TR0001");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

        assertThat(rejectedTransactions.size(), is(0));
    }

    @Test
    public void shouldReturnEmptyListIfThereIsATransactionWithinCreditLimitWithTwoTransactions() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,100,TR0001", "John,Doe,john@doe.com,100,TR0002");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

        assertThat(rejectedTransactions.size(), is(0));
    }

    @Test
    public void shouldReturnEmptyListIfThereIsATransactionWithinCreditLimitWithTwoConsumers() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,100,TR0001", "John,Doe,john@doe.com,100,TR0002", "Mary,Doe,mary@doe.com,100,TR0003");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

        assertThat(rejectedTransactions.size(), is(0));
    }

    @Test
    public void shouldReturnTransationThatIsOverCreditLimit() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,201,TR0001");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

        assertThat(rejectedTransactions, is(Arrays.asList("TR0001")));
    }

    @Test
    public void shouldReturnTransationThatIsOverZeroCreditLimit() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,201,TR0001");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 0);

        assertThat(rejectedTransactions, is(Arrays.asList("TR0001")));
    }

    @Test
    public void shouldReturnTransationThatIsOverNegativeCreditLimit() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,201,TR0001");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, -1);

        assertThat(rejectedTransactions, is(Arrays.asList("TR0001")));
    }

    @Test
    public void shouldReturnTransationThatIsOverCreditLimitWithTwoTransactions() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,101,TR0001", "John,Doe,john@doe.com,101,TR0002");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

        assertThat(rejectedTransactions, is(Arrays.asList("TR0002")));
    }

    @Test
    public void shouldReturnTransationThatIsOverCreditLimitWithTwoCustomers() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,101,TR0001", "John,Doe,john@doe.com,101,TR0002", "Mary,Doe,mary@doe.com,100,TR0003");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

        assertThat(rejectedTransactions, is(Arrays.asList("TR0002")));
    }

}