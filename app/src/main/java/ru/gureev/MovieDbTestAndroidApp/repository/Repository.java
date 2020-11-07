package ru.gureev.MovieDbTestAndroidApp.repository;

public class Repository {

    private static volatile Repository instance;

    private AccountData accountData;

    private Repository() {
        accountData = new AccountData();
    }

    public static Repository getInstance() {
        Repository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Repository.class) {
            if (instance == null) {
                instance = new Repository();
            }
            return instance;
        }
    }


    public AccountData getAccountData() {
        return accountData;
    }

}
