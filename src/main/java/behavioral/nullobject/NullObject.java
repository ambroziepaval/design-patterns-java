package behavioral.nullobject;

interface Log {

    void info(String msg);

    void warn(String msg);
}

class ConsoleLog implements Log {

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg) {
        System.out.println("WARNING: " + msg);
    }
}

class NullLog implements Log {

    @Override
    public void info(String msg) {
    }

    @Override
    public void warn(String msg) {
    }
}

class BankAccount {

    private final Log log;

    private int balance;

    public BankAccount(Log log) {
        this.log = log;
    }

    public void deposit(int amount) {
        balance += amount;

        log.info("Deposited " + amount);
    }

    public void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            if (log != null) {
                log.info("Withdrew " + amount + ", we have " + balance + " left");
            }
        } else {
            if (log != null) {
                log.warn("Could not withdraw " + amount + " because balance is only " + balance);
            }
        }
    }
}

class NullObjectDemo {
    public static void main(String[] args) {
        ConsoleLog log = new ConsoleLog();
        final BankAccount bankAccount = new BankAccount(log);
        bankAccount.deposit(100);
        bankAccount.withdraw(200);

        Log nullLog = new NullLog();
        final BankAccount bankAccount2 = new BankAccount(nullLog);
        bankAccount2.deposit(100);
    }
}
