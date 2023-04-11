package reuse_code;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Send_email {

static HashMap<String, String> Map= new HashMap<String, String>();
	
	static void read_excel() throws IOException
	{
//		Reading Excel sheet 
		String OldFile_Path="D:\\eclipse-workspace\\selenium\\Credentials.xlsx";
		FileInputStream i_stream= new FileInputStream(OldFile_Path);
		Workbook Old_Wbook= new XSSFWorkbook(i_stream);
		Sheet sheet= Old_Wbook.getSheet("Sheet1");
		
		for(int r=0; r<=sheet.getLastRowNum();r++)
		{
			Row Old_row= sheet.getRow(r);
			for(int c=0;c<Old_row.getLastCellNum();c++)
			   {
					try {
						Cell Old_cell= Old_row.getCell(c);
						Map.put(Old_row.getCell(0).getStringCellValue(), Old_row.getCell(1).getStringCellValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	   }
//		System.out.println(Map);
	}
	
	 static void send_attachment_email() throws MessagingException, IOException
	 {
		 
		 read_excel();
		 
//		 variable for gmail
		 String host="smtp.gmail.com";
//		 get the system properties
		 Properties properties= System.getProperties();
//		Setting important information to properties object
		 
//		 host set
		 properties.put("mail.smtp.host", host);
		 properties.put("mail.smtp.port", "465");
		 properties.put("mail.smtp.ssl.enable", "true");
		 properties.put("mail.smtp.auth", "true");
		 
//		Step 1: To get the session object
		 Session session= Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Map.get("U_name"),Map.get("password"));
			}
		});
		 
//		Step 2:
		MimeMessage m= new MimeMessage(session);
		
//		from email
		try {
			m.setFrom(Map.get("from"));
			
//		adding recipient to message
//			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.addRecipients(Message.RecipientType.TO, InternetAddress.parse(Map.get("to")));
			System.out.println(Map.get("cc"));
			if(Map.get("cc")!=null)
			{
				m.addRecipients(Message.RecipientType.CC, InternetAddress.parse(Map.get("cc")));
			}	
//		adding subject to message
			m.setSubject(Map.get("subject"));
		if(Map.get("Attachment").equalsIgnoreCase("Yes"))
		{
//		Attachment
		    MimeMultipart mulitpart=  new MimeMultipart();
//		    for text
		    MimeBodyPart textmime= new MimeBodyPart();
//		    for attachment
		    MimeBodyPart filemime= new MimeBodyPart();
		    
		    try {
				textmime.setText(Map.get("message"));
				File file= new File(Map.get("path"));
				filemime.attachFile(file);
				
				mulitpart.addBodyPart(textmime);
				mulitpart.addBodyPart(filemime);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    m.setContent(mulitpart);
		}else 
		{
//			adding text to message
				m.setText(Map.get("message"));
			
		}
//		Step 3: send message using Transport class
			Transport.send(m);
			
			System.out.println("Message sent successfully......");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	 }

	public static void main(String[] args) {
		
	}

}
