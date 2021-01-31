# E-Banking System

## A application to replace traditional banking systems

This is an online banking system where users will be allowed to do various forms of banking that were traditionally done in person.
The banking system will allow the user to perform various banking functions.

Functions in the Current Implementation Include:
- create and manage bank account(s) (with a unique username and password)
- Withdraw / deposit money
- Create different bank accounts
- viewing deposit history 
- viewing recent transactions and spending habits


This application is for users to maintain social distancing to reduce risk for spreading the virus causing the current pandemic.
This application will be an efficient alternative for users who still wish to perform regular banking duties without the need to expose themselves to the virus.
Additionally, this will be an effective method to reduce the number of staff typically required to run a bank.


## User Stories
- As a user, I wish to be able to create an account with my own name and password
- As a user, I wish to be able to create different bank accounts (USD or chequing)
- As a user, I wish to be able to withdraw and deposit money
- As a user, I wish to access my transaction histories.
- As a user, I want to be able to be given the option to save all UserAccounts to file when I quit the program (only include user bank accounts and balance and not transaction)
- As a user, I want to be able to be able to load my UserAccounts list when I run the program
- As a user, I want to be able to add multiple bank accounts to my account
- As a user, I want to be able to load and save all banking events that occurred during the life of the application




## Phase 4: Task 2
- A map interface was implemented in this program.
A map object called panelMap was used in the UserAccountUI class file. The key for the map is the current UI panel where the value of the map
represents the previous user panel. The map is initialized in a method called by the constructor called createPanelMap() which initializes panelMap as a HasMap.
panelMap was used in the method returnToPreviousPanel() which is used to determine the previous panel using the current panel as the key and set the previous panel to be visible.

## Phase 4 : Task 3
- There were a large amount of coupling with the UserAccountDatabase class that can be refactored to decrease coupling
- Make UserAccountDatabase extend Obserable class and make SaveAndLoad, SaveAndLoadGUI, MainGUI, and Workroom all extend Observer to reduce the coupling
- Adding some exceptions for BankAccount implementation to make my program more robust for undesired user inputs (i.e. for depositing and withdrawing)
- Make SaveAndLoad extend Observable  so there is less coupling between SaveAndLoadGUI and MainGUI
- Remove the association between MainGui and MainInterfaceUI and make MainInterfaceUI part of the list of GUI found in MainGui

