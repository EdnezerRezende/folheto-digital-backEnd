package br.com.igrejadecristo.folhetodigital.restExternal;

import java.util.List;

import br.com.igrejadecristo.folhetodigital.entidades.Capitulo;
import br.com.igrejadecristo.folhetodigital.entidades.Livro;
import br.com.igrejadecristo.folhetodigital.entidades.Versiculo;

public class Biblia {
		private Livro book;
		private Capitulo chapter;
		private List<Versiculo> verses;
		
		
		public Livro getBook() {
			return book;
		}
		public void setBook(Livro book) {
			this.book = book;
		}
		public Capitulo getChapter() {
			return chapter;
		}
		public void setChapter(Capitulo chapter) {
			this.chapter = chapter;
		}
		public List<Versiculo> getVerses() {
			return verses;
		}
		public void setVerses(List<Versiculo> verses) {
			this.verses = verses;
		}
		
		
		
}
