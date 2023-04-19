
package chat;

import java.io.Serializable;


public class paquete implements Serializable {
    
    private String nombre, direccion, mensaje;
    
    public paquete(String nombre, String direccion, String mensaje) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.mensaje = mensaje;
    }
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
