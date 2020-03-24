package br.com.igrejadecristo.folhetodigital.relatorios;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class GeraFolheto {
	@Autowired
	private MensagemRepository mensagemDAO;
	
	@Autowired
	private IgrejaRepository igrejaDAO;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public void gerar(String layout) throws JRException, SQLException, ClassNotFoundException, IOException {
		Resource resource = new ClassPathResource("/relatorios/folhetim.jrxml");
//		String path = resourceLoader.getResource("classpath:/relatorios/folhetim.jrxml").getURI().getPath();
		// gerando o jasper design
//		JasperDesign desenho = JRXmlLoader.load(layout);

//		// compila o relatório
//		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

//		// implementação da interface JRDataSource para DataSource ResultSet
//		JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
		//
		//List<Mensagem> mensagens = mensagemDAO.findAllByOrderByDataCriado();
		//JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(mensagens);
		// executa o relatório
		Map parametros = new HashMap(); // Usar parametros para informar dados mais gerenciais do relatório, tipo a data .... 
		parametros.put("dataFolhetim", "07/03/2020");
		parametros.put("enderecoIgreja", "QSB 10/11 A.E. 09 CEP: 72015-600 Taguatinga Sul-DF");
		parametros.put("telefoneIgreja", "(61) 3563-1865");
		parametros.put("siteIgreja", "www.ictaguatinga.com.br");
		parametros.put("emailIgreja", "cristeltagsul@bol.com.br");
		parametros.put("mensagem", "texto de mensagem gerado pelo jasper");
		parametros.put("tituloMensagem", "Quase no Reino de Deus?");
		parametros.put("autorMensagem", "Pr Gerson Vicente de Sousa");
//		parametros.put("mensagem", mensagens.get(0).getMensagem());
		//parametros.put("mensagemAutor", mensagens.get(0).getAutor());
		//parametros.put("mensagemTitulo", mensagens.get(0).getTitulo());
		JasperReport jr;
        JasperPrint jprint;
		jr = JasperCompileManager.compileReport(resource.getURL().getPath());
		JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, new JREmptyDataSource(1));
		// exibe o resultado
		JasperViewer viewer = new JasperViewer(impressao, true);
		viewer.show();
	}

	public static void main(String[] args) {
		try {
			new GeraFolheto().gerar("relatorios/folhetim_2.jrxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
