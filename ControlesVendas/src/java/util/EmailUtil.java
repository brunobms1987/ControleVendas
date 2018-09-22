package util;

import com.sun.mail.util.MailSSLSocketFactory;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {
    
    private String destinatario;

    private String conteudo;

    private String assunto;

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public EmailUtil(String destinatario, String conteudo, String assunto) {
        this.destinatario = destinatario;
        this.conteudo = conteudo;
        this.assunto = assunto;
    }

    public EmailUtil() {
    }

    public boolean enviarSSL() {
        boolean resultado = false;
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");//do java
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//do java
            properties.put("mail.smtp.auth", "true");//do server de email
            properties.put("mail.smtp.port", "465");//do server de email
            properties.put("mail.smtp.ssl.trust", "*");//do server de email
            properties.put("mail.debug", "true");

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("brunobms1987@gmail.com", "2506_car");
                            //return new PasswordAuthentication("testeaulas2016@gmail.com", ")(*adm123");
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("testeaulas2016@email.com"));//quem envia

            Address[] destinatarios = InternetAddress
                    .parse(this.destinatario);

            message.setRecipients(Message.RecipientType.TO, destinatarios);
            
            message.setSubject(this.assunto);
            
            message.setContent(this.conteudo, "text/html; charset=utf-8");
            
            Transport.send(message);
            
            resultado = true;

        } catch (Exception e) {
            System.err.println(e.toString());
        }
        finally{
            return resultado;
        }
    }
}
