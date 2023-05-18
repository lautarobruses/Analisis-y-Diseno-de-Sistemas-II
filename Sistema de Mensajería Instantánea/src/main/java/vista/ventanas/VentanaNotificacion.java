package vista.ventanas;

import controlador.ControladorInicio;
import controlador.ControladorMensajes;
import modelo.Cliente;
import modelo.Sistema;
import modelo.interfaces.IObserver;
import vista.interfaces.IVistaNotificacion;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class VentanaNotificacion extends JFrame implements IVistaNotificacion {
    private int tipo; // 1=error | 2=espera | 3=solicitud
    private JPanel PanelPrincipal;
    private JPanel PanelCentral;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JLabel TituloLabel;
    private JLabel Contenido1Label;
    private JLabel Contenido2Label;
    private JPanel ButtonPanel;

    @Override
    public void setActionListener(ActionListener controlador) {
        this.aceptarButton.addActionListener(controlador);
        this.cancelarButton.addActionListener(controlador);
    }

    @Override
    public void ejecutar() {
        setTitle("Sistema de Mensajeria Instantaneo");
        pack(); //Coloca los componentes
        setContentPane(PanelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(600,300); //Dimensiones del JFrame
        setResizable(false); //No redimensionable
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icono.png"))).getImage());
        toFront();
    }

    @Override
    public void cerrarVentana() {
        setVisible(false); //Oculto la ventana
        dispose(); // Cierra el JDialog
    }

    @Override
    public void creaOtraVentana(int tipo, String nombreUsuarioEmisor) {
        Cliente cliente = Sistema.getInstance().getCliente();
        switch (tipo) {
            case 0 -> { //CASO VENTANA MENSAJES
                VentanaMensajes ventanaMensajes = new VentanaMensajes();
                ControladorMensajes controladorMensajes = new ControladorMensajes(ventanaMensajes);
                ArrayList<IObserver> observadores = new ArrayList<>(cliente.getObservadores());
                observadores.add(controladorMensajes);
                cliente.setObservadores(observadores);
                cliente.setConnected(true);
                ventanaMensajes.setUsuarios(cliente.getNombreDeUsuario(), "nombre del receptor"); //TODO poner nombre de cliente receptor y hacer que se configure el nombre de cliente en la notificacion
                ventanaMensajes.ejecutar();
            }
            case 1 -> { //CASO VENTANA INICIO
                VentanaInicio ventanaInicio = new VentanaInicio();
                ControladorInicio controladorInicio = new ControladorInicio(ventanaInicio);
                cliente.agregarObservador(controladorInicio);
                //TODO REVISAR SI TENGO QUE REGISTRARME EN EL SERVER DE NUEVO
                try {
                    cliente.registrarServidor();
                } catch (Exception e){
                    e.printStackTrace();
                }
                ventanaInicio.ejecutar();
            }
        }
    }



    @Override
    public void lanzarVentanaEmergente(String mensaje) {
        JFrame jFrameVacio = new JFrame();
        JOptionPane.showMessageDialog(jFrameVacio, mensaje);
    }

    public int getTipo() {
        return tipo;
    }

    @Override
    public void setTipoVentana(int tipo, String nombreUsuarioEmisor) {
        this.tipo = tipo;
        switch (tipo) {
            case 1 -> { //tipo 1 -> Notificacion Error
                this.TituloLabel.setText("Error");
                this.Contenido1Label.setText("No ha sido posible conectarse con el cliente ingresado.");
                this.Contenido2Label.setText("Es posible que este no se encuentra disponible.");
                this.aceptarButton.setVisible(true);
                this.cancelarButton.setVisible(false);
            }
            case 2 -> { //tipo 2 -> Notificacion Espera
                this.TituloLabel.setText("Espere...");
                this.Contenido1Label.setText("La solicitud ha sido enviada correctamente. Aguarde a ");
                this.Contenido2Label.setText("que el cliente ingresado responda la misma.");
                this.aceptarButton.setVisible(false);
                this.cancelarButton.setVisible(true);
            }
            case 3 -> { //tipo 3 -> Notificacion Solicitud
                this.TituloLabel.setText("Atencion!");
                this.Contenido1Label.setText("El cliente '" + nombreUsuarioEmisor + "' quiere unirse a una ");
                this.Contenido2Label.setText("sesión con usted.");
                this.aceptarButton.setVisible(true);
                this.cancelarButton.setVisible(true);
            }
        }
    }
}
