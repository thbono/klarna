import org.junit.Assert;
import org.junit.Test;

public class FourSeasonsTest {

    @Test
    public void testWith008Days() {
        Assert.assertEquals("SUMMER", new FourSeasons().solution(new int[]{-3,-14,-5,7,8,42,8,3}));
    }

    @Test
    public void testWith012Days() {
        Assert.assertEquals("AUTUMN", new FourSeasons().solution(new int[]{2,-3,3,1,10,8,2,5,13,-5,3,-18}));
    }

    @Test
    public void testWith000Days() {
        Assert.assertEquals("WINTER", new FourSeasons().solution(new int[]{}));
    }

}
