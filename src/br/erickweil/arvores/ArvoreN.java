package br.erickweil.arvores;

public class ArvoreN<T> {
	T chave;
	ArvoreN<T> proximoIrmao;
	ArvoreN<T> primeiroFilho;
	
	public ArvoreN(T chave) {
		this.chave = chave;
		proximoIrmao = null;
		primeiroFilho = null;
	}
	
	public ArvoreN<T> adicionarFilho(T filho) {
		if(primeiroFilho == null) {
			primeiroFilho = new ArvoreN<T>(filho);
			return primeiroFilho;
		} else {
			return primeiroFilho.adicionarIrmao(filho);
		}
	}
	
	private ArvoreN<T> adicionarIrmao(T irmao) {
		if(proximoIrmao == null) {
			proximoIrmao = new ArvoreN<T>(irmao);
			return proximoIrmao;
		} else {
			// Pega o último irmão da lista encadeada
			ArvoreN<T> ultimoIrmao = proximoIrmao;
			while(ultimoIrmao.proximoIrmao != null) {
				ultimoIrmao = ultimoIrmao.proximoIrmao;
			}
			
			ultimoIrmao.proximoIrmao = new ArvoreN<T>(irmao);
			return ultimoIrmao.proximoIrmao;
		}
	}
}
