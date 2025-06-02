package lt.javau12.TransferX.exeptions;

public class SelfTransferException extends RuntimeException {
  public SelfTransferException(String message) {
    super(message);
  }
}
