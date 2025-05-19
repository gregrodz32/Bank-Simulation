import java.util.concurrent.*;
import java.util.*;

public class bankSim {
    static final int NUM_CUSTOMERS = 50;
    static final int NUM_TELLERS = 3;

    static Semaphore door = new Semaphore(2);            // 2 customers at max enter through the door
    static Semaphore safe = new Semaphore(2);            // 2 tellers max in the safe
    static Semaphore manager = new Semaphore(1);         // 1 teller at a time with manager
    static BlockingQueue<Teller> availableTellers = new LinkedBlockingQueue<>();

    enum Transaction {
        DEPOSIT, WITHDRAWAL
    }

    static class Customer extends Thread {
        int id;
        Transaction transaction;
        Teller assignedTeller;

        Customer(int id) {
            this.id = id;
            this.transaction = new Random().nextBoolean() ? Transaction.DEPOSIT : Transaction.WITHDRAWAL;
        }

        public void run() {
            try {
                // Random wait before going to bank
                Thread.sleep(new Random().nextInt(100));
                System.out.printf("Customer %d []: wants to perform a %s transaction%n", id, transaction.name().toLowerCase());

                System.out.printf("Customer %d []: going to bank.%n", id);
                door.acquire();
                System.out.printf("Customer %d []: entering bank.%n", id);
                System.out.printf("Customer %d []: getting in line.%n", id);

                // Assign a teller
                assignedTeller = availableTellers.take();
                System.out.printf("Customer %d []: selecting a teller.%n", id);
                System.out.printf("Customer %d [Teller %d]: selects teller%n", id, assignedTeller.id);
                System.out.printf("Customer %d [Teller %d] introduces itself%n", id, assignedTeller.id);

                assignedTeller.serve(this);

                System.out.printf("Customer %d [Teller %d]: leaves teller%n", id, assignedTeller.id);
                System.out.printf("Customer %d []: goes to door%n", id);
                System.out.printf("Customer %d []: leaves the bank%n", id);

                door.release(); // Leaving bank
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Teller extends Thread {
        int id;

        Teller(int id) {
            this.id = id;
        }

        public void run() {
            System.out.printf("Teller %d []: ready to serve%n", id);
            try {
                while (true) {
                    availableTellers.put(this);
                    System.out.printf("Teller %d []: waiting for a customer%n", id);
                    Thread.sleep(10); // simulate an idle wait
                }
            } catch (InterruptedException e) {
                System.out.printf("Teller %d []: leaving for the day%n", id);
            }
        }

        public void serve(Customer c) {
            System.out.printf("Teller %d [Customer %d]: serving a customer%n", id, c.id);
            System.out.printf("Teller %d [Customer %d]: asks for transaction%n", id, c.id);
            System.out.printf("Customer %d [Teller %d]: asks for %s transaction%n", c.id, id, c.transaction.name().toLowerCase());

            if (c.transaction == Transaction.WITHDRAWAL) {
                System.out.printf("Teller %d [Customer %d]: handling withdrawal transaction%n", id, c.id);
                try {
                    System.out.printf("Teller %d [Customer %d]: going to the manager%n", id, c.id);
                    manager.acquire();
                    System.out.printf("Teller %d [Customer %d]: getting manager's permission%n", id, c.id);
                    Thread.sleep(new Random().nextInt(26) + 5);
                    System.out.printf("Teller %d [Customer %d]: got manager's permission%n", id, c.id);
                    manager.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.printf("Teller %d [Customer %d]: handling deposit transaction%n", id, c.id);
            }

            try {
                System.out.printf("Teller %d [Customer %d]: going to safe%n", id, c.id);
                safe.acquire();
                System.out.printf("Teller %d [Customer %d]: enter safe%n", id, c.id);
                Thread.sleep(new Random().nextInt(41) + 10); 
                System.out.printf("Teller %d [Customer %d]: leaving safe%n", id, c.id);
                System.out.printf("Teller %d [Customer %d]: finishes %s transaction.%n", id, c.id, c.transaction.name().toLowerCase());
                safe.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Teller %d [Customer %d]: wait for customer to leave.%n", id, c.id);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Teller[] tellers = new Teller[NUM_TELLERS];
        for (int i = 0; i < NUM_TELLERS; i++) {
            tellers[i] = new Teller(i);
            tellers[i].start();
        }

        Customer[] customers = new Customer[NUM_CUSTOMERS];
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            customers[i] = new Customer(i);
            customers[i].start();
        }

        // Wait for all customers to finish
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            customers[i].join();
        }

        
        for (int i = 0; i < NUM_TELLERS; i++) {
            tellers[i].interrupt();
        }

        System.out.println("The bank closes for the day.");
    }
}
