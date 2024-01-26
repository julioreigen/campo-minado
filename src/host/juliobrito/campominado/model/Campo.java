package host.juliobrito.campominado.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Campo {
    private final int linha;
    private final int coluna;
    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    void setAberto(boolean aberto) {
        this.aberto = aberto;
        if (aberto)
            notificarObservadores(CampoEvento.ABRIR);
    }

    private final List<Campo> vizinhos = new ArrayList<>();
    private final Set<CampoObservador> observadores = new LinkedHashSet<>();
//  private final List<BiConsumer<Campo, CampoEvento>> observadores = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registrarObservador(CampoObservador observador) {
        observadores.add(observador);
    }

    private void notificarObservadores(CampoEvento evento) {
        observadores.forEach(o -> o.eventoOcorreu(this, evento));
    }

    public boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if ((deltaGeral == 1 && !diagonal) || (deltaGeral == 2 && diagonal)) {
            vizinhos.add(vizinho);
            return true;
        }
        return false;
    }

    public void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;

            if (marcado) {
                notificarObservadores(CampoEvento.MARCAR);
            } else {
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public void abrir() {
        if (!aberto && !marcado) {
            aberto = true;
            if (minado) {
                notificarObservadores(CampoEvento.EXPLODIR);
                return;
            }

            setAberto(true);

            if (vizinhancaSegura()) {
                vizinhos.forEach(Campo::abrir);
            }
        }
    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public void minar() {
        minado = true;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isMinado() {
        return minado;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public int minasNaVizinhanca() {
        return (int) vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
        notificarObservadores(CampoEvento.REINICIAR);
    }
}
