package uni.xml;


public class Talon {

	private String id;
	private String fecha;
	private String concepto;
	private String monto;
	private String moneda;
		
	public Talon(){}
	
	public Talon(String id, String f, String c, String m, String mo) {
		this.id = id;
		this.fecha = f;
		this.concepto = c;
		this.monto = m;
		this.moneda = mo;
	}
     
	public String getConcepto() {
		return concepto;
	}

	public String getFecha() {
		return fecha;
	}

	public String getMoneda() {
		return moneda;
	}

	public String getMonto() {
		return monto;
	}

	public String getId() {
		return id;
	}

	public void setConcepto(String string) {
		concepto = string;
	}

	public void setFecha(String string) {
		fecha = string;
	}

	public void setMoneda(String string) {
		moneda = string;
	}

	public void setMonto(String string) {
		monto = string;
	}

	public void setId(String string) {
		id = string;
	}
	
	public String toString() {
		return id + fecha + concepto + monto + moneda;
	}
        
//  This overrides java.lang.equals
    public boolean equals(Object obj) {
        if ( (obj instanceof Talon) 
              && (((Talon)obj).getId().equals(this.id)) 
              && (((Talon)obj).getFecha().equals(this.fecha))
              && (((Talon)obj).getConcepto().equals(this.concepto))
              && (((Talon)obj).getMonto().equals(this.monto))
              && (((Talon)obj).getMoneda().equals(this.moneda)) 
           )           
            return true;
        else
            return false;
    }
            
    //  This overrides java.lang.hashCode
    public int hashCode() {
        return this.id.hashCode() + this.fecha.hashCode() + this.concepto.hashCode() + this.monto.hashCode() + this.moneda.hashCode();
    }       
            
}
