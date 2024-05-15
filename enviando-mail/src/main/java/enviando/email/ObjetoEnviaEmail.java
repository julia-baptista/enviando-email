                                                                          package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
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
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {
	
	private String userName = "juliabaptistadev@gmail.com";
	private String senha = "uuflmtmerjvkiixe";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	
	
	
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente,
			String assuntoEmail, String textoEmail) {
		super();
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}




	public void enviarEmail(boolean envioHtml) throws Exception {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*"); /*Segurança*/
		properties.put("mail.smtp.auth", true); /*Autorização*/
		properties.put("mail.smtp.starttls.enable", "true"); /*Autenticação*/
		properties.put("mail.smtp.host", "smtp.gmail.com"); /*Servidor gmail Google*/
		properties.put("mail.smtp.port", "465"); /*Porta do servidor*/
		properties.put("mail.smtp.socketFactory.port", "465"); /*Especifica a porta a ser conectada pelo socket*/
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");/*Classe socket de conexão ao SMTP do java*/
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});
		
		Address[] toUser=  InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /*Quem está enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser); /*Email de destino*/
		message.setSubject(assuntoEmail); /*Assunto do email*/
		
		
		if(envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}
				
		Transport.send(message);
		
		System.out.println("Sent message successfully....");		
		
	}
	
	public void enviarEmailAnexo(boolean envioHtml) throws Exception {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*"); /*Segurança*/
		properties.put("mail.smtp.auth", true); /*Autorização*/
		properties.put("mail.smtp.starttls.enable", "true"); /*Autenticação*/
		properties.put("mail.smtp.host", "smtp.gmail.com"); /*Servidor gmail Google*/
		properties.put("mail.smtp.port", "465"); /*Porta do servidor*/
		properties.put("mail.smtp.socketFactory.port", "465"); /*Especifica a porta a ser conectada pelo socket*/
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");/*Classe socket de conexão ao SMTP do java*/
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});
		
		Address[] toUser=  InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /*Quem está enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser); /*Email de destino*/
		message.setSubject(assuntoEmail); /*Assunto do email*/
		
		
		/*Parte 1 do email é texto e descrição do email*/
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		
		
		if(envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}
		
		
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePDF()); /*Certificado*/
		arquivos.add(simuladorDePDF()); /*Nota Fiscal*/
		arquivos.add(simuladorDePDF()); /*Documento de texto*/
		arquivos.add(simuladorDePDF()); /*Imagem*/
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);
		
		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {
			
			/*Parte 2 do email que são os anexos em pdf*/
			MimeBodyPart anexoEmail = new MimeBodyPart();
			
			/*Onde é passado o simuladorDePDFvocê passa o seu arquivo gravado no banco de dados*/
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail" + index + ".pdf");
						
			// Multipart multipart = new MimeMultipart();
			// multipart.addBodyPart(corpoEmail);
			multipart.addBodyPart(anexoEmail);
			index++;
			
		}
		
		
		
		
		
		
		message.setContent(multipart);
				
		Transport.send(message);
		
		System.out.println("Sent message successfully....");		
		
	}
	
	/**
	 * Esse método simula o PDF ou qualquer arquivo que possa ser enviado por anexo no email.
	 * Você pode pegar o aruivo do seu banco de dados base64, byte[], Stream de Arquivos.
	 * Pode estar em um banco de dados, ou em uma pasta.
	 * 
	 * Retorna um PDF em Branco com o texto do Paragrafo de exemplo.
	 **/
	private FileInputStream simuladorDePDF() throws Exception{
		
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do PDF anexo com Java Mail, esse texto é do PDF"));
		document.close();
		
		return new FileInputStream(file);
		
	}
	
	
	/*Olhe as configurações smtp do seu email*/
	


}
