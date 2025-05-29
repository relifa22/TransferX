package lt.javau12.TransferX.mappers;

import lt.javau12.TransferX.DTO.TransactionResponseDto;
import lt.javau12.TransferX.DTO.TransferRequestDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Transaction;
import lt.javau12.TransferX.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransferRequestDto transferRequestDto,
                                Account sender,
                                Account receiver,
                                String transactionNumber,
                                TransactionType transactionType){

        Transaction transaction = new Transaction();
        transaction.setAmount(transferRequestDto.getAmount());
        transaction.setDescription(transferRequestDto.getDescription());
        transaction.setTransactionNumber(transactionNumber);
        transaction.setTransactionType(transactionType);
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);
        return transaction;
    }

    public TransactionResponseDto toResponseDto(Transaction transactionEntity,
                                                String senderFullName,
                                                Integer number,
                                                TransactionType transactionType){

        return new TransactionResponseDto(transactionType,
                number,
                transactionEntity.getTimestamp(),
                senderFullName,
                transactionEntity.getDescription(),
                transactionEntity.getAmount()
        );

    }

}
