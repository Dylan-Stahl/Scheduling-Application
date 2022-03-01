Application Title - Scheduling Application

Purpose - A GUI scheduling application designed for a consulting company that has locations across the world. 
The company has provided a MySQL database that the application must create, read, update, and delete data from.

Author - Dylan Stahl

Contact Information - dylangstahl@gmail.com

IDE and Version - IntelliJ IDEA 2021.1.1

Compatible Operating Systems - As of 3/1/22 the applications supports Windows operating systems. A bug exists with MacOS due to Java JDK and JavaFX versions. An updated JDK version is expected to support MacOS.

JDK version - Java SE 11.0.11 and compatible with JavaFX-SDK-11.0.2

How to run the program - When the zip file has been downloaded, extract the file. Now you can open the project by going
into the IntelliJ IDE and opening the source folder. Create a path variable in IntelliJ settings that locates the
javafx-sdk-11.02/lib file on your computer. In the run configurations in IntelliJ add VM options as the following:
--module-path ${Path Variable that you named} --add-modules javafx.fxml,javafx.controls,javafx.graphics
Then, edit the run configurations so that the main method is Model.Main and select java 11 version 11.0.11.
Save the run configurations and run the program.

Additional Report - From the main menu click on appointment filters. There is a radio button option titled "Past Appointments".
That report will show all appointments that have ended before the current LocalDateTime.

MySQL Connector driver version - mysql-connector-java-8.0.27

Lambda Expressions - Two lambda expressions using the Predicate Interface can be found in the Utilities package,
DBConnection class under the login method. A third lambda expression (a BooleanSupplier) is used in
the mainMenuController initialize() method.



 
