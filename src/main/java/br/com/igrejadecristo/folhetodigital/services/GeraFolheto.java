package br.com.igrejadecristo.folhetodigital.services;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.BoletimDTO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class GeraFolheto {
	
	public void gerar(BoletimDTO boletim, HttpServletResponse response) throws JRException, SQLException, ClassNotFoundException, IOException {
		Resource resource = new ClassPathResource("/relatorios/folhetim_2.jrxml");
		Resource caminhoLogo = new ClassPathResource("/imagens/logo.jpg");
		Resource caminhoCarrinho = new ClassPathResource("/imagens/carrinhocompras.jpg");
		ImageIcon gto = new ImageIcon(caminhoLogo.getURL().getPath()); 
		ImageIcon carrinho = new ImageIcon(caminhoCarrinho.getURL().getPath()); 
		
		String niver = "";
		for(String aniversariante : boletim.getAniversariantes()){
			niver += aniversariante + "\n";
		};
		
		String devocionais = "";
		for(String devocional: boletim.getTextosDevocionais()) {
			devocionais += devocional + "\n";
		}
		
		String pgsQuarta = "";
		for(String pg: boletim.getPgsQuartaValores()) {
			pgsQuarta += pg+"\n\n";
		}
		
		String pgsQuinta = "";
		for(String pg: boletim.getPgsQuintaValores()) {
			pgsQuinta += pg+"\n\n";
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>(); 
		parametros.put("logo", gto.getImage());
		parametros.put("dataFolhetim", boletim.getDataBoletim()+ " - Ano XVIII");
		
		String enderecoIgreja = boletim.getIgreja().getEndereco().getLogradouro() + 
			" CEP: " +boletim.getIgreja().getEndereco().getCep() +" "+
			boletim.getIgreja().getEndereco().getCidade().getNome();
		
		parametros.put("enderecoIgreja", enderecoIgreja);
		
		
		 Iterator<String> telefonesAsIterator = boletim.getIgreja().getTelefones().iterator();
		 String telefone = "";
         while (telefonesAsIterator.hasNext()){
        	 	String registro = telefonesAsIterator.next();
                telefone = "("+registro.substring(0,2)+") "+ registro.substring(2,6)+"-"+registro.substring(6);
         }
		
		parametros.put("telefoneIgreja", telefone);
		parametros.put("siteIgreja", "www.ictaguatinga.com.br");
		parametros.put("emailIgreja", boletim.getIgreja().getEmail());
		parametros.put("mensagem", boletim.getMensagem().getMensagem());
		parametros.put("tituloMensagem", boletim.getMensagem().getTitulo());
		parametros.put("autorMensagem", boletim.getMensagem().getAutor());
		parametros.put("aniversariantes", niver);
		parametros.put("carrinhoCompra", carrinho.getImage());
		parametros.put("missoes", boletim.getMissao().getMensagem());
		parametros.put("missoesTitulo", boletim.getMissao().getTitulo());
		parametros.put("textosDevocionais", devocionais);
		parametros.put("pgsQuarta", pgsQuarta);
		parametros.put("pgsQuinta", pgsQuinta);
		
		JasperReport jr;
		
		jr = JasperCompileManager.compileReport(resource.getURL().getPath());
		JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, new JREmptyDataSource(1));
		
//		JRExporter exporter = new JRPdfExporter();
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
//		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("boletim_semanal_"+ LocalDate.now().toString()+".pdf"));
//		exporter.exportReport();
		
		response.setContentType("application/x-pdf");
	    response.setHeader("Content-disposition", "inline; filename=boletim_semanal_"+LocalDate.now().toString()+".pdf");

	    final OutputStream outStream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(impressao, outStream);
	}
	
}
