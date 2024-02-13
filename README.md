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
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8Uyh8fWmrRn0D10BoTGZLNYbNBpI8YKCIJw0D5-IFMPWwkXq3FEqkMtkcvo1PiO3KVryNhoesXYAmc3q85MYAhZ0g0DbvfKT-zs7ibwa6TGTTBHheN4jwdH5hQiV0QXBSEvVhBEICRRZP11U4U0NS4-zuU0UFZC0rRfT5o0dWMRUBN1pVlLt5TLPt53VTUyyIsDnQiHUqWTQkUFLdMKyzNjcx-QsL243teKraga0LeBkAbZs20PKiVho3iBzQIdTHMKxbDMFBQxndhLGYGw-ACIIZNXKIJI3BIZDBYF0mBHc9y4A97B4zNxP6VjE2-ND4gfAzLTmZTMwOfjfM4i5jSwgCnjw8tM2C9z0FAp040iSC7JgkLQ3gpFGPC1DIoiaLGUMFAECeFB4topLRNCpi0tI+JbPBBz4URGB4u+GAAHUAAkPWBHsEtDABeEbaMakjC0K6k-JgEkyWQ9jb1pKThMW0lyQvWsV0bGBWxbTBB2HTSx2mDRpzcGAAHE7UxYzFzMkJmEGTaElu+yd3YO1ihyrNdu868ivzVNkB4e7qi4OrRrQMKfNBgsMOI-9AJq3jYdo1KZoysioIhKEAY6hCRpWgS0N-VGYvRskodUOYcYxZryCyqEFDJAC7RJpF6e+abmdmxH5s40s6YeiMoD4RxyYisHDXe9cxYQenMTwaXPMkwZ9rk9tSmAcXocl6XVPUkctJsbALCgbBKvgM0DHphdTOXcy3rXKz4mSNIslyX7qn+5K0A7f2UAlO1z3XC45o4+XsNZem5lD8PqKDhGQZF+WqeY+5YrADHEoBpnwLxoE2cm3iebJmO1vQmBStzwDE+Tu1i+dUvWegqEW+qKvQ4F8Dga-JHUyW8ka8E8IG9LDQNGbu0U-tAf24BcV3U9HuVlDsUZDDTV+4b2sJ4W7eZFlkeFY9mIHjtHfNcvbW3d1kPb5kHoTrUs7R1sRwKofbwYAACkIBPjutzGw2gECgCDK7V6-wPpJGeL7HIodA71XQB2facAIAPigHUU+kcrL-GPqLGAAArEBaBE4AzqFgnB0B8Gv3TsPTOyN66YTKujIOWNeJt3SqvTuhMK6ZiroxQ+QsM6xzvADc+rD1ooxzg8J47IoAACFHAF3QDwhq4iO4ExgkeUR-o+qDRYMNYmE0ZHiKHihORXFYJ2lkVI+R088703UTvJOTDl78Myl3Lm1Qd590cdY8IJC44BJQHfcJyNXGASSFA3+UA5h0NwYwwJMg+Es30VCFJbtsG4L2CouCnVQ4mKGpEoJE1T4+KPsLZx9ix5ONrtnNKM8NAJOAEkuYIB8n0LwQ46oWSXRkUlBRQZBh6KRIjMwXpr0CnQBMKdDS38bBmC6dJHCsBgDYFtoQG8zslz7XgUrRIrV7KOVyEYe+xD6m1yZJVPAChdnIBADeRmzTJ7sOpmVDQFUqocwQPFD5uiBHnPagoUEoIRpV26vIWpEiWENOJNtT5lMjQcNzn8yq5oyQqJBZilm4LhqQuhYsKuKj+ahJiaPVFNK66uOxQCw2KACU-N8WRYlXUoXTLymA6oVLCWItsciray1TpAA