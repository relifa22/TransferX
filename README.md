# TransferX – Banking Application

Moderni bankinė sistema, sukurta naudojant Java 21, Spring Boot ir React. Projektas simuliuoja pagrindines banko operacijas: naudotojų registraciją, sąskaitų valdymą, pavedimus, kortelių kūrimą ir ribojimų nustatymus.

## Funkcionalumai

- Naudotojų registracija ir prisijungimas
- Automatinis sąskaitos sukūrimas registracijos metu
- Sąskaitų peržiūra, balansai
- Pavedimai tarp sąskaitų (vidiniai pervedimai)
- Kortelių sukūrimas ir limitų valdymas
- Operacijų istorija

## Technologijos

| Technologija       | Naudojimo tikslas                         |
|--------------------|-------------------------------------------|
| Java 21            | Backend logika                            |
| Spring Boot        | REST API, validacija, priklausomybių injekcija |
| MySQL              | Duomenų bazė                              |
| Hibernate (JPA)    | ORM – Entity susiejimas su DB             |
| Bean Validation    | DTO validacija su anotacijomis (@Valid)   |
| React + Vite       | Frontend (modernus ir greitas)            |

## Pradėjimas (Getting Started)

1. Klonuok projektą:

```
git clone https://github.com/relifa22/TransferX.git
```

2. Importuok kaip Maven projektą į pasirinktą IDE

3. Paleisk projektą:

```
./mvnw spring-boot:run
```

Backend veikia per numatytąją prievadą: [http://localhost:8080](http://localhost:8080)

## API Dokumentacija

_(Planuojama naudoti Swagger dokumentaciją ateityje)_

### Pavyzdiniai API endpoint'ai (bus aktyvuoti paleidus projektą)

- `POST /api/users` – Naujo naudotojo registracija
- `POST /api/accounts` – Naujos sąskaitos sukūrimas
- `POST /api/transactions` – Pavedimas tarp sąskaitų
- `POST /api/cards` – Kortelės sukūrimas
- `PUT /api/cards/limits` – Kortelės limitų atnaujinimas

## Naudotojų tipai

- **Suaugusysis** – turi visas funkcijas
- **Vaikas / Paauglys** – gali tik gauti pinigus ir atsiskaityti (dienos limitas iki 10 €)

## Darbai planuojami:

- Swagger UI integracija
- JWT autentifikacija
- React frontend funkcionalumų pilnas sujungimas

---

**Sukūrė:** Renata Vaičiūnaitė