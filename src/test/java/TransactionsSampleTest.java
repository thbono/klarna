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

    @Test
    public void test5() {
        List<String> transactions = Arrays.asList("John,Doe,john@doe.com,199,TR0001", "John,Doe,john@doe.com,2,TR0002", "John,Doe,john@doe.com,1,TR0003");

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

        assertThat(rejectedTransactions, is(Arrays.asList("TR0002")));
    }

    @Test
    public void test9() {
        List<String> transactions = Arrays.asList(
                "John,Doe,jd@hotmail.com,30,TR000",
                "Jane,Dou,jd@example.com,2,TR001",
                "Jake,Doe,jd@email.com,10,TR002",
                "Jane,Doa,jd@hotmail.com,31,TR003",
                "Jake,Doe,jd@email.com,15,TR004",
                "Jane,Doe,jd@example.com,8,TR005",
                "John,Doe,jd@hotmail.com,13,TR006",
                "Jake,Doe,jd@hotmail.com,26,TR007",
                "John,Doa,jd@email.com,1,TR008",
                "John,Doe,jd@hotmail.com,49,TR009",
                "John,Doa,jd@hotmail.com,45,TR0010",
                "Jane,Doe,jd@hotmail.com,7,TR0011",
                "John,Dou,jd@example.com,42,TR0012",
                "Jane,Dou,jd@example.com,46,TR0013",
                "John,Dou,jd@email.com,46,TR0014",
                "Jane,Dou,jd@example.com,5,TR0015",
                "John,Dou,jd@email.com,30,TR0016",
                "John,Doa,jd@hotmail.com,9,TR0017",
                "Jake,Dou,jd@hotmail.com,44,TR0018",
                "Jake,Doa,jd@email.com,44,TR0019",
                "Jake,Dou,jd@email.com,13,TR0020",
                "Jane,Doa,jd@hotmail.com,13,TR0021",
                "Jane,Doa,jd@example.com,20,TR0022",
                "Jane,Doa,jd@hotmail.com,20,TR0023",
                "Jake,Dou,jd@example.com,30,TR0024",
                "Jane,Doa,jd@example.com,13,TR0025",
                "John,Dou,jd@example.com,42,TR0026",
                "John,Doe,jd@hotmail.com,40,TR0027",
                "Jane,Dou,jd@email.com,32,TR0028",
                "Jake,Doa,jd@example.com,39,TR0029",
                "John,Doe,jd@example.com,8,TR0030",
                "Jane,Dou,jd@hotmail.com,42,TR0031",
                "Jake,Dou,jd@example.com,21,TR0032",
                "Jane,Doa,jd@email.com,29,TR0033",
                "John,Doe,jd@example.com,13,TR0034",
                "Jane,Doa,jd@hotmail.com,35,TR0035",
                "Jane,Dou,jd@hotmail.com,32,TR0036",
                "Jake,Doa,jd@example.com,40,TR0037",
                "Jane,Doa,jd@example.com,21,TR0038",
                "Jane,Doe,jd@hotmail.com,6,TR0039",
                "John,Doe,jd@email.com,24,TR0040",
                "John,Doa,jd@example.com,3,TR0041",
                "Jake,Doe,jd@example.com,10,TR0042",
                "John,Doa,jd@example.com,49,TR0043",
                "Jake,Doa,jd@email.com,14,TR0044",
                "Jake,Doa,jd@email.com,46,TR0045",
                "Jane,Doe,jd@hotmail.com,27,TR0046",
                "Jake,Dou,jd@hotmail.com,25,TR0047",
                "Jane,Dou,jd@example.com,24,TR0048",
                "Jake,Doa,jd@email.com,18,TR0049"
        );

        List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 80);

        assertThat(rejectedTransactions, is(Arrays.asList("TR009", "TR0026", "TR0027", "TR0035", "TR0045")));
    }

}