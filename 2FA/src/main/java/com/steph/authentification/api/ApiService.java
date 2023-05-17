package com.steph.authentification.api;



import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ApiService {

	public ApiService() {}
	
	public void sendmail(String addresse, String message, String titre) throws AddressException, MessagingException{
		//définition des propriétés
		
		  Properties props = new Properties(); 
		  props.put("mail.smtp.auth", "true"); 
		  props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		  props.put("mail.smtp.host", "smtp.gmail.com"); 
		  props.put("mail.smtp.port", "587");
		  
		  //initiation de la session
		  //ici nous allons utilisé l'authentification par mot de passe de Gmail
		  
		  Session session = Session.getInstance(props, new javax.mail.Authenticator() { 
			   protected PasswordAuthentication getPasswordAuthentication() { 
			    return new PasswordAuthentication("bocobi237@gmail.com", "kjty aihb wrvs cwyn"); 
			   } 
	     });
		  
		  //ici on definit la provenance du mail
		  
		  Message msg = new MimeMessage(session); 
		  msg.setFrom(new InternetAddress("BOCOBI", false));
		  
		  // on renseigne le recepteur du message
		  
		  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresse));
		  
		  msg.setSubject(titre);
		  
		  msg.setContent("ce message contient le code de validation pour l'application", "text/html");
		  
		  msg.setSentDate(new Date());
		  
		  MimeBodyPart messageBodyPart = new MimeBodyPart(); 
		  messageBodyPart.setContent(message, "text/html"); 
		 
		  Multipart multipart = new MimeMultipart(); 
		  multipart.addBodyPart(messageBodyPart); 
		  //MimeBodyPart attachPart = new MimeBodyPart();
		  
		  //attachPart.attachFile("/var/tmp/image19.png"); 
		  //multipart.addBodyPart(attachPart); 
		  msg.setContent(multipart); 
		  Transport.send(msg); }
}

