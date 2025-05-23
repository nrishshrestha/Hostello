PHASE 1: Set Up Database

Created users table in Mysql workbench with a database name Hostel management system 

```sql
use hostel;

CREATE TABLE users (
  user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('warden','admin') NOT NULL DEFAULT 'warden'
);

INSERT INTO users (username, email, password, role) 
VALUES ('SuperAdmin', 'admin@aayush.com', 'admin123', 'admin');

select * from users

```

Here  the table is created for both admin and user. 

The user can register through only signup but it can only be warden so an admin will need to go to the login page to login. Admin can only be added manually through database for security.

PHASE 2: Setup NetBeans Project 

**Open NetBeans**

- File > New Project > Java with Ant > Java Application
- Name: `HostelManagementApp`

Now under source packages make a package name hostel inside we wi;ll create signup and login

Step 3: Create Signup Page (Java Swing) Only GUI  now

Components - Design a simple form with username, password, and signup button.

Logic- On submit, insert the user into the `users` table with role = `'warden'`. Later explanation

**Step 4: Create Login Page (Java Swing)**

- Design a form with username, password, and login button.
- On submit, query the database for user with matching username and password.
- If user found:
    - Check role.
    - If role = `'admin'`, open Admin Dashboard.
    - Else if role = `'warden'`, open Warden Dashboard.
- If no user or wrong credentials, show error.

**Step 5: Build Simple Dashboard Windows**

- Admin Dashboard and Warden Dashboard — just basic JFrame windows with labels “Welcome Admin” or “Welcome Warden” for now.

step 6: Add MySQL JDBC Connector JAR

we will be connecting the swing to database in sql but if i would send an quary to mysql in java it wont understand so i need JDBC Connector JAR it is a trnslator like a trnaspiler that is used to translate java query to sql while sending and sql to java while reciving.

Its a bridge. It is a trnaslator as well as has packages and methods to establish a connection between you swing and database.

Now the Jar file will be placed in the properties → libraries → class path cause this jar code will run while in the build process so external tools like it are added  here.

It has the Connection class and driverManager class.

step 7 Create DBConnection.java

It is the file that holds the logic to connect with the database

Create a new class: 

- In your project → right-click on **Source Packages** → **New** → **Java Class**.
- Name it: `DBConnection`

Loads the MySQL driver.

Connects to your `hostel` database 

Returns a `Connection` object that any page (Signup, Login, etc.) can use.

```sql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

```

`Connection` is an interface that tells and checks if a variable is made from it does it has things that are inside the interface or not. 
When you call `DriverManager.getConnection()`, it returns an **object** that *implements* that `Connection` interface. That object *represents* the actual live connection to your database. So that obj returned by the get Connection is of type Connection Interface.

DriverManageris a class it is like axious or Client class from appwrite that has a method to connect to the database and ask for database path name and password to access it and connect it .

```sql
public class DBConnection {
```

This class will handle all database connection work so you don’t have to repeat code every time.

```java
private static Connection conn;
```

You're creating a variable `conn` that will hold your database connection 

```java
public static Connection getConnection() {

```

It will return a `Connection` object when you call it from anywhere in your project.

```java
Connection con = DBConnection.getConnection();
```

```java
if (conn != null) {
    return conn;
}

```

**If the connection is already created, just return it** — no need to create it again. This is called **connection reuse**. It saves memory and time.

```java
try {
    // Load the MySQL JDBC driver
    Class.forName("com.mysql.cj.jdbc.Driver");

```

If this driver isn’t available, your program won't know how to connect to MySQL.

```java
    // Database URL, username and password
    String url = "jdbc:mysql://localhost:3306/hostel";
    String user = "root";
    String password = "";

```

```java
    conn = DriverManager.getConnection(url, user, password);
    System.out.println("Connected to database successfully!");

```

his line actually opens the connection using `DriverManager`.

If successful, it stores the connection in the`conn` variable and shows a success message.

```java
} catch (ClassNotFoundException | SQLException e) {
    e.printStackTrace();
}

```

If there is an error:

- `ClassNotFoundException` happens if the MySQL driver is missing.
- `SQLException` happens if the connection fails (wrong URL, user, password, etc).

