# Debian
## BSI-4
### Adriel Andrade, Kauana Mondim, Danilo Tenesse, Vinícius Palhares

## Instalação:
```bash
sudo apt update
sudo apt install openjdk-17-jdk maven git
git clone https://github.com/brunobaruffi/blockchain
cd ./blockchain
./mvnw clean install
```
## Rodar:
```bash
./mvnw spring-boot:run
```

## EndPoints:
- http://localhost:8080/
- http://localhost:8080/node/${Params}

### Params:
#### GET:
- isValid
- blockchain
- block/${(int) index}

#### POST:
- addBlock
-- (String) data
- addWallet
-- (String) username
-- (String) password

---