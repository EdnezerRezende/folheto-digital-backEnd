package br.com.igrejadecristo.folhetodigital.relatorios;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GeraFolheto {
	@Autowired
	private MensagemRepository mensagemDAO;
	
	public void gerar(String layout) throws JRException, SQLException, ClassNotFoundException {

		// gerando o jasper design
		JasperDesign desenho = JRXmlLoader.load(layout);

		// compila o relatório
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// estabelece conexão
//		Class.forName(driver);
//		Connection con = DriverManager.getConnection(url, login, pwd);
//		Statement stm = con.createStatement();
//		String query = "select * from turma";
//		ResultSet rs = stm.executeQuery(query);
//
//		// implementação da interface JRDataSource para DataSource ResultSet
//		JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
		//Captura os dados
		List<Mensagem> mensagens = mensagemDAO.findAllByOrderByDataCriado();
		
		// executa o relatório
		Map parametros = new HashMap();
//		parametros.put("nota", new Double(10));
		parametros.put("mensagem", mensagens.get(0));
		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros);

		// exibe o resultado
		JasperViewer viewer = new JasperViewer(impressao, true);
		viewer.show();
	}

	public static void main(String[] args) {
		try {
			new GeraFolheto().gerar("report.jrxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
