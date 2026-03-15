package lt.javau12.TransferX.entities;

import jakarta.persistence.*;

@Entity
public class BuisnessClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String companyName;
    private String address;

    @Column(unique = true)
    private String email;
    private String password;





}
