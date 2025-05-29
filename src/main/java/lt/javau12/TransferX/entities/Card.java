package lt.javau12.TransferX.entities;

import jakarta.persistence.*;
import lt.javau12.TransferX.enums.CardBrand;
import lt.javau12.TransferX.enums.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(unique = true)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardBrand cardBrand;

    @Column(length = 3, nullable = false)
    private String cvv;

    private LocalDate expirationDate;
    private BigDecimal dailySpendingLimit;
    private BigDecimal weeklySpendingLimit;
    private BigDecimal monthlySpendingLimit;
    private boolean isActive;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Card(){

    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardBrand getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(CardBrand cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getDailySpendingLimit() {
        return dailySpendingLimit;
    }

    public void setDailySpendingLimit(BigDecimal dailySpendingLimit) {
        this.dailySpendingLimit = dailySpendingLimit;
    }

    public BigDecimal getWeeklySpendingLimit() {
        return weeklySpendingLimit;
    }

    public void setWeeklySpendingLimit(BigDecimal weeklySpendingLimit) {
        this.weeklySpendingLimit = weeklySpendingLimit;
    }

    public BigDecimal getMonthlySpendingLimit() {
        return monthlySpendingLimit;
    }

    public void setMonthlySpendingLimit(BigDecimal monthlySpendingLimit) {
        this.monthlySpendingLimit = monthlySpendingLimit;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
