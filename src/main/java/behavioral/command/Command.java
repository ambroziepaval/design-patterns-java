package behavioral.command;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

interface Command {
    void call();

    void undo();
}

class BankAccount {

    private int balance;
    private int overdraftLimit = -500;

    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ", balance is now " + balance);
    }

    public boolean withdraw(int amount) {
        if (balance - amount >= overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew " + amount + ", balance is now " + balance);
            return true;
        } else {
            System.out.println("Cannot withdraw " + amount + " (exceeding overdraft)");
            return false;
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

class BankAccountCommand implements Command {

    private BankAccount account;
    private Action action;
    private int amount;
    private boolean succeeded;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }

    @Override
    public void call() {
        switch (action) {
            case DEPOSIT:
                succeeded = true;
                account.deposit(amount);
                break;
            case WITHDRAW:
                succeeded = account.withdraw(amount);
                break;
        }
    }

    @Override
    public void undo() {

        if (!succeeded) {
            return;
        }

        switch (action) {
            case DEPOSIT:
                account.withdraw(amount);
                break;
            case WITHDRAW:
                account.deposit(amount);
                break;
        }
    }

    public enum Action {
        DEPOSIT, WITHDRAW
    }
}

class CommandDemo {
    public static void main(String[] args) {
        final BankAccount bankAccount = new BankAccount();
        System.out.println(bankAccount);

        final List<BankAccountCommand> commands = Arrays.asList(
                new BankAccountCommand(bankAccount, BankAccountCommand.Action.DEPOSIT, 100),
                new BankAccountCommand(bankAccount, BankAccountCommand.Action.WITHDRAW, 1000)
        );

        for (BankAccountCommand command : commands) {
            command.call();
            System.out.println(bankAccount);
        }

        // UNDO
        System.out.println();
        for (Command command : Lists.reverse(commands)) {
            command.undo();
            System.out.println(bankAccount);
        }
    }
}