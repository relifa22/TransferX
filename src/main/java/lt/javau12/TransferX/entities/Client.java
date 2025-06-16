package lt.javau12.TransferX.entities;

import jakarta.persistence.*;
import lt.javau12.TransferX.enums.IncomeType;
import lt.javau12.TransferX.enums.ClientType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    private LocalDate birthDate;

    @Column(unique = true)
    private String personalIdentificationNumber;

    private String documentNumber;
    private BigDecimal monthlyIncome;

    @Enumerated(EnumType.STRING)
    private IncomeType incomeType;

    private String country;
    private String city;
    private String address;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Client parent;

    private boolean verified;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "parent")
    private List<Client> children = new ArrayList<>();

    public Client(){

    }

    public Client(String name,
                  String lastName,
                  String email,
                  String password,
                  LocalDate birthDate,
                  String personalIdentificationNumber,
                  String documentNumber,
                  BigDecimal monthlyIncome,
                  IncomeType incomeType,
                  String country,
                  String city,
                  String address) {

        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.documentNumber = documentNumber;
        this.monthlyIncome = monthlyIncome;
        this.incomeType = incomeType;
        this.country = country;
        this.city = city;
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Client getParent() {
        return parent;
    }

    public void setParent(Client parent) {
        this.parent = parent;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Client> getChildren() {
        return children;
    }

    public void setChildren(List<Client> children) {
        this.children = children;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
