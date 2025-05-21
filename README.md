
# TransferX – Moderni P2P Mokėjimų Sistema

TransferX – tai inovatyvi, PayPal tipo sistema, leidžianti vartotojams valdyti sąskaitas, siųsti pinigus, turėti virtualias korteles ir stebėti finansinius srautus. Sistema palaiko vaikų/paauglių sąskaitas su apribojimais ir paruošta augti į realaus pasaulio produktą.

---

## Funkcionalumas

- Vartotojų registracija ir prisijungimas
- Automatinis EUR sąskaitos sukūrimas
- Galimybė atidaryti papildomas sąskaitas
- Pinigų siuntimas tarp vartotojų
- Pavedimų istorija
- Virtualios kortelės su limitais
- Vaikų ir paauglių sąskaitų valdymas su apribojimais (10 €/diena)
- Dokumentų įkėlimas ir validavimas

---

## Naudojamos technologijos

- Java 21
- Spring Boot
- Maven
- MySQL (naudojama per MySQL Workbench)
- JPA (Hibernate) duomenų saugojimui
- REST API
- Validacija (vietoj autentifikacijos)

Pastaba: Šiuo metu JWT autentifikacija dar neįdiegta – naudojama tik duomenų validacija. Pilnas saugumas planuojamas ateityje.

Frontend šiuo metu nekurtas. Planuojama naudoti React + Vite ateityje.

---

## Paleidimas (backend)

1. Klonuoti projektą:
```bash
git clone https://github.com/relifa22/transferx.git
```
2. Atidaryti su IntelliJ
3. Sukurti `transferx` duomenų bazę naudojant MySQL Workbench
4. Paleisti Spring Boot programą – lentelės susikurs automatiškai

---

## Duomenų modelis ir struktūra

Projekte naudojamos `User`, `Account`, `Transaction`, `Card` klasės bei jų DTO atitikmenys: `UserDto`, `CreateUserDto`, `LoginDto`, `CreateAccountDto`, `AccountDto`.

Visi duomenys validuojami ir apdorojami per servisus. Klasių ryšiai apibrėžti su JPA anotacijomis.

---

## Sukūrė

relifa22  
GitHub: [https://github.com/relifa22](https://github.com/relifa22)
