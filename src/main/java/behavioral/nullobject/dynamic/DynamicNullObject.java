package behavioral.nullobject.dynamic;

import java.lang.reflect.Proxy;

interface Log {

    void info(String msg);

    void warn(String msg);
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
}

class DynamicNullObjectDemo {

    @SuppressWarnings("unchecked")
    public static <T> T noOp(Class<T> itf) {
        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[]{itf},
                (proxy, method, args) -> {
                    if (method.getReturnType().equals(Void.TYPE)) {
                        return null;
                    } else {
                        return method.getReturnType().getConstructor().newInstance();
                    }
                }
        );
    }

    public static void main(String[] args) {

        Log log = noOp(Log.class);

        final BankAccount bankAccount = new BankAccount(log);
        bankAccount.deposit(100);
    }
}
