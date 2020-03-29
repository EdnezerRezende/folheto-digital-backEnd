package br.com.igrejadecristo.folhetodigital.relatorios;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
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
		Resource resource = new ClassPathResource("/relatorios/folhetim_2.jrxml");
		Resource caminhoLogo = new ClassPathResource("/imagens/logo.jpg");
		Resource caminhoCarrinho = new ClassPathResource("/imagens/carrinhocompras.jpg");
		ImageIcon gto = new ImageIcon(caminhoLogo.getURL().getPath()); 
		ImageIcon carrinho = new ImageIcon(caminhoCarrinho.getURL().getPath()); 
		
		//Isto devera ser removido e vim uma lista de aniversariantes
		List<String> aniversariantes = new ArrayList<>();
		aniversariantes.add("15/03 - Ana Luiza Aragão Gomes");
		aniversariantes.add("16/03 - Geni Alves da Silva");
		aniversariantes.add("17/03 - Maria Helena de Freitas Ferreira");
		aniversariantes.add("18/03 - Ednezer de Godoi Rezende");
		aniversariantes.add("20/03 - Guilherme Leandro Silva Oliveira");
		aniversariantes.add("21/03 - Sarah Arielle G. Coutinho da Silva");
		
		String niver = "";
		for(String aniversariante : aniversariantes){
			niver += aniversariante + "\n";
		};
		
		List<String> textosDevocionais = new ArrayList<>();
		textosDevocionais.add("1) Êxodo 17.1.7 - O Senhor está entre nós ou não?");
		textosDevocionais.add("2) 1 Coríntios 10.1-12 - Não ponham Deus á prova");
		textosDevocionais.add("3) João 9.1-12 - Quem pecou?");
		textosDevocionais.add("4) Romanos 5.1-11 - Justificados pela fé");
		textosDevocionais.add("5) Salmo 95 - Somos o povo de Deus");
		textosDevocionais.add("6) João 9.35-41 - Eu creio!");
		
		String devocionais = "";
		for(String devocional: textosDevocionais) {
			devocionais += devocional + "\n";
		}
		
		List<String> pgsQuartaValores = new ArrayList<>();
		pgsQuartaValores.add("*Casa da Cleide e Paulo - 20h \n SMT Conjunto 2, Lote 5C \n Taguatinga Sul \n Líder: Moisés ");
		pgsQuartaValores.add("*Casa da Helena e Pedro - 20h \n Quadra 101, conjunto 12, casa 05 \n Recanto das Emas \n Líder: Pr Euflávio ");
		pgsQuartaValores.add("*Casa da Lia e Adail - 20h \n CNB 14, Lote 4, Apto 317 Ed. Marília \n Taguatinga Norte \n Líder: Mauro ");
		pgsQuartaValores.add("*Casa da Maria Helena - 20h \n CSB 03, Lote 5, Apto 1201 Ed. São José \n Taguatinga Sul \n Líder: Pra. Iolanda ");
		pgsQuartaValores.add("*Casa da Nunci - 20h \n CSB 6, Lote 8 Apto 405 Ed. Pedro Gontijo \n  \n Líder: Pra. Maria");
		pgsQuartaValores.add("*PIC Taguatinga - 20h \n QSB 10/11, A.E. 09 \n Taguatinga Sul \n Líder: Sheyla");
		String pgsQuarta = "";
		for(String pg: pgsQuartaValores) {
			pgsQuarta += pg+"\n\n";
		}
		List<String> pgsQuintaValores = new ArrayList<>();
		pgsQuintaValores.add("*Casa da Margarida e do Antônio - 15h \n Rua 12, Chácaraa 129-A, Conjunto B, Casa 7-A \n Vicente Pires \n Líder: Ivani ");
		pgsQuintaValores.add("*Casa da Ieda e Adeuvaldo - 20h \n Rua 24 Norte, Lote 8, Apto 1301 Res. Águas de Manaíra \n Águas Claras \n Líder: Pra. Renata ");
		String pgsQuinta = "";
		for(String pg: pgsQuintaValores) {
			pgsQuinta += pg+"\n\n";
		}
		
		String missoesTitulo = "Conheça os missionários apoiados pela PIC";
		String missoes = "Você sabe quem são os missionários apoiados pela PIC Taguatinha "
				+ "e onde eles realizam o seu trabalho? Nessa Edição do boletim trazemos uma lista com todos eles e algumas informações que podem te ajudar na hora de cobri-los em oração:";
		
		Map parametros = new HashMap(); // Usar parametros para informar dados mais gerenciais do relatório, tipo a data ....
		parametros.put("logo", gto.getImage());
		parametros.put("dataFolhetim", "07/03/2020");
		parametros.put("enderecoIgreja", "QSB 10/11 A.E. 09 CEP: 72015-600 Taguatinga Sul-DF");
		parametros.put("telefoneIgreja", "(61) 3563-1865");
		parametros.put("siteIgreja", "www.ictaguatinga.com.br");
		parametros.put("emailIgreja", "cristeltagsul@bol.com.br");
		parametros.put("mensagem", "texto de mensagem gerado pelo jasper");
		parametros.put("tituloMensagem", "Quase no Reino de Deus?");
		parametros.put("autorMensagem", "Pr Gerson Vicente de Sousa");
		parametros.put("aniversariantes", niver);
		parametros.put("carrinhoCompra", carrinho.getImage());
		parametros.put("missoes", missoes);
		parametros.put("missoesTitulo", missoesTitulo);
		parametros.put("textosDevocionais", devocionais);
		parametros.put("pgsQuarta", pgsQuarta);
		parametros.put("pgsQuinta", pgsQuinta);
		
		JasperReport jr;
		
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
