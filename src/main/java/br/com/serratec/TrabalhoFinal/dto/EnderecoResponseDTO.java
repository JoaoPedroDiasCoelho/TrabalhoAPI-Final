package br.com.serratec.TrabalhoFinal.dto;

public record EnderecoResponseDTO(String cep, String logradouro, String bairro, String localidade, String uf) {

	
	public String cep() {
		return cep;
	}

	public String logradouro() {
		return logradouro;
	}

	public String bairro() {
		return bairro;
	}

	public String localidade() {
		return localidade;
	}

	public String uf() {
		return uf;
	}

	
}