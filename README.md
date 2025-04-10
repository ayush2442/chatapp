# ChatApp

A basic **Client-Server Chat Application** built using **Core Java**. This application demonstrates socket programming with multi-threading and a graphical user interface on the client side.

## Technologies and Core Java Concepts Used

### 1. **Networking**
- **`ServerSocket` and `Socket`**  
  Used to establish communication between server and client over port `8000`.
- **`BufferedReader` and `PrintWriter`**  
  For reading input and sending output via input/output streams connected to the sockets.

### 2. **Multi-threading**
- Used to handle simultaneous read and write operations:
  - One thread handles **reading** messages.
  - Another thread handles **writing** messages.
- Threads are implemented using `Runnable` interfaces and started with `new Thread(runnable).start()`.

### 3. **GUI Programming (Client Side Only)**
- Built using **Swing**:
  - `JFrame`, `JTextArea`, `JTextField`, `JScrollPane`, `JLabel`
- Layout handled with `BorderLayout`
- Event handling using `KeyListener` for detecting when the "Enter" key is pressed to send a message.

### 4. **Exception Handling**
- Try-catch blocks ensure the application handles disconnections and other IO errors gracefully.

### 5. **OOP Principles**
- **Encapsulation:** Socket and stream operations are wrapped within the `client` and `server` classes.
- **Constructor Usage:** All connection and setup logic is done in class constructors.
- **Class Design:** Cleanly separates responsibilities between the client and server classes.

---

## Features

- Simple text-based communication between server and client.
- GUI-based input/output for the client side.
- Multi-threaded message exchange (read & write operations work simultaneously).
- Auto-disconnection on typing "exit" or "Exit".
- Graceful shutdown of connection.

---

