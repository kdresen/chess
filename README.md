# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
## Phase 2 Sequence Diagram Link
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8Uyh8fWmrRn0D10BoTGZLNYbNBpI8YKCIJw0D5-IFMPWwkXq3FEqkMtkcvo1PiO3KVryNhoesXYAmc3q85MYAhZ0g0DbvfKT-zs7ibwa6TGTTBHheN4jwdH5hQiV0QXBSEvVhBEICRRZo0dWNax1Klk0JFB4iPT9dVOFNDUuP87lNFBWQtK0X0+ZCwOdSJXUlaVZS7eUyz7ed1U1MtaKdOMInQ3MDVLdMKyzQTv0I2sLxE3sxKraga0LeBkAbZs20PViVnYsSBzQIdTHMKxbDMFBQxndhLGYGw-ACIJVNXKJFI3BIZDBYF0mBHc9y4A97FEzMFP6ATE0krD4gfSzLTmHTMwOCSCKwi5jVIgCnko8tMxigL0FAviRUBch3Jg2LQ3gpEeJS50QuvRL81TUrxNCuqC3CKr7kMFAECeFAMo47K5Li3jUJdQq3PBTz4URGAMu+GAAHUAAkPWBHtMtDABeNaOOGjE0Oa6lCOJUlyQSw6kuUmSYBJMkgqUiIV0bGBWxbTBB2HIyx2mDRpzcGAAHE7UxGzF3skJmEGK6En+jyd3YO1ikau7L3CM7MPq7D7zcQHqi4Ab1rQeKDvR1qYHah50py59GrykaGMKqCIShRqpoQtbdvAmqvxahqqbwjDb1pI0SMZNKwAUMkcdUOZab20agWKqEJYQAC7VZpEpe+DnqtR4nBcx4BJaBiMoD4Rx+aEqTLvXUsjdxk2zeR2sVPB9T21KQ2EClzE8DNvSDJHYybGwCwoGwbr4DNAwpYXOzlwciG12c+JkjSLJcnh6pEapjtM5QCU7XPdcLjR-WyNZKW5jzgu2KponavOjHfxQ-9AL6sT8Y42XwPphXoOZqn1fZ9r9obknecGytS5-YWW9SwDK+ru1u-ogE+6Z1Xqhrgxys3sQR8Laejuuk6LbCpuInJiMNA0Re7W3le417t1mL37e6jzsUZDDTU8+1-jdZjzLnvL+Z8eaGkhjbEBMgnbKUem7XOdov49DevpD6o5bCOC6g+bwMAABSEAnwAzVjYbQCBQBBnjuDf4UMkjPHTjkPO2dJ5oA7I9OAEAHxQA-kgmQRdnL-CPuFGAAArQhaBK6NTqOwzh0AeHVC-vXbmjdSZXzblTTuYlH4FXXiVQeu9KoiwAUIjGskCZgJUULYic9RaAXZFAAAQo4duWUab-x0UVfusE1YGP9AtZaLBVosy2kjA+XN8KWMxrhExrU1FPClk4r+VdeHaPlp4jen9v67z-mEwByjx6Y0yRYgpyUjEUzAEkchWCoBzBkVw+RKBFHuLSYzGCtSE4cK4XsexcFpp538StaBMAtpFNyTE1MN1Tp6xntYuiHVgA30qcAapcwQAdNkdw7x1RUnPyYh6Fitpqg-z3hGZgazwadOgM08JAthIn1uuMiB4QrqTNgQ9BObsTDvUMhgmwZhlkqXIrAYA2Bw6EBvLHJcj0aFQNcu5TyO4jCwMiI8pk3U8AKFBcgEAN4ZbFP1s3OZ8QNBdR6srDKeKD7P3Gh5VaChQSgjWkPWa8hrl5IiQU46DzplW1mXxYlpLzRknsZSoxHiaWTXpYyxYQ97FazGTy4RrzHmEv5Z1bqQqvZ2lFTYp+a8ybwrpQyk5u9NasoVUAu5yrFUX0gcne55ILzO3gc9NsqChxAA
