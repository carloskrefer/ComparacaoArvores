package com.krefer.utils.lista_encadeada;

public class No<T> {
	private T dado;
	private No<T> proximoNo;

	public No() {
		this.dado = null;
		this.proximoNo = null;
	}
	
	public No(T dado) {
		this.dado = dado;
		this.proximoNo = null;
	}

	public T getDado() {
		return dado;
	}

	public void setDado(T dado) {
		this.dado = dado;
	}

	public No<T> getProximoNo() {
		return proximoNo;
	}

	public void setProximoNo(No<T> proximoNo) {
		this.proximoNo = proximoNo;
	}
}
