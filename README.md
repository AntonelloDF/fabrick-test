# Progetto gestione conto Fabrick
Il progetto fornisce 3 enpoint:
- /saldo (permette la visualizzazione del saldo)
- /transazioni (permette di visualizzare le transazioni)
- /doBonifico (permette di eseguire un bonifico)

### Requisiti applicativi
- JDK 1.8
- Maven 4.0

### Build
```shell
mvn clean install

```
### URLs 
[Open Api  (http://localhost:8080)](http://localhost:8080)

[Console H2 (http://localhost:8080/h2-console/login.jsp)](http://localhost:8080/h2-console/login.jsp) 

### REST API
1. GET `/saldo` 
2. GET `/transazioni` 
3. POST `/doBonifico`
