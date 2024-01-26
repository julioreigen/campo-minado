package host.juliobrito.campominado.model;

@FunctionalInterface
public interface CampoObservador {
    void eventoOcorreu(Campo campo, CampoEvento evento);
}
