# 🎯 Guess Game - gRPC Implementation

## 📌 Overview
The **Guess Game** is a gRPC-based interactive game where a client tries to guess a randomly generated number between **1 and 100**. The server provides feedback on whether the guess is **TOO LOW**, **TOO HIGH**, or **CORRECT**.

## ⚡ Requirements
- The **client** sends numbers between **1 and 100**.
- The **server** responds with one of the following messages:
    - **TOO LOW** → If the guessed number is lower than the target.
    - **TOO HIGH** → If the guessed number is higher than the target.
    - **CORRECT** → If the guessed number matches the target.
- The client keeps guessing until it finds the correct number.

## 🛠 Technology Stack
- **gRPC** for communication
- **Java** (or another language of choice) for server & client implementation
- **Protocol Buffers (protobuf)** for message serialization

## 🚀 How It Works
### 1️⃣ Server Side
- The server randomly generates a number between **1 and 100**.
- It listens for incoming guesses from the client.
- It responds with **TOO LOW**, **TOO HIGH**, or **CORRECT** based on the guess.
- The server continues responding until the client finds the correct number.

### 2️⃣ Client Side
- The client sends a guessed number to the server.
- It receives feedback from the server.
- If the response is **CORRECT**, the game ends; otherwise, it continues guessing.

## 🎮 Example Game Play
```
Client: Guessing 50
Server: TOO LOW
Client: Guessing 75
Server: TOO HIGH
Client: Guessing 63
Server: TOO LOW
Client: Guessing 68
Server: CORRECT! You won!
```
