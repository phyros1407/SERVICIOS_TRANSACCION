package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.internet.MimeMultipart;

import beans.BoletaBean;

public class EnviarBoleta extends Object {


	final String username ="nano_sport_test@hotmail.com";
	final String password ="nanosport1234";
	
	
 
    MimeMultipart multiParte = new MimeMultipart("related");
	Properties props = new Properties();
	
	private void init() {
 
		props = new Properties();
		 
		props.put("mail.smtp.host", "smtp-mail.outlook.com");
		props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port","587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.mail.sender", username);
		props.put("mail.smtp,password",password);
		props.put("mail.smtp.user", username);
		
        
	}
 
	public void sendEmail(String mensaje,String correo) throws Exception{
 
			init();
			
            
     
            addContent(mensaje);
     
            /**
            //añadir imagenes
            addCID("cidqr", "images_email/portada.jpg");
            */
           /*
            addAttach(""); //ruta donde se encuentra el fichero que queremos adjuntar.
            */
            
            // enviar el correo MULTIPART
            sendMultipart(correo);
        
		
	}

	public void addContent(String htmlText) throws Exception
    {
        // first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlText, "text/html");
        // add it
        this.multiParte.addBodyPart(messageBodyPart);
    }
	
	public void addCID(String cidname,String pathname) throws Exception
    {
        DataSource fds = new FileDataSource(pathname);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID","<"+cidname+">");
        this.multiParte.addBodyPart(messageBodyPart);
    }
	
