GXR230010 - Project 2

## 4/10/25 

### Thoughts
I read the project requirements and find this to be an interesting problem that lets me visualize it in a real world context. There will be 3 Bank teller threads and 50 customer threads that will all be coordinating with semaphones. 
Safe - 2 max
Manager - 1 max
Door - 2 max

### Plan for today
Today I will initialize the repo and main files like the devlog, bankSim.java and readme
I will try to create the Teller and Customer classes 

## Current status
The tellers and customer all print to the terminal
I need to add the availability for the tellers and the door logic

## 4/11/25

### Thoughts
Today I want to move on to the interactive part. The tellers need to signal availability, 
and customers must acquire a teller then introduce themselves. 

### Plan
- Find a way to hold available tellers
- Implement customer entry, teller selection, and intro logs
- Add method for tellers to serve customers

## Current Status 
Today I implemented the coordination between tellers and customers using a 'BlockingQueue'
to manage teller availability and a semaphore to limit the number of customers entering 
the bank at once. 

The teller threads now continuously place themselves into a shared availableTellers queue 
to indicate they are ready to serve. Customers, upon entering the bank where a semaphore 
will only allow 2 at a time. They will take a teller from this queue and initiate an interation 
by introducing themselves to the assigned teller. I added an idle on the tellers part to have 
the customer interact first.

I have created a server method that currently does not have any transaction logic but the communication 
pipeline between the customer and teller should be functional. 

Since I have the tellers continously looping and listing themselves as avaliable the current output is a 
infinite string of 'waiting for a customer". Tommorrow I intend to add the safe and manager features as 
well as a sync between the customers and tellers


## 4/12/25 

### Thoughts 
Today I hope to finish all the main requirements of the project. I need to setup transaction simulation, safe access, and the customer exit. I need to pay attention to the sample run as a baseline

### Plan
- Add `Semaphore safe = new Semaphore(2)`
- Add `Semaphore manager = new Semaphore(1)`
- Withdraw goes to manager, all tellers go to safe
- Customers leave the door and log exit
- Main thread joins customer threads and prints bank when the bank is closed

### Status
I am implementing full teller-customer interactions, as well as handling customer exits and teller resets. I added sleep delays to simulate the passage of time.

The main() function now blocks on all customer threads using join(). This ensures the simulation doesn’t end prematurely. Once all customer threads finish, the simulation logs “The bank closes for the day,” and the tellers are all interrupted and out that they are leaving for the day.

I see the full project is now functional and all the outputs closely resembles the sample output

## 4/13/25

Project checked and everything seems to be working well, I created the readme file and everything is ready to be turned in.