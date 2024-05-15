package enviando.email;

import junit.framework.TestCase;


/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	@org.junit.Test
	public void testeEmail() throws Exception {
		
		StringBuilder stringBuilderTextoEmail =new StringBuilder();
		stringBuilderTextoEmail.append("Olá, <br/><br/>");
		stringBuilderTextoEmail.append("Você está recebendo acesso ao curso de Java.<br/><br/>");
		stringBuilderTextoEmail.append("Para ter acesso, clique no botão abaixo.<br/><br/>");
		
		stringBuilderTextoEmail.append("<b>Loging:</b> login.<br/>");
		stringBuilderTextoEmail.append("<b>Senha:</b> senha.<br/><br/>");

		stringBuilderTextoEmail.append(
				"<a target=\"_blank\""
				+ "href=\"https://projetojavaweb.com\""
				+ "style=\"color:#2525a7; padding: 14px 25px;"
				+ "text-align:center;"
				+ "text-decoration:none;"
				+ "display:inline-block;"
				+ "border-radius:30px;"
				+ "font-size:20px;"
				+ "font-family:courier;"
				+ "border:3px solid green;"
				+ "background-color:#99DA39;\">Acessar Portal</a><br/><br/>");
		
		stringBuilderTextoEmail.append("<span style=\"fonti-size:8px;\">Ass Julia Baptista JDev Treinamento:</span><br/><br/>");

		
		try {
		
		ObjetoEnviaEmail enviaEmail =
				new ObjetoEnviaEmail("baptista.julia@yahoo.com.br, juliabaptistadev@gmail.com",
									"Julia Baptista Jdev Treinamento",
									"Testando e-mail java",
									stringBuilderTextoEmail.toString());
		
		//enviaEmail.enviarEmail(true);
		
		enviaEmail.enviarEmailAnexo(true);
		
		// Thread.sleep(5000);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
