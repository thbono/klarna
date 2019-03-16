import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SmoothieTest {

    @Test
    public void classicSmoothie() {
        assertEquals("banana,honey,mango,peach,pineapple,strawberry",
                Smoothie.ingredients("Classic"));
    }

    @Test
    public void classicSmoothieWithoutStrawberry() {
        assertEquals("banana,honey,mango,peach,pineapple",
                Smoothie.ingredients("Classic,-strawberry"));
    }

    @Test
    public void classicSmoothieWithoutALot() {
        assertEquals("banana,honey,mango,peach,pineapple",
                Smoothie.ingredients("Classic,-strawberry,-peanut,-cocoa"));
    }

    @Test
    public void example1() {
        assertEquals("banana,honey,mango,peach,pineapple",
                Smoothie.ingredients("Classic,-strawberry,-peanut"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void example2() {
        Smoothie.ingredients("Classic,chocolate");
    }

    @Test(expected = IllegalArgumentException.class)
    public void example3() {
        Smoothie.ingredients("Vitamin smoothie");
    }

    @Test
    public void test1() {
        assertEquals("banana,honey,mango,peach,pineapple",
                Smoothie.ingredients("Classic,-strawberry,"));
    }

}