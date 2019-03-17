import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {

    @Test
    public void test() {
        Assert.assertEquals(3, new Solution().solution(3, 7));
    }

    @Test
    public void zero() {
        Assert.assertEquals(0, new Solution().solution(0, 0));
    }

    @Test
    public void max() {
        Assert.assertEquals(9, new Solution().solution(100000000, 100000000));
    }

}
