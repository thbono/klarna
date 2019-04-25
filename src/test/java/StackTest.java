import org.junit.Test;

public class StackTest {

    @Test
    public void example1() {
        Stack sol = new Stack();
        sol.push(5);
        sol.push(2);                    // stack: [5,2]
        assert sol.top() == 2;
        sol.pop();                      // stack: [5]
        assert sol.top() == 5;

        Stack sol2 = new Stack();
        assert sol2.top() == 0;         // top of an empty stack is 0
        sol2.pop();                     // pop should do nothing
    }

    @Test
    public void example2() {
        Stack sol = new Stack();
        sol.push(4);

        sol.begin();                    // start transaction 1
        sol.push(7);                    // stack: [4,7]

        sol.begin();                    // start transaction 2
        sol.push(2);                    // stack: [4,7,2]
        assert sol.rollback() == true;  // rollback transaction 2

        assert sol.top() == 7;          // stack: [4,7]

        sol.begin();                    // start transaction 3
        sol.push(10);                   // stack: [4,7,10]
        assert sol.commit() == true;    // transaction 3 is committed
        assert sol.top() == 10;

        assert sol.rollback() == true;  // rollback transaction 1
        assert sol.top() == 4;          // stack: [4]

        assert sol.commit() == false;   // there is no open transaction
    }

    @Test
    public void example3() {
        Stack sol = new Stack();
        sol.push(42);
        assert sol.top() == 42 : "top() should be 42";
    }

}