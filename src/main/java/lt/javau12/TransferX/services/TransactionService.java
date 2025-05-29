package lt.javau12.TransferX.services;

import jakarta.transaction.Transactional;
import lt.javau12.TransferX.DTO.TransactionResponseDto;
import lt.javau12.TransferX.DTO.TransferRequestDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Transaction;
import lt.javau12.TransferX.enums.TransactionType;
import lt.javau12.TransferX.exeptions.NotFoundExeption;
import lt.javau12.TransferX.mappers.TransactionMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    @Transactional
    public TransactionResponseDto sendMoney(TransferRequestDto requestDto){
        Account sender = accountRepository.findById(requestDto.getFromAccountId())
                .orElseThrow(()-> new RuntimeException("Sender account not found"));

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

        String senderFullName = sender.getUser().getName() + " " + sender.getUser().getLastName();
        int transactionCount = transactionRepository.findAllBySenderAccountId(sender.getId()).size();

        return transactionMapper.toResponseDto(transaction, senderFullName, transactionCount +1, TransactionType.EXPENSE );


    }

    public List<TransactionResponseDto> getHistoryByIbanAndPeiod(String iban, String period){
        Account account = accountRepository.findByIban(iban)
                .orElseThrow(()-> new NotFoundExeption("Account not found"));

        LocalDateTime fromDate = switch (period){
            case "Ši diena" -> LocalDate.now().atStartOfDay();
            case "Savaitė" -> LocalDate.now().minusDays(7).atStartOfDay();
            case "Mėnuo" -> LocalDate.now().minusMonths(1).atStartOfDay();
            default -> LocalDateTime.MIN;
        };

        List<Transaction> sent = transactionRepository
                        .findAllBySenderAccountIdAndTimestampAfter(account.getId(), fromDate);
        List<Transaction> received = transactionRepository
                        .findAllByReceiverAccountIdAndTimestampAfter(account.getId(), fromDate);

        List<Transaction> all = new ArrayList<>();
        all.addAll(sent);
        all.addAll(received);

        return all.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .map(transaction -> {

                    int number = all.indexOf(transaction) + 1; // paprastas eiliškumo skaičiavimas

                    TransactionType transactionType = transaction.
                                                getSenderAccount()
                                                .getId()
                                                .equals(account.getId())
                            ?TransactionType.EXPENSE
                            :TransactionType.INCOME;

                    String fullName = transactionType == TransactionType.EXPENSE
                            ? transaction.getReceiverAccount().getUser().getName() + " "
                                                + transaction.getReceiverAccount().getUser().getLastName()
                            : transaction.getSenderAccount().getUser().getName() + " "
                                                + transaction.getSenderAccount().getUser().getLastName();

                    return transactionMapper.toResponseDto(transaction, fullName, number, transactionType);
                })
                .toList();
    }
}
