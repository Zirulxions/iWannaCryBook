package utility;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class Email {
	
	Properties props = new Properties();

	// host 
	props.setProperty("mail.smtp.host", "smtp.gmail.com");

	// TLS 
	props.setProperty("mail.smtp.starttls.enable", "true");

	// Puerto
	props.setProperty("mail.smtp.port","587");

	//  usuario
	props.setProperty("mail.smtp.user", "ejemplo@gmail.com");

	// Si requiere o no usuario y password para conectarse.
	props.setProperty("mail.smtp.auth", "true");

	Session session = Session.getDefaultInstance(props);

	// Para obtener un log de salida más extenso
	session.setDebug(true);
	
	
	
	
	MimeMessage message = new MimeMessage(session);

	// Quien envia 
	message.setFrom(new InternetAddress("26053780r@gmail.com"));

	// A quien va dirigido
	message.addRecipient(Message.RecipientType.TO, new InternetAddress("destinatario@dominio.com"));

	message.setSubject("iWannaCryBook");
	message.setText("Your account have been banned");
	
	
	
	
	Transport t = session.getTransport("smtp");

	// Aqui usuario y password de gmail
	t.connect("chuidiang@gmail.com","la password");
	t.sendMessage(message,message.getAllRecipients());
	t.close();
}
	