```java
return conn;

```

 it **returns the connection** to whoever called `getConnection()`.

 it **returns the connection** to whoever called `getConnection()`.

Sign up form code 

**Collecting User Input**

```java
String username = txtUsername.getText().trim();
String email = txtEmail.getText().trim();
String password = new String(txtPassword.getPassword());//password is given as char so we convert it 

```

You're pulling the data the user typed into the form.

txtUsername etc are the variable name with a method to extract their data.

**Validation — Check if Fields Are Empty**

```java
if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Please fill all fields");
    return;
}
```

`JOptionPane` class has method to show a warning popup, and `return;` exits the method early.

Connect to the Database

```java
Connection conn = DBConnection.getConnection();
```

This creates a **live link** to your MySQL database.

And Connection obj is catched in the conn obj of type Connection interface.

Prepare the SQL Statement

PreparedStatement- 
It is a **Java Interface** from the package:
It is part of **JDBC (Java Database Connectivity)**.

What is SQL Injection?
It means that a hacker can just write the Sql quary inside the text fields and the without prepare Statement the hacker can inject code even if it asks for data not code.

But with the preparedStatement it cays that evry input is data in string not a code.

This is clearly explained in login page code below.

```java
String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, 'warden')";
PreparedStatement pst = conn.prepareStatement(sql);

```

conn.prepareStatement(sql);→ this just compiles my sql query and gives me a PreparedStatement obj that has methods to insert data manually insid ehte values section of the compilled query.

- You're building an SQL command, but with `?` placeholders.
- This avoids SQL injection — a security move.

Bind Parameters

```java
pst.setString(1, username);
pst.setString(2, email);
pst.setString(3, password);

```

- You fill in the blanks `?` in your SQL with real values.
- 1st `?` = username, 2nd = email, 3rd = password.

Execute the Query

```java
int inserted = pst.executeUpdate();

```

- This **sends the SQL to the database**.
- It returns the number of rows affected (should be 1 if successful).

**Handle Result**

```java
if(inserted > 0) {
    JOptionPane.showMessageDialog(this, "Signup successful! Please login.");
    new LoginPage().setVisible(true);
    this.dispose();
}

```

here new opens a whole new instance of the login page class for that user. As every user is a new instance.

this. it refers to the current GUI window that is signup page not the instance

.dispose() tells to close this window.

redirect code to login

Method Declaration

```java
private void lblLoginMouseClicked(java.awt.event.MouseEvent evt)
```

This is **an event method** — triggered when your mouse **enters the label’s area**.

lblLogin is the name of th label field variable like get element by id. 

```java
lblLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

```

lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {

This line changes the mouse pointer to a

**hand**

when you hover over the

```
loginLabel
```

.

Why? To show it’s clickable — just like a hyperlink on a website.

**the lblLable has access to all JLabel methods**, 

- `setText(...)`
- `setCursor(...)`
- `addMouseListener(...)`

 

```java
 new LoginPage().setVisible(true);
    this.dispose();
```

redirect and closing theis page

Login page build:

```java
private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {                                         

```

This method triggers when you press the **Login** button.

he `ActionEvent evt` carries info about the click; you don’t use it here, but it’s the standard way Java handles button clicks.

```java
String email = txtEmail.getText().trim();
String password = new String(txtPassword.getPassword());

```

- `txtEmail.getText()` grabs the text typed in the email field.
- `.trim()` removes spaces at the start or end — no accidental blanks.
- `txtPassword.getPassword()` returns a char array (secure way to handle passwords).
- You convert it into a String for easier comparison.

```java
if(email.isEmpty() || password.isEmpty()) {
    javax.swing.JOptionPane.showMessageDialog(this, "Please fill all fields.");
    return;
}

```

- **What:** Validation.
- **Why:** If either email or password is empty, don’t waste time querying the database.
- **How:**
    - `.isEmpty()` checks if string length is zero.
    - `JOptionPane.showMessageDialog` pops up a simple dialog alerting the user.
    - `return;` stops further execution — no login attempt without full info.

---

```java
try {
    Connection conn = DBConnection.getConnection();

```

this establishes the connection with the database and returns a conn obj.

- `DBConnection.getConnection()` is a method you wrote (or imported) that sets up and returns a `Connection` object and stores in conn.
- This `conn` is your **channel** to communicate with the database.

```java
    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
```

- **What:** The SQL query string.
- **Why:** To find the user row with matching email and password.
- **How:**
    - The `?` are placeholders — this prevents SQL Injection (someone trying to inject malicious commands).
    - Using placeholders means you’ll safely insert values later.

What is SQL Injection?

Let’s say you don’t use `PreparedStatement`. You do this:

```java
String sql = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
```

Now if a hacker types this as the email:

```java
' OR '1'='1
```

Your final SQL becomes:

```java
SELECT * FROM users WHERE email = '' OR '1'='1' AND password = ''
```

`'1'='1'` is always **true**, so the hacker logs in **without a password**.

That's called **SQL Injection**

How does `PreparedStatement` stop this?

```java
pst.setString(1, email);
pst.setString(2, password);

```

You are

**not injecting values into the query directly**

Even if someone tries to enter `' OR '1'='1`, it **won’t break your SQL** — it’ll just search for that weird string.

```java
    java.sql.PreparedStatement pst = conn.prepareStatement(sql);

```

- **What:** You prepare the SQL statement.
- **Why:** `PreparedStatement` allows you to set parameters safely.
- **How:**
    - `conn.prepareStatement(sql)` compiles your query but doesn’t run it yet.
    - It returns an object `pst` representing that query, ready to fill in the placeholders.

```java
    pst.setString(1, email);
    pst.setString(2, password);

```

- **What:** You set the values for the placeholders `?` in the SQL.
- **Why:** Prevent SQL Injection and ensure the query uses the exact inputs.
- **How:**
    - `setString(1, email)` means replace the first `?` with the email string.
    - `setString(2, password)` replaces the second `?` with the password string.
- **Note:** The indexing starts at 1, not 0. This is how JDBC works.

```java
    java.sql.ResultSet rs = pst.executeQuery();

```

Reasultset is an interfave used to declare a result set variable.

- **What:** Execute the SQL query and get results.
- If this query finds a row where both `email` and `password` match, then that row is **fetched** from the `users` table its pointer is  stored in the `ResultSet` `rs`.

| id | name | email | password | role |
| --- | --- | --- | --- | --- |
| 1 | John Doe | [admin@email.com](mailto:admin@email.com) | 1234 | admin |
- **Why:** You want to check if any record matches those credentials.
- **How:**
    - `executeQuery()` runs the SELECT command.
    - It returns a `ResultSet` object `rs` — a pointer to the rows found.
    - If no row, `rs` is empty.
    
    if rs has 1 row the pointer will always will be before that row which will be empty so if you will try to read rs.getString() it will give error as their is no row but if 1 row exist then the pinter will shift from before that row to that row using next(_) that gives true but if 0 rows ar fount then the pointer shifted will reach no row.
    

```java
    if(rs.next()) {

```

- Without `rs.next()`, the pointer is at position **before the first row**, and trying to get data like `rs.getString()` will throw an error — because there’s no row under the pointer.
- After `rs.next()` returns `true`, the pointer sits on row 1 — and you're allowed to access its columns using `getString("columnName")`, `getInt("id")`, etc.

```java
        String role = rs.getString("role");

```

- **What:** Retrieve the user’s role from the database row.
- **Why:** Role determines where to send the user — admin or warden dashboard.
- **How:**
    - `rs.getString("role")` fetches the column named `role` from the current row.

```java
        if(role.equalsIgnoreCase("admin")) {
            new AdminDashboard().setVisible(true);
        } else if(role.equalsIgnoreCase("warden")) {
            new WardenDashboard().setVisible(true);
        }

```

- **What:** Direct the user to their proper dashboard based on role.
- **Why:** Different users have different privileges and views.
- **How:**
    - If role is `admin`, open `AdminDashboard`.
    - Else if `warden`, open `WardenDashboard`.
    - `new ClassName().setVisible(true);` creates and shows that window.
- **Note:** `equalsIgnoreCase` ignores uppercase/lowercase differences.

Flow of Execution:  

## 🔐 **SIGNUP FLOW — Step-by-step Operation**

### 🎯 GOAL: Insert a new user into the database.

---

### ✅ 1. **User clicks the "Sign Up" button**

That triggers this event:

```java
private void btnSignupActionPerformed(ActionEvent evt)

```

This is your **command center** — when clicked, everything below is executed.

---

### ✅ 2. **Fetch user input**

```java
String username = txtUsername.getText();
String email = txtEmail.getText();
String password = new String(txtPassword.getPassword());
String role = comboBoxRole.getSelectedItem().toString(); // optional

```

> You collect the ammunition — the data needed to register a new soldier.
> 

---

### ✅ 3. **Establish database connection**

```java
Connection conn = DBConnection.getConnection();

```

> DBConnection.getConnection() returns a Connection object — a pipe to your MySQL battlefield.
> 

This pipe allows you to **send queries**, **receive results**, or **update tables**.

---

### ✅ 4. **Prepare SQL query**

```java
String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
PreparedStatement pst = conn.prepareStatement(sql);

```

You’re not writing raw SQL directly. You’re **preparing** it safely — this is your bulletproof vest against SQL Injection.

---

### ✅ 5. **Attach parameters**

```java
pst.setString(1, username);
pst.setString(2, email);
pst.setString(3, password);
pst.setString(4, role);

```

Each `?` in the SQL gets replaced with your actual data.

---

### ✅ 6. **Execute the insertion**

```java
int inserted = pst.executeUpdate();

```

If the number returned is `1`, insertion was **successful**.

---

### ✅ 7. **Redirect**

```java
new LoginPage().setVisible(true);
dispose(); // Close current Signup window

```

> Signup is done. You open the Login Page.
> 

---

---

## 🔑 **LOGIN FLOW — Step-by-step Operation**

### 🎯 GOAL: Authenticate a user and open the correct dashboard (admin/warden).

---

### ✅ 1. **User clicks "Login" button**

Triggers:

```java
private void btnLoginActionPerformed(ActionEvent evt)

```

This is the **control room** again.

---

### ✅ 2. **Get login credentials**

```java
String email = txtEmail.getText().trim();
String password = new String(txtPassword.getPassword());

```

---

### ✅ 3. **Get Connection**

```java
Connection conn = DBConnection.getConnection();

```

Just like in signup, you grab the **pipe to the database**.

---

### ✅ 4. **Prepare SQL query**

```java
String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
PreparedStatement pst = conn.prepareStatement(sql);

```

You’re saying:

> “Give me any soldier with this email and password.”
> 

---

### ✅ 5. **Attach login credentials**

```java
pst.setString(1, email);
pst.setString(2, password);

```

---

### ✅ 6. **Execute query**

```java
ResultSet rs = pst.executeQuery();

```

This **sends** the query to the MySQL server, and returns any **matching rows** as a **ResultSet**.

---

### ✅ 7. **Check if match found**

```java
if(rs.next()) {
    String role = rs.getString("role");

    if(role.equalsIgnoreCase("admin")) {
        new AdminDashboard().setVisible(true);
    } else if(role.equalsIgnoreCase("warden")) {
        new WardenDashboard().setVisible(true);
    }

    this.dispose(); // Close login window
} else {
    JOptionPane.showMessageDialog(this, "Invalid email or password.");
}

```

- `rs.next()` → checks if any row exists
- `rs.getString("role")` → fetches the role of that row
- Then based on role → dashboard is opened

---

## 🧠 FULL VISUAL FLOW:

```
User Fills Form
      ↓
Clicks Button
      ↓
Event Triggered (Signup/Login)
      ↓
Get Inputs
      ↓
Get Connection (conn)
      ↓
Prepare SQL → Attach Data (pst)
      ↓
If Signup → pst.executeUpdate()
If Login  → pst.executeQuery()
      ↓
If Login: Use rs.next() to check result
      ↓
Decide what to show next (dashboard or error)

```
Controller → it is a code or  function that tells what to do  when an event is 
triggered when user intracts with the gui. So sign up button click is event
  and the code inside the even listioner is the controller.