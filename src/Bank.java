import java.util.ArrayList;
import java.util.Random;

public class Bank {

    //    name of the bank
    private String name;

    //    List of users
    private ArrayList<User> users;

    //    list of accounts
    private ArrayList<Account> accounts;

    //constructor for bank with empty list for accounts and users
    public Bank(String name) {

        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();

    }

    public String getName() {
        return name;
    }

    //    Creates new unique User ID
//    Make sure it doesnt exist already
    public String getNewUserUUID() {

        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

//        continue looping until we get a unique id
        do {

            //generate number in set length and append to uuid
            uuid = "";
            for (int c = 0; c < len; c++) {

                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }

            }


        } while (nonUnique);


        return uuid;
    }

    //    creates new unique Account ID
    public String getNewAccountUUID() {

        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        //Contiue looping until we get a unique ID
        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (Account acc : this.accounts) {
                if (uuid.compareTo(acc.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);


        return uuid;
    }

    //    add account to the list of accounts
    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }

    public User addUser(String firstName, String lastName, String pin) {

        //Create new userObject and add to list of users
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //create a savings account
        Account newAccount = new Account("Savings", newUser, this);

        //assing the account to user
        newUser.addAccount(newAccount);

        //add account to bank
        this.accounts.add(newAccount);


        return newUser;
    }


    public User userLogin(String userID, String pin) {

        //search through list of users
        for (User u : this.users) {

            //check user ID correct
            if(u.getUUID().compareTo(userID)== 0 && u.validatePin(pin)) {
                return  u;
            }

        }

        return null;
    }

}
