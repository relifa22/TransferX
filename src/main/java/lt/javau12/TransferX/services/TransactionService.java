package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.TransactionResponseDto;
import lt.javau12.TransferX.DTO.TransferRequestDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Transaction;
import lt.javau12.TransferX.enums.TransactionType;
import lt.javau12.TransferX.mappers.TransactionMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository,
                              TransactionMapper transactionMapper) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponseDto sendMoney(TransferRequestDto requestDto){
        Account sender = accountRepository.findById(requestDto.getFromAccountId())
                .orElseThrow(()-> new RuntimeException("Sender account not fount"));

        Account receiver = accountRepository.findByIban(requestDto.getToIban())
                .orElseThrow(()-> new RuntimeException("Receiver account not found"));

        if (sender.getIban().equals(receiver.getIban())) {
            throw new RuntimeException("Cannot send money to same account");
        }

        if (sender.getBalance().compareTo(requestDto.getAmount()) < 0){
            throw new RuntimeException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance().subtract(requestDto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(requestDto.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        String transactionNumber = UUID.randomUUID().toString();

        Transaction transaction = transactionMapper.toEntity(
                requestDto,
                sender,
                receiver,
                transactionNumber,
                TransactionType.EXPENSE
        );
        transactionRepository.save(transaction);

        String sendelFullName = sender.getUser().getName() + " " + sender.getUser().getLastName();
        int transactionCount = transactionRepository.findAllBySenderAccountId(sender.getId()).size();

        return transactionMapper.toResponseDto(transaction, sendelFullName, transactionCount +1 );


    }
}
