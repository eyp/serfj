package net.sf.serfj.config;

/**
 * Clase en la que se definen los diferentes parametros de configuracion que se van a utilizar en el cliente, y los que
 * son comunes a los dos.
 * 
 * @author Eduardo
 */
public class ConfigParam {

    /**
     * Nombre del parametro.
     */
    private String name = "";

    /**
     * Valor por defecto del parametro.
     */
    private String defaultValue = null;

    /**
     * Constructor con el nombre del parametro.
     * 
     * @param name - Nombre del parametro.
     */
    public ConfigParam(final String name) {
        this.name = name;
    }

    /**
     * Constructor con valor por defecto.
     * 
     * @param name - Nombre del parametro.
     * @param defaultValue - Valor por defecto.
     */
    public ConfigParam(final String name, final String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    /**
     * Obtiene el identificador del parametro.
     * 
     * @return el identificador del parametro.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Obtiene el valor por defecto del parametro.
     * 
     * @return el valor por defecto del parametro o una cadena vacia si no tiene valor por defecto.
     */
    public final String getDefaultValue() {
        return this.defaultValue;
    }

}