import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//Mail to be send by java source Stack overflow 
//https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail

public class P13 {

    private static String USER_NAME = "abckbc321";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "abckbc@123"; // GMail password
    private static String RECIPIENT = "abhinandanmay11@gmail.com";

    public static void main(String[] args) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Java send mail example";
        String body = "Welcome to JavaMail!";

        sendFromGMail(from, pass, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
                System.out.println("The value of toAddress is " + toAddress[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
			System.out.println("The subject is " + subject);
            message.setSubject(subject);
            message.setText(body);
            System.out.println("Body of mail is " + body);
            Transport transport = session.getTransport("smtp");
             System.out.println("After transport");
            transport.connect(host, from, pass);
            System.out.println("After Connect");
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("After sendMessage");
            transport.close();
            System.out.println("After Close");
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
