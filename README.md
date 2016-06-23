Ch-039.Chernivtsi Hospitals
=====
Configure SSL(HTTPS protocol)
---
Small topic for configuration ssl in local tomcat server.
Ch-039 group used at least tomcat servaer version 8.
**Java (annotation) spring configuration.**

Steps
---

**1 step.**
Enable ssl in spring security.
@Override
	protected void configure(HttpSecurity http) throws Exception {
	.....
	**.and().requiresChannel().anyRequest().requiresSecure();**
	}
	
	**2 step.**
	Create and connect to tomcat security certificate.
	Fisrt, open the terminal on your computer and type:
**Windows:**
```html
cd %JAVA_HOME%/bin
```

**Linux or Mac OS:**
```html
cd $JAVA_HOME/bin
```
The $JAVA_HOME on Mac is located on **“/System/Library/Frameworks/JavaVM.framework/Versions/{your java version}/Home/”**

You will change the current directory to the directory Java is installed on your computer.
Inside the Java Home directory, cd to the bin folder.
Inside the bin folder there is a file named keytool.
This guy is responsible for generating the keystore file for us.

Next, type on the terminal:
```html
keytool -genkey -alias tomcat -keyalg RSA
```
When you type the command above, it will ask you some questions. First, it will ask you to create a password (My password is “password“):
```html
loiane:bin loiane$ keytool -genkey -alias tomcat -keyalg RSA
Enter keystore password:  password
Re-enter new password: password
What is your first and last name?
  [Unknown]:  Loiane Groner
What is the name of your organizational unit?
  [Unknown]:  home
What is the name of your organization?
  [Unknown]:  home
What is the name of your City or Locality?
  [Unknown]:  Sao Paulo
What is the name of your State or Province?
  [Unknown]:  SP
What is the two-letter country code for this unit?
  [Unknown]:  BR
Is CN=Loiane Groner, OU=home, O=home, L=Sao Paulo, ST=SP, C=BR correct?
  [no]:  yes
 
Enter key password for
    (RETURN if same as keystore password):  password
Re-enter new password: password
```
It will create a .keystore file on your user home directory. On Windows, it will be on: C:Documents and Settings[username]; on Mac it will be on /Users/[username] and on Linux will be on /home/[username].

	**3 step.**
	Configuring Tomcat for using the keystore file – SSL config
	
	Open your Tomcat installation directory and open the conf folder. Inside this folder, you will find the **server.xml** file. Open it.

Find the following declaration:
```html
<!--
<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
    maxThreads="150" scheme="https" secure="true"
    clientAuth="false" sslProtocol="TLS" />
-->
```
Uncomment it and modify it to look like the following:
```html
Connector SSLEnabled="true" acceptCount="100" clientAuth="false"
    disableUploadTimeout="true" enableLookups="false" maxThreads="25"
    port="8443" keystoreFile="/Users/loiane/.keystore" keystorePass="password"
    protocol="org.apache.coyote.http11.Http11NioProtocol" scheme="https"
    secure="true" sslProtocol="TLS" />
 ```
**4 step.**
Let’s test it!
Start tomcat service and try to access https://localhost:8443. You will see Tomcat’s local home page.
	
	
**Сonfiguration information was taken from the site https://dzone.com/**
	
Authors
---
Copyright(c) 2016 Dream-Tema CH-039

	
	
	
