package ru.sberbank.coursework.demo.mail;

public class Send {

    private static Sender tlsSender = new Sender("tttech32@gmail.com", "P@ssw0rd32");

    public  static void SendEmail(String email, String subject, String text){
        tlsSender.send(subject, text,"tttech32@gmail.com", email);
    }

}
