/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package informeseguridadinterna;

/**
 *
 * @author pablo
 */
public class Transaction {

    String decisionPointCn;
    String FlowID;
    String fechaLanzamiento;
    String fechaAprobacionSI;
    String accionSI;
    String entidad;
    String peticionario;
    String usuarioAfectado;

    public Transaction() {
    }

    public Transaction (String FlowID, String fechaAprobacionSI, String accionSI){
        this.FlowID = FlowID;
        this.fechaAprobacionSI = fechaAprobacionSI;
        this.accionSI = accionSI;
    }
    
    public Transaction(String decisionPointCn, String FlowID, String fechaLanzamiento, String fechaAprobacionSI, String accionSI, String entidad, String peticionario, String usuarioAfectado) {
        this.decisionPointCn = decisionPointCn;
        this.FlowID = FlowID;
        this.fechaLanzamiento = fechaLanzamiento;
        this.fechaAprobacionSI = fechaAprobacionSI;
        this.accionSI = accionSI;
        this.entidad = entidad;
        this.peticionario = peticionario;
        this.usuarioAfectado = usuarioAfectado;
    }
    
    

    public String getFlowID() {
        return FlowID;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String getEntidad() {
        return entidad;
    }

    public String getPeticionario() {
        return peticionario;
    }

    public String getUsuarioAfectado() {
        return usuarioAfectado;
    }

    public String getFechaAprobacionSI() {
        return fechaAprobacionSI;
    }

    public String getDecisionPointCn() {
        return decisionPointCn;
    }

    public String getAccionSI() {
        return accionSI;
    }

    
    
    
    public void setFlowID(String FlowID) {
        this.FlowID = FlowID;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public void setPeticionario(String peticionario) {
        this.peticionario = peticionario;
    }

    public void setUsuarioAfectado(String usuarioAfectado) {
        this.usuarioAfectado = usuarioAfectado;
    }

    public void setFechaAprobacionSI(String fechaAprobacionSI) {
        this.fechaAprobacionSI = fechaAprobacionSI;
    }

    public void setAccionSI(String accionSI) {
        this.accionSI = accionSI;
    }
    
    
    
    
}