	public void addAttach(String pathname) throws Exception
    {
        File file = new File(pathname);
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource ds = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(ds));
        messageBodyPart.setFileName(file.getName());
        messageBodyPart.setDisposition(Part.ATTACHMENT);
        this.multiParte.addBodyPart(messageBodyPart);
    }
	
	public void sendMultipart(String correo) throws Exception
    {
        Session mailSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport("smtp");
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("Nano-Sport-Shop : ¡Gracias por su compra!");
        message.setFrom(new InternetAddress((String)props.get("mail.smtp.mail.sender")));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(correo));
        // put everything together
        message.setContent(multiParte);
        
        transport.connect(username, password);
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
	
	public  String armarEmail(ArrayList<BoletaBean> boleta){
		
		
		String tabla = "";
		double subtotal = 0;
		
		for(int i = 0;i<boleta.size();i++){
			
				tabla = tabla + ( "<tr>"
						+ "<td>"+boleta.get(i).getCod_pro()+"</td>"
						+ "<td>"+boleta.get(i).getNom_pro()+"</td>"
						+ "<td>S/. "+Math.round((boleta.get(i).getPre()*1.19) * 100.00) / 100.00+"</td>"
						+ "<td>S/. "+Math.round(((boleta.get(i).getPre()*1.19)*boleta.get(i).getDescuento()) * 100.00) / 100.00+"</td>"
						+ "<td>"+boleta.get(i).getCan()+"</td>"
						+ "<td>S/. "+Math.round((boleta.get(i).getImporte()*1.19) * 100.00) / 100.00+"</td>"
						+ "</tr>");
		
				subtotal = subtotal + boleta.get(i).getImporte();
		}
				
		String datosCliente = "";
		if(boleta.get(0).getFec_can()==null){
			
			if(boleta.get(0).getIde().equalsIgnoreCase("P")){
				
				if(boleta.get(0).getTip_com().equalsIgnoreCase("BOLETA")){
			
					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label> POR CANCELAR </label><br>                                                                         "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      "+
							"			    			<table align='right' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2' style='text-align: left;padding-bottom: 15px;'>                      "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos de Envio</legend>                                                                                                                               "+
							"						    				<label>DIRECCION : </label><label>"+boleta.get(0).getDir()+"</label><br>                                                                                      "+
							"						    				<label>"+boleta.get(0).getDis()+" - "+boleta.get(0).getPro()+" - "+boleta.get(0).getDep()+"</label><br>                                                       "+
							"						    				<label>TIPO DE ENTREGA : </label><label>"+boleta.get(0).getTip_ent()+"</label><br>                                                                            "+
							"						    				<label>FECHA DE ENTREGA DE PEDIDO : </label><label>"+boleta.get(0).getFec_ent()+"</label>                                                                     "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";
				}else{
					

					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label> POR CANCELAR </label><br>                                                                           "+
							"						    				<label>RUC : </label><label>"+boleta.get(0).getRuc_cli()+"</label><br>                                                                           "+
							"						    				<label>RAZON SOCIAL : </label><label>"+boleta.get(0).getRaz_soc()+"</label><br>                                                                           "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      "+
							"			    			<table align='right' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2' style='text-align: left;padding-bottom: 15px;'>                      "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos de Envio</legend>                                                                                                                               "+
							"						    				<label>DIRECCION : </label><label>"+boleta.get(0).getDir()+"</label><br>                                                                                      "+
							"						    				<label>"+boleta.get(0).getDis()+" - "+boleta.get(0).getPro()+" - "+boleta.get(0).getDep()+"</label><br>                                                       "+
							"						    				<label>TIPO DE ENTREGA : </label><label>"+boleta.get(0).getTip_ent()+"</label><br>                                                                            "+
							"						    				<label>FECHA DE ENTREGA DE PEDIDO : </label><label>"+boleta.get(0).getFec_ent()+"</label>                                                                     "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";
					
				}
				
				
				
			}else{
				
				if(boleta.get(0).getTip_com().equalsIgnoreCase("BOLETA")){
					
					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label>"+boleta.get(0).getFec_can()+"</label><br>                                                                           "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";
							                                                                                                                                                        
				}else{
					

					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label>"+boleta.get(0).getFec_can()+"</label><br>                                                                           "+
							"						    				<label>RUC : </label><label>"+boleta.get(0).getRuc_cli()+"</label><br>                                                                           "+
							"						    				<label>RAZON SOCIAL : </label><label>"+boleta.get(0).getRaz_soc()+"</label><br>                                                                           "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";

					
				}
				
				
			}
			
			
		}else{
			
			if(boleta.get(0).getIde().equalsIgnoreCase("P")){
				
				if(boleta.get(0).getTip_com().equalsIgnoreCase("BOLETA")){
			
					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label>"+boleta.get(0).getFec_can()+"</label><br>                                                                         "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      "+
							"			    			<table align='right' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2' style='text-align: left;padding-bottom: 15px;'>                      "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos de Envio</legend>                                                                                                                               "+
							"						    				<label>DIRECCION : </label><label>"+boleta.get(0).getDir()+"</label><br>                                                                                      "+
							"						    				<label>"+boleta.get(0).getDis()+" - "+boleta.get(0).getPro()+" - "+boleta.get(0).getDep()+"</label><br>                                                       "+
							"						    				<label>TIPO DE ENTREGA : </label><label>"+boleta.get(0).getTip_ent()+"</label><br>                                                                            "+
							"						    				<label>FECHA DE ENTREGA DE PEDIDO : </label><label>"+boleta.get(0).getFec_ent()+"</label>                                                                     "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";
				}else{
					

					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label>"+boleta.get(0).getFec_can()+"</label><br>                                                                           "+
							"						    				<label>RUC : </label><label>"+boleta.get(0).getRuc_cli()+"</label><br>                                                                           "+
							"						    				<label>RAZON SOCIAL : </label><label>"+boleta.get(0).getRaz_soc()+"</label><br>                                                                           "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      "+
							"			    			<table align='right' width='300px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2' style='text-align: left;padding-bottom: 15px;'>                      "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos de Envio</legend>                                                                                                                               "+
							"						    				<label>DIRECCION : </label><label>"+boleta.get(0).getDir()+"</label><br>                                                                                      "+
							"						    				<label>"+boleta.get(0).getDis()+" - "+boleta.get(0).getPro()+" - "+boleta.get(0).getDep()+"</label><br>                                                       "+
							"						    				<label>TIPO DE ENTREGA : </label><label>"+boleta.get(0).getTip_ent()+"</label><br>                                                                            "+
							"						    				<label>FECHA DE ENTREGA DE PEDIDO : </label><label>"+boleta.get(0).getFec_ent()+"</label>                                                                     "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";
					
				}
				
				
				
			}else{
				
				if(boleta.get(0).getTip_com().equalsIgnoreCase("BOLETA")){
					
					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label>"+boleta.get(0).getFec_can()+"</label><br>                                                                           "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";
							                                                                                                                                                        
				}else{
					

					 datosCliente = 
							
							"			    			<table align='left' style='text-align: left;padding-bottom: 15px;' width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table-2'>                       "+
							"			    				<tr>                                                                                                                                                                      "+
							"			    					<td>                                                                                                                                                                  "+
							"			    						<fieldset style='border:3px solid;border-radius: 8px;'>                                                                                                           "+
							"						    				<legend>Datos Generales</legend>                                                                                                                              "+
							"						    				<label>CLIENTE : </label><label>"+boleta.get(0).getNom_cli()+"</label><br>                                                                                    "+
							"						    				<label>FECHA DE EMISION : </label><label>"+boleta.get(0).getFec_emi()+"</label><br>                                                                           "+
							"						    				<label>FECHA DE CANCELACION : </label><label>"+boleta.get(0).getFec_can()+"</label><br>                                                                           "+
							"						    				<label>RUC : </label><label>"+boleta.get(0).getRuc_cli()+"</label><br>                                                                           "+
							"						    				<label>RAZON SOCIAL : </label><label>"+boleta.get(0).getRaz_soc()+"</label><br>                                                                           "+
							"						    				<br>                                                                                                                                                          "+
							"						    			</fieldset>                                                                                                                                                       "+
							"			    					</td>                                                                                                                                                                 "+
							"			    				</tr>                                                                                                                                                                     "+
							"			    			</table>                                                                                                                                                                      ";

					
				}
				
				
			}
			
			
		}
		
		String cuerpo =
				"<!DOCTYPE html>                                                                                                                                                                                          		  "+
						"                                                                                                                                                                                                         "+
						"<html>                                                                                                                                                                                                   "+
						"  <head>                                                                                                                                                                                                 "+
						"    <meta charset='utf-8'>                                                                                                                                                                               "+
						"    <meta name='viewport' content='width=device-width;initial-scale-1'>                                                                                                                                  "+
						"    <meta http-equiv='X-UA-Compatible' content='IE-edge'>                                                                                                                                                "+
						"    <title></title>                                                                                                                                                                                      "+
						"    <style>                                                                                                                                                                                              "+
						"    	body{                                                                                                                                                                                             "+
						"    		font-family: Arial, Helvetica, sans-serif;                                                                                                                                                    "+
						"    	}                                                                                                                                                                                                 "+
						"                                                                                                                                                                                                         "+
						"		.container {                                                                                                                                                                                      "+
						"		    position: relative;                                                                                                                                                                           "+
						"		}                                                                                                                                                                                                 "+
						"                                                                                                                                                                                                         "+
						"		.bottomright {                                                                                                                                                                                    "+
						"		    position: absolute;                                                                                                                                                                           "+
						"		    bottom: 8px;                                                                                                                                                                                  "+
						"		    right: 16px;                                                                                                                                                                                  "+
						"		    font-size: 18px;                                                                                                                                                                              "+
						"		}                                                                                                                                                                                                 "+
						"                                                                                                                                                                                                         "+
						"		img {                                                                                                                                                                                             "+
						"		    width: 100%;                                                                                                                                                                                  "+
						"		    height: auto;                                                                                                                                                                                 "+
						"		}                                                                                                                                                                                                 "+
						"                                                                                                                                                                                                         "+
						"	    @media screen and (max-width:600px){                                                                                                                                                              "+
						"		    table [class='responsive-table'] {                                                                                                                                                            "+
						"		    	width: 100% !important;                                                                                                                                                                   "+
						"		    }                                                                                                                                                                                             "+
						"                                                                                                                                                                                                         "+
						"		    table [class='responsive-imagen'] {                                                                                                                                                           "+
						"            	width: 100% !important;                                                                                                                                                                   "+
						"                                                                                                                                                                                                         "+
						"            }                                                                                                                                                                                            "+
						"                                                                                                                                                                                                         "+
						"            table [class='responsive-table-2']{                                                                                                                                                          "+
						"            	width: 100% !important;                                                                                                                                                                   "+
						"            	padding: 20px;                                                                                                                                                                            "+
						"            }                                                                                                                                                                                            "+
						"                                                                                                                                                                                                         "+
						"            td[class='titulo']{                                                                                                                                                                          "+
						"            	font-size: 10px !important;                                                                                                                                                               "+
						"            	text-align: center !important;                                                                                                                                                            "+
						"            }                                                                                                                                                                                            "+
						"                                                                                                                                                                                                         "+
						"    	}                                                                                                                                                                                                 "+
						"    </style>                                                                                                                                                                                             "+
						"  </head>                                                                                                                                                                                                "+
						"  <body >                                                                                                                                                                                                "+
						"  	<!-- TABLA CONTENEDORA-->                                                                                                                                                                             "+
						"    <table width='100%' cellpadding='0' cellspacing='0' border='0' align='center'>                                                                                                                       "+
						"    	<tr>                                                                                                                                                                                              "+
						"    		<td style='vertical-align: middle; text-align: center;' >                                                                                                                                     "+
						"    			<!--TABLA DEL EMAIL-->                                                                                                                                                                    "+
						"    			<table width='600px' cellpadding='0' cellspacing='0' border='0' style='text-align:left' class='responsive-table'>                                                                         "+
						"			    	<tr>                                                                                                                                                                                  "+
						"			    		<td class='titulo'>                                                                                                                                                               "+
						"			    			<div class='container'>                                                                                                                                                       "+
						"							  <img  style='border-radius: 4px;' class='responsive-imagen'  height='150px' src='https://imagizer.imageshack.us/v2/600x150q90/923/cxMyfj.jpg'>                              "+
						"							  	<div class='bottomright'>                                                                                                                                                 "+
						"							  		<h2 style='text-align:right;text-shadow: 2px 0 0 #fff, -2px 0 0 #fff, 0 2px 0 #fff, 0 -2px 0 #fff, 1px 1px #fff, -1px -1px 0 #fff, 1px -1px 0 #fff, -1px 1px 0 #fff;'>"+
						"							  			<span>"+boleta.get(0).getTip_com()+" DE VENTA                                                                                                                                             "+
						"							  				<span>&nbsp;</span><br>                                                                                                                                       "+
						"							  				<span>&nbsp;</span>                                                                                                                                           "+
						"							  				<span style='font-size:18px;'>"+boleta.get(0).getNum_com()+"</span>                                                                                                          "+
						"							  			</span>                                                                                                                                                           "+
						"							  		</h2>                                                                                                                                                                 "+
						"							  	</div>                                                                                                                                                                    "+
						"							  </div>                                                                                                                                                                      "+
						"							</div>                                                                                                                                                                        "+
						"			    		</td>                                                                                                                                                                             "+
						"			    		<td>                                                                                                                                                                              "+
						"			    			                                                                                                                                                                              "+
						"			    		</td>                                                                                                                                                                             "+
						"			    	</tr>                                                                                                                                                                                 "+
						"			    	<tr>                                                                                                                                                                                  "+
						"			    		<td>                                                                                                                                                                              "+
													datosCliente+
						"			    		</td>                                                                                                                                                                             "+
						"			    	</tr>                                                                                                                                                                                 "+
						"			    </table>                                                                                                                                                                                  "+
						"			    <table width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table'>                                                                                                 "+
						"			    	<tr>                                                                                                                                                                                  "+
						"			    		<td style='padding-bottom:20px;text-align:left;'>                                                                                                                                 "+
						"			    			<label style='font-weight:bold;'>Detalle de Pedido : </label>                                                                                                                 "+
						"			    		</td>                                                                                                                                                                             "+
						"			    	</tr>                                                                                                                                                                                 "+
						"			    	<tr>                                                                                                                                                                                  "+
						"			    		<td>                                                                                                                                                                              "+
						"			    			<table width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table' style='padding-bottom:20px;'>                                                        "+
						"			    				<thead style='font-weight:bold;background-color:#D8D8D8;'>                                                                                                                "+
						"			    					<tr>                                                                                                                                                                  "+
						"			    						<td style='border-width:2.5px;border-top-style: none;border-right-style: solid;border-left-style: none;'>N° de Producto</td>                                      "+
						"			    						<td style='border-width:2.5px;border-top-style: none;border-right-style: solid;border-left-style: none;'>Producto</td>                                            "+
						"			    						<td style='border-width:2.5px;border-top-style: none;border-right-style: solid;border-left-style: none;'>Precio Und.</td>                                         "+
						"			    						<td style='border-width:2.5px;border-top-style: none;border-right-style: solid;border-left-style: none;'>Descuento</td>                                           "+
						"			    						<td style='border-width:2.5px;border-top-style: none;border-right-style: solid;border-left-style: none;'>Cantidad</td>                                            "+
						"			    						<td style='border-width:2.5px;border-top-style: none;border-right-style: solid;border-left-style: none;'>Importe</td>                                             "+
						"			    					</tr>                                                                                                                                                                 "+
						"			    				</thead>                                                                                                                                                                  "+
						"			    				<tbody>                                                                                                                                                                   "+
						"			    					  "+tabla+"                                                                                                                                                                    "+
						"			    				</tbody>                                                                                                                                                                  "+
						"			    			</table>                                                                                                                                                                      "+
						"                                                                                                                                                                                                         "+
						"			    			<table width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table'>                                                                                     "+
						"			    				<tr>                                                                                                                                                                      "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX' style='text-align:left;'>SUBTOTAL: </td>                                                                                                            "+
						"			    					<td width='100PX'>S/. "+Math.round(subtotal * 100.00) / 100.00+"</td>                                                                                                                                           "+
						"			    				</tr>                                                                                                                                                                     "+
						"			    				<tr>                                                                                                                                                                      "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX' style='text-align:left;'>IGV (18%) : </td>                                                                                                          "+
						"			    					<td width='100PX'>S/. "+Math.round((subtotal*0.19) * 100.00) / 100.00+"</td>                                                                                                                                           "+
						"			    				</tr>                                                                                                                                                                     "+
						"			    				<tr>                                                                                                                                                                      "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX' style='text-align:left;'>CAR. ENVIO : </td>                                                                                                         "+
						"			    					<td width='100PX'>S/. "+Math.round(boleta.get(0).getCar_ent() * 100.00) / 100.00+"</td>                                                                                                                                           "+
						"			    				</tr>                                                                                                                                                                     "+
						"			    				<tr>                                                                                                                                                                      "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX'></td>                                                                                                                                               "+
						"			    					<td width='100PX' style='text-align:left;'>TOTAL (con IGV) : </td>                                                                                                            "+
						"			    					<td width='100PX'>S/. "+Math.round((subtotal*1.19) * 100.00) / 100.00+"</td>                                                                                                                                           "+
						"			    				</tr>                                                                                                                                                                     "+
						"			    			</table>                                                                                                                                                                      "+
						"			    			<table width='600px' cellpadding='0' cellspacing='0' border='0' class='responsive-table' style='padding-bottom:20px;'>                                                        "+
						"			    				<tr>                                                                                                                                                                      "+
						"			    					<td style='padding.right:15px;'>                                                                                                                                      "+
						"			    						<img  class='responsive-imagen' width='80px' height='80px' src='https://imagizer.imageshack.us/v2/330x330q90/924/WwjtMm.jpg' alt=''>                              "+
						"			    					</td>                                                                                                                                                                 "+
						"			    					<td style='text-align:left;'>                                                                                                                                         "+
						"			    						NANO SPORTS PRODUCTOS EXCLUSIVOS PARA EL ENTRENAMIENTO<br>                                                                                                        "+
						"			    						TELEFONO : (511) 997274277                                                                                                                                        "+
						"			    						www.nanosports.com.pe - info@nanosports.com.pe                                                                                                                    "+
						"			    					</td>                                                                                                                                                                 "+
						"			    				</tr>                                                                                                                                                                     "+
						"			    			</table>                                                                                                                                                                      "+
						"			    		</td>                                                                                                                                                                             "+
						"			    	</tr>                                                                                                                                                                                 "+
						"			    </table>                                                                                                                                                                                  "+
						"    		</td>                                                                                                                                                                                         "+
						"    	</tr>                                                                                                                                                                                             "+
						"    </table>                                                                                                                                                                                             "+
						"  </body>                                                                                                                                                                                                "+
						"</html>                                                                                                                                                                                                  ";

		
        String formulario = cuerpo;
 
		
		
		return formulario;
		
	}
	
}
