README

I.
Title:
Scheduling App
Application version 1.1
08 May 2021

II.
Author: 
Adam Brigham


III.
Purpose:
The purpose of Scheduling App is to provide an efficient and user-friendly way for users connect and interract with the business database to establish and maintain customer business relationships.  

The functions within Scheduling App allow the user to implement the following:
1. Create, delete, and modify appointments
2. Check reports regarding customers, contacts, and their respective appointments
3. Create, delete, and modify customers
4. Ensure appointments comply with business rules regarding business hours and scheduling conflicts
5. Prevent non-validated users from utilizing the database, maintaining data integrity

IV.
IDE information:
IntelliJ IDEA 2020.3.3 (Community Edition)
Build #IC-203.7717.56, built on March 15, 2021
Runtime version: 11.0.10+8-b1145.96 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10 10.0
GC: ParNew, ConcurrentMarkSweep
Memory: 1970M
Cores: 12
Non-Bundled Plugins: com.intellij.javafx, com.github.vljubovic.javaFxHelper, org.jetbrains.kotlin

V.
Instructions for use:
Refer to https://www.jetbrains.com/help/idea/javafx.html#download-javafx for instructions on setting up IntelliJ for JavaFX functionallity with this application
Open the main class from the src package and Run 'Main' (shift+F10)

Login Screen:
Enter username and password in appropriate textfields and click OK. If successfully validated, the application notifies the user of impending appointments and opens a main menu.  All login attempts are tracked per business rules.

Main Menu:
Click the appropropriate button to navigate to desired Application functionality.

Customers Main:
All customers are viewed in the tableView. A customer may be selected from the view and updated or deleted. Note that these actions are permenent. Varify that there are no appointments with a given customer prior to deletion, or the appointment will be deleted simultaneously. A new customer may be added by clicking the add customer button.

Add Customers:
Enter all fields into the appropriate textFields and comboBoxes.  All fields are required. Click enter to save the new customer, or cancel to discard changes and return to main menu.

Update Customers:
Fields will be automatically populated from the customer selected from the customers main screen.  Make appropriate changes to textFields and comboBoxes. Click save to confirm changes, or cancel to return.

Appointments Main:
All appointments are viewed in the tableView by default. To filter appointments by month or by week, select the appropriate radio button and then select the desired week or month in the datePicker.  Only the appointments that are in the selected week or month will be displayed in the tableView.  Appointments may be added, deleted, or updated similarly to customers.

Add Appointments:
Enter all fields into the appropriate textFields and comboBoxes.  To set the appointment date and time, select the date in the datePicker, then choose the start time from the comboBox.  Select the appointment duration using the slider to choose the minutes desired. Save the appointment or cancel changes.  Appointments set outside of business hours (eastern standard time) will not be saved, nor will appointments that overlap existing appointments with a given customer.

Update Appointments:
Fields will be automatically populated from the appointment selected from the appointments main screen.  Make all appropriate changes, noting scheduling conflicts as necessary.  Click save to confirm changes, or cancel to return.

Reports Main:
Click the appropropriate button to navigate to desired report.

Appointments by Customer:
Select the customer using the comboBox. By default, the number of all appointments without filtration will be selected.  Select to filter by month, by type, or all using the appropriate radio button.  This will enable the relevant comboBox to choose the type or month.  Select the type or month and click OK. The number of appointments for the selection will be output in the blue box.  Click Main Menu to return.

Schedule By Contact:
Select the contact using the comboBox.  All appointments for the selected contact will be displayed in the tableView. Click Main Menu to return.

Customer Statistics:
This report provides an index for the level of time commitment for a given customer.  A customer with many appointments scheduled and a long average duration requires time adequate time commitment from the business staff. Select the customer from the comboBox and click OK.  The number of scheduled appointments and the average appointment duration is displayed in the blue box.

