package modelo;

import java.io.*;
import java.net.Socket;

public class Conexion {
    private int puertoCliente;
    private int puertoServidor;
    private boolean estaModoEscucha;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String nombreUsuario;

    public Conexion() {
    }

    public void mandarMensaje(Object o) throws IOException {
        output.writeObject(o);
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        } catch (IOException e) {
            System.err.println("\nError al cerrar el socket: " + e.getMessage());
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getPuertoCliente() { return puertoCliente; }

    public void setPuertoCliente(int puertoCliente) { this.puertoCliente = puertoCliente; }

    public int getPuertoServidor() { return puertoServidor; }

    public void setPuertoServidor(int puertoServidor) { this.puertoServidor = puertoServidor; }

    public boolean isEstaModoEscucha() { return estaModoEscucha;}

    public void setEstaModoEscucha(boolean estaModoEscucha) {this.estaModoEscucha = estaModoEscucha;}

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "puertoServidor=" + puertoServidor +
                ", puertoCliente=" + puertoCliente +
                ", socket=" + socket +
                ", output=" + output +
                ", input=" + input +
                '}';
    }
}