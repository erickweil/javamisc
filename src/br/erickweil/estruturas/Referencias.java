package br.erickweil.estruturas;

public class Referencias {

	/**
	 * Classe Elo, representa um Elo de uma Corrente
	 * A ideia é que armazena um valor, no caso uma String nome
	 * E faz referência ao próximo elo, que pode ser null para inexistente
	*/
	public static class Elo {
		public String nome;
		public Elo proximo;
		
		/**
		 * Método construtor, cria o objeto com um valor para nome
		 * O próximo começa valendo null (Referência a nada)
		 */
		public Elo(String nome) {
			this.nome = nome;
			this.proximo = null;
		}
	}
	
	/**
	 * Classe Corrente, controle a adição de novos elos, para ficar mais simples
	 * 
	 * Veja que faz essencialmente o que foi feito no método testeAula(), porém organizado em métodos
	 */
	public static class Corrente {
		// Atributo que armazena o primeiro elo da corrente
		public Elo primeiro;
		
		/**
		 * Método que adiciona um elo na corrente. A ideia é
		 * fazer com que o novo elemento seja o primeiro agora.
		 * 
		 * Então ele passa a apontar para o antigo primeiro
		 * e em seguida o novo elemento se torna o primeiro da corrente
		 * @param novo Elemento que será adicionado
		 */
		public void adicionarElo(Elo novo) {
			// Faz o novo apontar para o atual primeiro
			novo.proximo = primeiro;
			// Faz o novo ser o primeiro agora
			primeiro = novo;
		}
		
		/**
		 * Printa na tela todos os elos, partindo do primeiro
		 */
		public void printar() {
			// Atual funciona como um contador, começa apontando ao primeiro
			// Se o primeiro é nulo nem entra na repetição
			Elo atual = primeiro;
			
			// enquanto o atual não é nulo repita...
			while(atual != null) {
				System.out.println(atual.nome);
				
				// É tipo que somar mais um no contador
				// Aqui estamos fazendo o atual apontar para o próximo
				// Se for nulo, irá parar na próxima etapa da repetição já
				atual = atual.proximo;
			}
		}
	}
	
	// Ponto de partida quando clica para executar
	public static void main(String[] args) {
		// testeAula(); //Descomente essa linha para executar o outro exemplo
		testeCorrente();
	}

	// Experimentando a classe Corrente
	public static void testeCorrente() {
		// Cria uma corrente vazia
		Corrente corrente = new Corrente();
		
		// Adiciona os elementos, veja que ao printar é printado na ordem ao contrário do que inseriu
		corrente.adicionarElo(new Elo("Batata"));
		corrente.adicionarElo(new Elo("Cenoura"));
		corrente.adicionarElo(new Elo("Beterraba"));
		corrente.adicionarElo(new Elo("Mandioca"));
		
		corrente.printar();
	}
	
	// Teste feito na aula sem usar a classe Corrente
	public static void testeAula() {
		
		Elo primeiro = new Elo("Maria");
		
		primeiro.proximo = new Elo("João");

		Elo novo = new Elo("Hellen");
		novo.proximo = primeiro;
		primeiro = novo;
		
		Elo atual = primeiro;
		while(atual != null) {
			System.out.println(atual.nome);
			
			atual = atual.proximo;
		}
	}

}
