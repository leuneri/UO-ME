# UO ME

### You Owe Me

Whenever friends go out to travel, we *borrow* money from each other during times of need (such as not bringing enough 
cash). The issue comes up when everybody has to pay each other back - it's hard to keep track of everybody's 
transactions. It is also cumbersome to reverse the transactions and pay each other for each transaction. The solution to this is an application called
**UO ME (You Owe Me)**.

In this application as a user, I want to be able to:
- Keep track of different amounts of money borrowed and lent by different people
- Add new people (borrowers and lenders) to the record of transactions when borrowing or lending money
- Get a list of every person who has a net negative total (lent money) or a net positive total (borrowed money)
- Have only one amount associated with each person even with unlimited transactions
- Clear all records of peoples' owed amount
- save my record of transactions to a file
- load my record of transactions from where I left off from a file

# Instructions for Grader

- You can add lenders and borrowers with the associated amount to the record of transactions by clicking "Add a transaction" and submitting the required inputs
- You can change the balance of existing lenders or borrowers by, once again, adding a transaction but adding the same name in the existing records as the lender or borrower
- You can modify a person's position as lender or borrower based on their balance by clicking "Add a transaction" and also submitting the required inputs but with in mind their balance
- You can remove all transactions from the records by clicking the "Clear Contents" button
- You can locate the visual component by clicking the "Show everyone categorized by lender or borrower by their final amount" after adding some transactions
- You can save the state of my application by clicking "Exit program", then selecting yes for the pop-up window for saving information
- You can reload the state of my application by restarting/starting the program and clicking yes to the pop-up window for loading information


# Phase 4: Task 2

- You can add events by clicking "Add a transaction" and submitting the required inputs
  - Depending on the inputs, there will be different descriptions for events such as a borrower having a negative balance and therefore becoming a lender, or a person lending for the first time
- You can get a full list of the events in the console by clicking "Exit program" after adding some transactions. The list will be printed before the saving option


# Phase 4: Task 3

- The RecordOfTransactions class has quite a bit of methods that are only related in that they operate on the same hashmaps, so I would probably create more classes that use takes from the hashmaps in RecordOfTransactions such that all the GUI buttons operate on a different class
- The GUI has methods such as saving and loading the files, and sorting the balances in ascending order for the histogram. I would take those and make classes out of them instead of having them exist in the GUI