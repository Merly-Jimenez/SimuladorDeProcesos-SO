
package App;

/**
 *
 * @author Merly Jimenez
 */
public class Proceso {
    
    private int IdProceso; 
    private int ContadorPrograma=0;
    private String EstadoDeProceso, NomProceso;

    public Proceso(int a) {
        this.IdProceso = a;
        this.EstadoDeProceso = "Listo";
        this.NomProceso = "Proceso " + a;
    }

    public int getIdProceso() {
        return IdProceso;
    }

    public void setIdProceso(int IdProceso) {
        this.IdProceso = IdProceso;
    }

    public int getContadorPrograma() {
        return ContadorPrograma;
    }

    public void setContadorPrograma(int ContadorPrograma) {
        this.ContadorPrograma = ContadorPrograma;
    }

    public String getEstadoDeProceso() {
        return EstadoDeProceso;
    }

    public void setEstadoDeProceso(String EstadoDeProceso) {
        this.EstadoDeProceso = EstadoDeProceso;
    }

    public String getNomProceso() {
        return NomProceso;
    }

    public void setNomProceso(String NomProceso) {
        this.NomProceso = NomProceso;
    }
    
    
    
}
