package huffman;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class Huffman {
	private Node raiz;

    private char[] getLetras(String texto) {
        char[] letras = new char[texto.length()];
        texto.getChars(0, texto.length(), letras, 0); 
        return letras;
    }

    private PriorityQueue<Node> contafrequencias(char[] letras) { // function that returns a priority queue.
        Map<Character, Node> count = new HashMap<>();
        for (char ch : letras) {
            if (!count.containsKey(ch)) {
                count.put(ch, new Node(ch));
            }
            count.get(ch).add();
        }

        return new PriorityQueue<>(count.values());
    }

    private Node criaArvore(PriorityQueue<Node> nodes) {
        while (true) {
            Node node1 = nodes.poll();
            Node node2 = nodes.poll();
            Node pai = new Node(node1, node2);

            if (nodes.isEmpty()) {
                return pai;
            }

            nodes.add(pai);
        }
    }

    private Map<Character, String> criaMapa() {
        Map<Character, String> result = new TreeMap<>();
        raiz.preencheMapa(result, "");
        return result;
    }

    public String codifica(String text) {
        char[] letters = getLetras(text);
        raiz = criaArvore(contafrequencias(letters));
        Map<Character, String> codemap = criaMapa();
        
        StringBuilder data = new StringBuilder();
        for (char ch : letters) {
            data.append(codemap.get(ch));
           
        }
        return data.toString();
    }

    public String decodifica(String data) {
        Node atual = raiz;

        StringBuilder resultado = new StringBuilder();
        for (char ch : getLetras(data)) {
            if (ch == '0') {
                atual = atual.retEsquerda();
            } else {
                atual = atual.retDireita();
            }

            if (atual.ehFolha()) {
            	resultado.append(atual.retSimbolo());
                atual = raiz;
            }
        }
        return resultado.toString();
    }
    
    public void printMapa(){
		Map<Character, String> Dicionario = criaMapa();
		TreeSet<Character> Chaves = new TreeSet<Character> ( Dicionario.keySet() );
		System.out.println("Tamanho do Dicionario "+ Dicionario.size() );
		for( Character chave : Chaves )
		{
    	 System.out.println("\n"+chave+":"+Dicionario.get(chave));
		}
    }
}