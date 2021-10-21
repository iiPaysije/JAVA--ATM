import javax.sound.midi.SysexMessage;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {

    private String firstName;
    //        First name of user

    private String lastName;
    //    Last name of user

    private String uuid;
    //    Unique User ID

    private byte pinHash[];
    //    Md5 hash of pincode for user

    private ArrayList<Account> accounts;
    //    list of accounts for user


    public User(String firstName, String lastName, String pin, Bank theBank) {

        //set's users name
        this.firstName = firstName;
        this.lastName = lastName;

        // store pins md5 hash
        // security
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Greska u algoritmu ");
            System.out.println(e.toString());
            System.exit(1);
        }

        //get a new UUID
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<Account>();

        System.out.println("new user created!");
        System.out.println(this.uuid);



    }

    //    Assign account to user
    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUUID() {
        return this.uuid;
    }

//    Check if take ping, digest it, and compare to stored pinHash for user
    public boolean validatePin(String pin) {

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException e) {

            System.err.println("No such algorithm");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    public void printAccountsSummary() {
        System.out.println(this.firstName + " Accounts summary");
        for (int i = 0; i < this.accounts.size(); i++) {

            System.out.printf("%d) %s\n", i+1,
                            this.accounts.get(i).getSummaryLine() );

        }
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printActTransHistory(int theAccIdx) {
        this.accounts.get(theAccIdx).printTransHistory();
    }

    public double getAccountBallance(int accIdx) {
        return this.accounts.get(accIdx).getBalance();
    }

    public String getAccUUID(int accIdx) {
        return this.accounts.get(accIdx).getUUID();
    }

    public void addAccTransaction(int accIdx, double amount,  String memo) {
        this.accounts.get(accIdx).addTransaction(amount, memo);


    }
}
