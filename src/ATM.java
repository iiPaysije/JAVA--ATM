import java.sql.SQLOutput;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {


        //init Scanner
        Scanner sc = new Scanner(System.in);

        //init bank
        Bank theBank = new Bank("Bank of Mine");

        //add a user to the bank and auto create savings account
        User aUser = theBank.addUser("John", "Doe", "1234");

        //add a checking account for user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            //stay in the login prompt until successful
            curUser = ATM.mainMenuPrompt(theBank, sc);

            //stay in main menu until  user quits
            ATM.printUserMenu(curUser, sc);
        }

    }

    public static void printUserMenu(User curUser, Scanner sc) {

        //print a summary of user account
        curUser.printAccountsSummary();

        //init
        int choice;

        //user menu
        do {
            System.out.printf("welcome " + curUser.getFirstName() + ", what would you like to do\n");
            System.out.println("1. Show history of transactions");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println();
            System.out.println("Enter your choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please enter 1-5 !");
            }

        } while (choice < 1 || choice > 5);

        //process the choice
        switch (choice) {

            case 1:
                ATM.showTransHistory(curUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(curUser, sc);
                break;
            case 3:
                ATM.depositFunds(curUser, sc);
                break;
            case 4:
                ATM.transferFunds(curUser, sc);
                break;
        }

        //redisplay menu unless user quits
        if (choice != 5) {
            ATM.printUserMenu(curUser, sc);
        }
    }

    private static void depositFunds(User curUser, Scanner sc) {

        //inits
        int toAcc;
        double amount;
        double accBal;
        String memo;

        //get the acc to transfer from
        do {
            System.out.println("Enter the number(1-" + curUser.numAccounts() + " of the account to deposit in: ");
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 || toAcc >= curUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (toAcc < 0 || toAcc >= curUser.numAccounts());

        accBal = curUser.getAccountBallance(toAcc);

        //get the ammount to transfer

        System.out.printf("Enter the amount to transfer  $");
        amount = sc.nextDouble();
        if (amount < 0) {
            System.out.println("you cannot enter negative amount! ");
        } else {


            //clean scanner
            sc.nextLine();

            //get the memo
            System.out.println("Enter a memo: ");
            memo = sc.nextLine();

            //do the withdrawal
            curUser.addAccTransaction(toAcc, amount, memo);
        }
    }

    private static void withdrawFunds(User curUser, Scanner sc) {

        //inits
        int fromAcc;
        double amount;
        double accBal;
        String memo;

        //get the acc to transfer from
        do {
            System.out.println("Enter the number(1-" + curUser.numAccounts() + " of the account to withdraw from");
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= curUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (fromAcc < 0 || fromAcc >= curUser.numAccounts());

        accBal = curUser.getAccountBallance(fromAcc);

        //get the ammount to transfer
        do {
            System.out.printf("Enter the amount to transfer: (max $%.02f): $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("you cannot enter negative amount! ");
            } else if (amount > accBal) {
                System.out.printf("Amount must be not greater than " +
                        "the balance of  $%0.2f\n", accBal);
            }


        } while (amount < 0 || amount > accBal);

        //clean scanner
        sc.nextLine();

        //get the memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdrawal
        curUser.addAccTransaction(fromAcc, -1 * amount, memo);
    }

    private static void transferFunds(User curUser, Scanner sc) {

        //inits
        int fromAcc;
        int toAcc;
        double amount;
        double accBal;

        //get the acc to transfer from
        do {
            System.out.println("Enter the number(1-" + curUser.numAccounts() + " of the account to transfer from");
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= curUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (fromAcc < 0 || fromAcc >= curUser.numAccounts());

        accBal = curUser.getAccountBallance(fromAcc);

        //get the account to transfer to
        do {
            System.out.println("Enter the number(1-" + curUser.numAccounts() + " of the account to transfer to");
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 || toAcc >= curUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (toAcc < 0 || toAcc >= curUser.numAccounts());

        //get the amount to transfer

        do {
            System.out.printf("Enter the amount to transfer: (max $%.02f): $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("you cannot enter negative amount! ");
            } else if (amount > accBal) {
                System.out.printf("Amount must be not greater than " +
                        "the balance of  $%0.2f\n", accBal);
            }


        } while (amount < 0 || amount > accBal);

        //finally do the transfer
        curUser.addAccTransaction(fromAcc, -1 * amount, String.format("Transfer to account %s", curUser.getAccUUID(toAcc)));
        curUser.addAccTransaction(toAcc, amount, String.format("Transfer to account %s", curUser.getAccUUID(fromAcc)));
    }

    private static void showTransHistory(User curUser, Scanner sc) {

        int theAcc;
        do {
            System.out.println("Enter the number(1-" + curUser.numAccounts() + " of the account whose transactions you want to see");

            theAcc = sc.nextInt() - 1;
            if (theAcc < 0 || theAcc >= curUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }

        } while (theAcc < 0 || theAcc >= curUser.numAccounts());

        curUser.printActTransHistory(theAcc);


    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        //inits
        String userId;
        String pin;
        User authUser;

        //prompt for id and pin until correct
        do {

            System.out.println("\n\n Welcome to\n\n" + theBank.getName());
            System.out.println("Enter user ID: ");
            userId = sc.nextLine();
            System.out.println("Enter pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userId, pin);
            if (authUser == null) {
                System.out.println("Incorrect id/pin combo " +
                        "Please try again!");
            }

        } while (authUser == null);

        return authUser;
    }
}
