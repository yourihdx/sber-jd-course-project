package ru.sberbank.coursework.demo.mail;

public class Send {

    private static Sender tlsSender = new Sender("techoutbox32@gmail.com", "ithpzsmq32");

    public  static void SendEmail(String email, String subject, String text){
        tlsSender.send(subject, text,"techoutbox32@gmail.com", email);
    }

}
