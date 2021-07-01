package ru.sberbank.coursework.demo.mail;

public class Send {

    private static Sender tlsSender = new Sender("techoutbox32@gmail.com", "ithpzsmq32");
    private static String email = "shadoba@ya.ru";

    public  static void SendEmail(String email){
        tlsSender.send("Пароль для входа на портал кредитных предложений", "Ваш пароль для входа на платформу: client","techoutbox32@gmail.com", email);
    }

    public static void main(String[] args) {
        SendEmail(email);
    }

}
