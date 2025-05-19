# CS4348 Project 2 — GXR2300010 

This java file simulates a bank environment using threads and semaphores to model real world synchronization constraints. The simulation includes 3 tellers and 50 customers, all interacting concurrently with rules for entering the bank, accessing the manager, and accessing the safe.

## Features

- 3 teller threads
- 50 customer threads
- synchronization using Java semaphores
- bank door only allows 2 customers in at a time
- maximum of 2 tellers in the safe at one time
- only 1 teller can speak with the manager at a time
- the customers are randomized to either deposit or withdraw
- bank closure when all customer threads are done

## How to run

javac bankSim.java


## Sample output

Teller 0 []: ready to serve
Customer 0 []: wants to perform a deposit transaction
Customer 0 []: entering bank.
Customer 0 []: getting in line.
Customer 0 []: selecting a teller.
Customer 0 [Teller 2]: selects teller
Customer 0 [Teller 2] introduces itself
Teller 2 [Customer 0]: serving a customer
Teller 2 [Customer 0]: asks for transaction
Customer 0 [Teller 2]: asks for deposit transaction
Teller 2 [Customer 0]: handling deposit transaction
Teller 2 [Customer 0]: going to safe