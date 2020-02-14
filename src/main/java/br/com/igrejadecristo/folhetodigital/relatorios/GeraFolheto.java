package br.com.igrejadecristo.folhetodigital.relatorios;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class GeraFolheto {
	@Autowired
	private MensagemRepository mensagemDAO;
	
	@Autowired
	private IgrejaRepository igrejaDAO;
	
	public void gerar(String layout) throws JRException, SQLException, ClassNotFoundException {

		// gerando o jasper design
//		JasperDesign desenho = JRXmlLoader.load(layout);

//		// compila o relatório
//		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

//		// implementação da interface JRDataSource para DataSource ResultSet
//		JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
		
		List<Mensagem> mensagens = mensagemDAO.findAllByOrderByDataCriado();
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(mensagens);
		// executa o relatório
		Map parametros = new HashMap(); // Usar parametros para informar dados mais gerenciais do relatório, tipo a data .... 
		parametros.put("dataFolhetim", LocalDate.now());
		parametros.put("mensagem", mensagens.get(0).getMensagem());
		parametros.put("mensagemAutor", mensagens.get(0).getAutor());
		parametros.put("mensagemTitulo", mensagens.get(0).getTitulo());
		JasperPrint impressao = JasperFillManager.fillReport(layout, parametros, ds);

		// exibe o resultado
		JasperViewer viewer = new JasperViewer(impressao, true);
		viewer.show();
	}

	public static void main(String[] args) {
		try {
			new GeraFolheto().gerar("./resources/relatorios/report.jrxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
