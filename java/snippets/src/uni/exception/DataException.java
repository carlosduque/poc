package uni.exception;

public class DataException extends Exception {

	/** Código de error */
	private String errorCode;	
	/** Causa de la excepción*/
	private Throwable cause;

	/**
	 * Constructor default.
	 */
	public DataException() {}
	
	/**
	 * Constructor. Establece el mensaje de detalle.
	 * @param message el mensaje de detalle
	 */
	public DataException(String message) {
		super(message);
	}

	/**
	 * Constructor. Establece la causa de la excepción.
	 * @param cause la causa de la excepción
	 */
	public DataException(Throwable cause) {
		this.cause = cause;
	}

	/**
	 * Constructor. Establece el código de error y el mensaje de detalle.
	 * @param errorCode el código de error
	 * @param message el mensaje de detalle
	 */
	public DataException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	/**
	 * Constructor. Establece el mensaje de detalle y la causa de la excepción.
	 * @param message el mensaje de detalle
	 * @param cause la causa de la excepción
	 */
	public DataException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}

	/**
	 * Constructor. Establece el código de error, el mensaje de detalle y la causa de la excepción.
	 * @param errorCode el código de error
	 * @param message el mensaje de detalle
	 * @param cause la causa de la excepción
	 */
	public DataException(String errorCode, String message, Throwable cause) {
		super(message);
		this.errorCode = errorCode;
		this.cause = cause;
	}
	
	/**
	 * Retorna el código de error.
	 * @return el código de error
	 */
	public String getErrorCode() {
		return errorCode;
	}
		
	/**
	 * Retorna la causa de la excepción.
	 * @return la causa de la excepción.
	 */
	public Throwable getCause() {
		return cause;
	}
	
	/**
	 * Imprime el stack trace de la causa de la excepción al standard error stream.
	 */
	public void printStackTrace() {

		System.err.println(this.toString());
		
		if (cause != null) {
			System.err.print("Caused by: ");
			cause.printStackTrace();
		}
	}
}
