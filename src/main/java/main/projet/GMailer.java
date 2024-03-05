package main.projet;


import entity.Account;
import services.AccountService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Random;

public class GMailer {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "firas.guesmi93806411@gmail.com";
    private static final String EMAIL_PASSWORD = "txwr qbfo oubc ccpg";
    private static final Session session = createSession();


    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });
    }

    public static void EmailTest(String recipientEmail) {
        try {
            // Génération d'un code aléatoire
            String randomCode = generateRandomCode();


            Account.setCodemail(randomCode);

            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Mot de passe oublié");

            // Création du contenu HTML avec le code aléatoire
            // Création du contenu HTML avec le code aléatoire et ajout de CSS
            // Création du contenu HTML avec le code aléatoire et ajout de CSS
            // Création du contenu HTML avec le code aléatoire et ajout de CSS
            // Création du contenu HTML avec le code aléatoire et ajout de CSS
            // Création du contenu HTML avec le code aléatoire et ajout de CSS
            String emailContentWithSignature = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: 'Arial', sans-serif; background-color: #3498db; margin: 0; padding: 0; color: #ecf0f1; }" +
                    ".container { max-width: 600px; margin: 0 auto; background-color: #2980b9; box-shadow: 0 0 20px rgba(0, 0, 0, 0.3); border-radius: 10px; overflow: hidden; }" +
                    ".header { background-color: #1f618d; color: #ecf0f1; padding: 30px; text-align: center; }" +
                    ".content { padding: 30px; }" +
                    "p { color: #ecf0f1; font-size: 18px; line-height: 1.6; }" +
                    "strong { color: #2980b9; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<div class='header'>" +
                    "<h2 style='margin: 0;'>Mot de passe oublié</h2>" +
                    "<p style='margin: 10px 0 0; font-size: 14px;'>Une demande de réinitialisation de mot de passe a été effectuée. Utilisez le code ci-dessous pour récupérer l'accès à votre compte.</p>" +
                    "</div>" +
                    "<div class='content'>" +
                    "<p style='margin: 0;'>Voici le code : <strong>" + randomCode + "</strong></p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";



            // Ajout du contenu au message
            Multipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(emailContentWithSignature, "text/html");
            multipart.addBodyPart(textPart);
            message.setContent(multipart);

            // Envoi du message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }

    private static String generateRandomCode() {
        // Génération d'un code aléatoire de 6 chiffres
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
