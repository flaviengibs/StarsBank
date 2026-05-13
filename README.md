# StarsBank

A simple console-based banking application written in Java.

## Features

- Create customers and bank accounts
- Deposit, withdraw, and transfer money between accounts
- Customer and admin login (password-hashed with PBKDF2)
- Suspend, unsuspend, and close accounts
- Persistent storage via `save.json`
- Scheduled salary payments (`ScheduledSalary`)

---

## Requirements

- **Java 11 or higher** — [Download here](https://aws.amazon.com/fr/corretto/)
- No Maven installation needed — the Maven Wrapper (`mvnw`) is included.

---

## Run from source

```bash
# Clone the repo
git clone https://github.com/flaviengibs/StarsBank.git
cd StarsBank

# Build (downloads dependencies automatically)
./mvnw package          # Linux / macOS
mvnw.cmd package        # Windows

# Run
java -jar target/StarsBank.jar
```

> On Linux/macOS you may need to make the wrapper executable first:
> ```bash
> chmod +x mvnw
> ```

---

## Run the pre-built JAR

If a `StarsBank.jar` release is available on the [Releases page](../../releases):

```bash
java -jar StarsBank.jar
```

The JAR is self-contained — no extra dependencies needed.

---

## Scheduled salary payments

`ScheduledSalary` is a separate entry point that credits each customer's salary on the 4th of every month.  
You can run it manually or schedule it with a cron job / Task Scheduler:

```bash
# Build first, then run with a different main class
java -cp target/StarsBank.jar ScheduledSalary
```

---

## Data persistence

The app reads and writes `save.json` in the **working directory** (the folder you run the command from).  
Keep that file alongside the JAR to preserve your data between sessions.

---

## Default admin password

The hardcoded admin password is `TheAdminPassword`.  
Change it in `Admin.java` before deploying.

