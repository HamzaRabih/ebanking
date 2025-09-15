package ma.enset.iibdcc.ebankingbackend.exeptions;

public class BalanceNotSufficientExeption extends Exception {
    public BalanceNotSufficientExeption(String message) {
        super(message);
    }
}
