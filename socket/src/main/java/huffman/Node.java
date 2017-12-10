package huffman;
import java.util.Map;

public class Node implements Comparable<Node> {
	private char simbolo;
    private int contador;

    private Node esquerda;
    private Node direita;

    public Node(char simbolo) {
        this.simbolo = simbolo;
    }

    public Node(Node esquerda, Node direita) {
        this.simbolo = '+';
        this.esquerda = esquerda;
        this.direita = direita;
    }

    public boolean ehFolha() {
        return esquerda == null && direita == null;
    }

    public int retFrequencia() {
        if (ehFolha())
            return contador;
        return direita.retFrequencia() + direita.retFrequencia();
    }

    public char retSimbolo() {
        return simbolo;
    }

    public Node retEsquerda() {
        return esquerda;
    }

    public Node retDireita() {
        return direita;
    }

    public void add() {
        contador++;
    }

    @Override
    public int compareTo(Node n) {
        return retFrequencia() - n.retFrequencia() ;
    }

    @Override
    public String toString() {
        String ch = simbolo == '\n' ? "\\n" : "" + simbolo;

        return String.format("'%s': %d", ch, retFrequencia());
    }

    public void preencheMapa(Map<Character, String> codemap, String work) {
        if (ehFolha()) {
            codemap.put(retSimbolo(), work);
            return;
        }

        esquerda.preencheMapa(codemap, work + "0");
        direita.preencheMapa(codemap, work + "1");
    }
}