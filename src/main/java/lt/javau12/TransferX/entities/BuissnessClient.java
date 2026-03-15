package lt.javau12.TransferX.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
public class BuissnessClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String companyName;
    private String address;

    @Column(unique = true)
    private String email;
    private String password;





}
