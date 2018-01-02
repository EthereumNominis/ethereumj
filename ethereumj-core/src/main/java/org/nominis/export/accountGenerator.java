package org.nominis.export;

        import org.ethereum.core.Account;

        import java.util.ArrayList;
        import java.util.Random;


public class accountGenerator {

    private static int numberOfAccount;
    private static ArrayList<Account> accounts;
    private static Random rand;

    /**
     * -----------------------------------------
     * set number of generated accounts
     *
     * @param int numberOfAccount
     *            -----------------------------------------
     **/

    public accountGenerator(int num) {
        this.numberOfAccount = num;
        this.rand = new Random();
        this.accounts = new ArrayList<Account>();

    }

    public int getNumberOfAccount() {
        return numberOfAccount;
    }

    public void setNumberOfAccount(int numberOfAccount) {
        this.numberOfAccount = numberOfAccount;
    }


    /**
     * -----------------------------------------
     * generate a series of random accounts
     * with address and private key
     * ArrayList would be for containing the info
     * tuple would be for each pair of account
     * <p>
     * <p>
     * --77 ------------
     * The getGeneratedAccounts is able to provide a list of accounts.
     * However, you would not be able to get balance.
     * These accounts may only contain private keys, public key and addresses
     *
     *
     *
     * The starting balance of each new account will be a random BigInteger Y, where: 0 < Y < X ; such that X has 'numBits' # of bits.
     * So essentially numBits sets the upper bound of the starting balance Y.
     * -----------------------------------------
     **/


    public ArrayList<Account> getGeneratedAccounts() {
        Account itemAccount;
        ArrayList<Account> listOfAccount = new ArrayList<Account>();
        try {
            if (accounts.size() == 0) {
                for (int i = 0; i < numberOfAccount; i++) {
                    itemAccount = new Account();
                    itemAccount.init();
                    listOfAccount.add(itemAccount);
                }
                this.accounts = listOfAccount;
                return this.accounts;
            } else return this.accounts;
        }
        catch(NullPointerException e){
            System.out.println("problem with getGeneratedAccounts function");
            return null;

        }
    }
}
