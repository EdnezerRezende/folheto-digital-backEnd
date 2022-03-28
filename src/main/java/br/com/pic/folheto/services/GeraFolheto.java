package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.BoletimDTO;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class GeraFolheto {
	
	public void gerar(final BoletimDTO boletim, final HttpServletResponse response) throws JRException, SQLException, ClassNotFoundException, IOException {
		final InputStream caminhoFolhetim = this.getClass().getResourceAsStream("/relatorios/folhetim_2.jrxml");
		final InputStream caminhoLogoInput = this.getClass().getResourceAsStream("/imagens/logo.jpg");
		final InputStream caminhoCarrinho = this.getClass().getResourceAsStream("/imagens/carrinhocompras.jpg");
		
		final ImageIcon gto = new ImageIcon(ImageIO.read(caminhoLogoInput));
		final ImageIcon carrinho = new ImageIcon(ImageIO.read(caminhoCarrinho));
		
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
		
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo", gto.getImage());
		parametros.put("dataFolhetim", boletim.getDataBoletim()+ " - Ano XVIII");
		
		final String enderecoIgreja = boletim.getIgreja().getEndereco().getLogradouro() +
			" CEP: " +boletim.getIgreja().getEndereco().getCep() +" "+
			boletim.getIgreja().getEndereco().getCidade().getNome();
		
		parametros.put("enderecoIgreja", enderecoIgreja);
		
		
		 final Iterator<String> telefonesAsIterator = boletim.getIgreja().getTelefones().iterator();
		 String telefone = "";
         while (telefonesAsIterator.hasNext()){
        	 	String registro = telefonesAsIterator.next();
                telefone = "("+registro.substring(0,2)+") "+ registro.substring(2,6)+"-"+registro.substring(6);
         }
		
		parametros.put("telefoneIgreja", telefone);
		parametros.put("siteIgreja", "www.ictaguatinga.com.br");
		parametros.put("emailIgreja", boletim.getIgreja().getEmail());

		final String mensagemSemanal = limparTagsHtml(boletim.getMensagem().getMensagem());
		final String missaoSemanal = limparTagsHtml(boletim.getMissao().getMensagem());
		
		parametros.put("mensagem", mensagemSemanal);
		parametros.put("tituloMensagem", boletim.getMensagem().getTitulo());
		parametros.put("autorMensagem", boletim.getMensagem().getAutor());
		parametros.put("aniversariantes", niver);
		parametros.put("carrinhoCompra", carrinho.getImage());
		parametros.put("missoes", missaoSemanal);
		parametros.put("missoesTitulo", boletim.getMissao().getTitulo());
		parametros.put("textosDevocionais", devocionais);
		parametros.put("pgsQuarta", pgsQuarta);
		parametros.put("pgsQuinta", pgsQuinta);
		
		JasperReport jr;
		
		jr = JasperCompileManager.compileReport(caminhoFolhetim);
		jr.setProperty("net.sf.jasperreports.default.font.name", "Arial Narrow");
		final JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, new JREmptyDataSource(1));
		
		response.setContentType("application/x-pdf");
	    response.setHeader("Content-disposition", "inline; filename=boletim_semanal_"+LocalDate.now().toString()+".pdf");

	    final OutputStream outStream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(impressao, outStream);
	}

	private String limparTagsHtml(String mensage) {
		mensage = mensage.replaceAll("&nbsp;", " ");
		mensage = mensage.replaceAll("<br>", ":BR:");
		mensage = mensage.replaceAll("\\<.*?>","");
		mensage = mensage.replaceAll("  ", " ");
		mensage = mensage.replaceAll("\r\n", " ");
		mensage = mensage.replaceAll(":BR:", "\r\n");
		
		return mensage;
	}
	
}
