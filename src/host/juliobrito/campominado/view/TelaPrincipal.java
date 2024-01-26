package host.juliobrito.campominado.view;

import host.juliobrito.campominado.model.Tabuleiro;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        Tabuleiro tabuleiro = new Tabuleiro(16,30, 60);
        add(new PainelTabuleiro(tabuleiro));
        // Configurando a janela
        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        // Definindo ícone da aplicação
        URL url = this.getClass().getResource("../resources/icon.png");
        Image icone = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(icone);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
