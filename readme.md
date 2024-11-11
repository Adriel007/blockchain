# Debian
## Instalação:
```bash
sudo apt update
sudo apt install openjdk-17-jdk
sudo apt install maven
./mvnw clean install
```
## Rodar:
```bash
./mvnw spring-boot:run
```

## EndPoints:
http://localhost:8080/node/
- addBlock (POST)
- isValid (GET)
- blockchain (GET)

---