package ph.jsalcedo.backendv2.exceptions;

public class InvalidWithdrawalAmountException extends RuntimeException{
    public InvalidWithdrawalAmountException() {
        super("Withdrawal amount should be greater than $0.00");
    }
}
