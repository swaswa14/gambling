package ph.jsalcedo.backendv2.exceptions;

public class InvalidDepositAmountException extends RuntimeException{
    public InvalidDepositAmountException() {
        super("Deposit amount must be greater than or equal to $1");
    }
}
