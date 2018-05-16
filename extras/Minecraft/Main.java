import java.util.List;
import java.util.Arrays;

abstract class Bloco {
    public double tempo;
    List<String> ferramentas = Arrays.asList("picareta", "machado", "pá");
    public abstract void quebra(String ferramenta);
}

class Madeira extends Bloco {
    public void quebra(String ferramenta) {
        if (ferramenta == "machado")
            tempo = 3;
        else if (ferramentas.contains(ferramenta))
            tempo = 5;
        else tempo = 7;
        System.out.println("Quebrou 1 bloco de Madeira Bruta em " + tempo + "s.");
        System.out.println("Usou: " + ferramenta);
        System.out.println("Dropou 4 blocos de madeirinha.");
    }
}

class Terra extends Bloco {
    public void quebra(String ferramenta) {
        if (ferramenta == "pá")
            tempo = 1;
        else if (ferramentas.contains(ferramenta))
            tempo = 3;
        else tempo = 4;
        System.out.println("Quebrou 1 bloco de Terra em " + tempo + "s.");
        System.out.println("Usou: " + ferramenta);
        System.out.println("Dropou 1 bloco de terra.");
    }
}

class Pedra extends Bloco {
    public void quebra(String ferramenta) {
        if (ferramenta == "picareta")
            tempo = 1.5;
        else if (ferramentas.contains(ferramenta))
            tempo = 8;
        else tempo = 15;
        System.out.println("Quebrou 1 bloco de Pedra em " + tempo + "s.");
        System.out.println("Usou: " + ferramenta);
        System.out.println("Dropou 1 bloco de cobblestone");
    }
}

class Main {
    public static void main(String[] args) {
        Madeira m = new Madeira(); 
        Terra t = new Terra();
        Pedra p = new Pedra();

        m.quebra("machado");
        t.quebra("pá");
        p.quebra("mão");
    }
}
