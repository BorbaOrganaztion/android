package com.example.games4all;


public class User {
    private String userId, userName, userEmail, userCity, userPhone, userCardNumber, userCardName, userCardExp, userCardCVC, userBankingNumber, userBankingAccount, userBalance;
    private double userSellerRate;


    public User(){}



    public User(String userId, String userName, String userEmail ) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public void setUserSellerRate(double userSellerRate) {
        this.userSellerRate = userSellerRate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserCardNumber(String userCardNumber) {
        this.userCardNumber = userCardNumber;
    }

    public void setUserCardName(String userCardName) {
        this.userCardName = userCardName;
    }

    public void setUserCardExp(String userCardExp) {
        this.userCardExp = userCardExp;
    }

    public void setUserCardCVC(String userCardCVC) {
        this.userCardCVC = userCardCVC;
    }

    public void setUserBankingNumber(String userBankingNumber) {
        this.userBankingNumber = userBankingNumber;
    }

    public void setUserBankingAccount(String userBankingAccount) {
        this.userBankingAccount = userBankingAccount;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserCardNumber() {
        return userCardNumber;
    }

    public String getUserCardName() {
        return userCardName;
    }

    public String getUserCardExp() {
        return userCardExp;
    }

    public String getUserCardCVC() {
        return userCardCVC;
    }

    public String getUserBankingNumber() {
        return userBankingNumber;
    }

    public String getUserBankingAccount() {
        return userBankingAccount;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public double getUserSellerRate() {
        return userSellerRate;
    }
}
