Application Title - Scheduling Application

Purpose - A GUI scheduling application designed for a consulting company that has locations across the world. 
The company has provided a MySQL database that the application must create, read, update, and delete data from.

Author - Dylan Stahl

Contact Information - dstahl8@wgu.edu

Student Application Version - QAM1 TASK 1: JAVA APPLICATION DEVELOPMENT, Course started May 6th, 2021.

IDE and Version- IntelliJ IDEA 2021.1.1

JDK version - Java SE 11.0.11 and compatible with JavaFX-SDK-11.0.2

How to run the program - When the zip file has been downloaded, extract the file. Now you can open the project by going
into the IntelliJ IDE and opening the source folder. Create a path variable in IntelliJ settings that locates the
javafx-sdk-11.02/lib file on your computer. In the run configurations in IntelliJ add VM options as the following:
--module-path ${Path Variable that you named} --add-modules javafx.fxml,javafx.controls,javafx.graphics
Then, edit the run configurations so that the main method is Model.Main and select java 11 version 11.0.11.
Save the run configurations and run the program.

Additional Report - From the main menu click on appointment filters. There is a radio button option titled "Past Appointments".
That report will show all appointments that have ended before the current LocalDateTime.

MySQL Connector driver version - mysql-connector-java-8.0.22

Lambda Expressions - Two lambda expressions using the Predicate Interface can be found in the Utilities package,
DBConnection class under the login method. A third lambda expression (a BooleanSupplier) is used in
the mainMenuController initialize() method.


 