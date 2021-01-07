package behavioral.templatemethod;

abstract class Game {

    protected final int numberOfPlayer;
    protected int currentPlayer;

    protected Game(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public void run() {
        start();
        while (!haveWinner()) {
            takeTurn();
        }
        System.out.println("Player " + getWinningPlayer() + " wins.");
    }

    protected abstract int getWinningPlayer();

    protected abstract void takeTurn();

    protected abstract boolean haveWinner();

    protected abstract void start();
}

class Chess extends Game {

    private static final int MAX_TURNS = 10;
    private int turn = 2;

    public Chess() {
        super(2);
    }

    @Override
    protected int getWinningPlayer() {
        return currentPlayer;
    }

    @Override
    protected void takeTurn() {
        System.out.println("Turn " + (turn++) + " taken by player " + currentPlayer);
        currentPlayer = (currentPlayer + 1) % numberOfPlayer;
    }

    @Override
    protected boolean haveWinner() {
        return turn == MAX_TURNS;
    }

    @Override
    protected void start() {
        System.out.println("Starting a game of chess...");
    }
}

class TemplateMethodDemo {

    public static void main(String[] args) {

        Game chess = new Chess();
        chess.run();
    }
}