package chat;

import java.io.Serializable;


public class paquete implements Serializable {
    
    private String IP_origen, IP_destino, usuario, Mensaje; 

    public String getIP_origen() {
        return IP_origen;
    }

    public void setIP_origen(String IP_origen) {
        this.IP_origen = IP_origen;
    }

    public String getIP_destino() {
        return IP_destino;
    }

    public void setIP_destino(String IP_destino) {
        this.IP_destino = IP_destino;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public paquete(String IP_origen, String IP_destino, String usuario, String Mensaje) {
        this.IP_origen = IP_origen;
        this.IP_destino = IP_destino;
        this.usuario = usuario;
        this.Mensaje = Mensaje;
    }
    public paquete() {
    }
    
    
}
