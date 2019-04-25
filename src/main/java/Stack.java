import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Stack {

    // Deque does not need to be concurrent because using stack as semaphore for all operations
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Deque<Deque<Integer>> transactionStack = new ArrayDeque<>();

    public void push(int value) {
        synchronized (stack) {
            // Get the most recent transaction, or the root one
            final Deque<Integer> stackToAdd = Optional.ofNullable(transactionStack.peekFirst()).orElse(stack);
            stackToAdd.addFirst(value);
        }
    }

    public int top() {
        synchronized (stack) {
            final Deque<Integer> stackToPeek = Optional.ofNullable(transactionStack.peekFirst()).orElse(stack);
            return Optional.ofNullable(stackToPeek.peekFirst()).orElse(0);
        }
    }

    public void pop() {
        synchronized (stack) {
            try {
                final Deque<Integer> stackToRemove = Optional.ofNullable(transactionStack.peekFirst()).orElse(stack);
                stackToRemove.removeFirst();
            } catch (NoSuchElementException ex) {
                // Not a problem
            }
        }
    }

    public void begin() {
        synchronized (stack) {
            transactionStack.addFirst(new ArrayDeque<>());
        }
    }

    public boolean rollback() {
        synchronized (stack) {
            try {
                transactionStack.removeFirst();
                return true;
            } catch (NoSuchElementException ex) {
                return false;
            }
        }
    }

    public boolean commit() {
        synchronized (stack) {
            try {
                final Deque<Integer> stackToMerge = transactionStack.removeFirst();
                // Merge elements into the previous stack, or into the root one
                final Deque<Integer> stackToAdd = Optional.ofNullable(transactionStack.peekFirst()).orElse(stack);

                // No opening a new block to avoid unnecessary new thread stack context
                while (!stackToMerge.isEmpty()) stackToAdd.addFirst(stackToMerge.removeLast());

                return true;
            } catch (NoSuchElementException ex) {
                return false;
            }
        }
    }

}
