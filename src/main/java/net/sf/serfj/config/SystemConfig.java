/*
 * Copyright 2010 Eduardo Yáñez Parareda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.serfj.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lee la configuración de un sistema.
 * 
 * @author eduardo.yanez
 */
public class SystemConfig {

    /**
     * Log.
     */
    private static final Logger log = LoggerFactory.getLogger(SystemConfig.class);

    /**
     * Properties con la configuración leída.
     */
    private Properties props = null;

    /**
     * Nombre del fichero de configuración.
     */
    private String configFile = "";

    /**
     * Constructor.
     */
    public SystemConfig(final String filename) throws ConfigFileIOException {
        this.configFile = filename;
        this.init();
    }

    /**
     * Devuelve el valor del parametro como un Boolean. Evidentemente, si el valor del parametro no es un booleano, o no
     * se puede realizar la conversion, se lanzara una excepcion.
     * 
     * @param param -
     *            Parametro del que se quiere obtener el valor.
     * @return Boolean con el valor del parametro.
     */
    public Boolean getBoolean(final ConfigParam param) {
        return Boolean.valueOf(this.getValue(param));
    }

    /**
     * Devuelve el valor del parametro como un int. Evidentemente, si el valor del parametro no es un entero, o no se
     * puede realizar la conversion, se lanzara una excepcion.
     * 
     * @param param -
     *            Parametro del que se quiere obtener el valor.
     * @return int con el valor del parametro.
     */
    public int getInt(final ConfigParam param) {
        return Integer.valueOf(this.getValue(param));
    }

    /**
     * Devuelve el valor del parametro como un Long. Evidentemente, si el valor del parametro no es un entero, o no se
     * puede realizar la conversion, se lanzara una excepcion.
     * 
     * @param param -
     *            Parametro del que se quiere obtener el valor.
     * @return Long con el valor del parametro.
     */
    public long getLong(final ConfigParam param) {
        return Long.valueOf(this.getValue(param));
    }

    /**
     * Devuelve el valor del parametro como un String.
     * 
     * @param param -
     *            Parametro del que se quiere obtener el valor.
     * @return String con el valor del parametro.
     */
    public String getString(final ConfigParam param) {
        return this.getValue(param);
    }

    /**
     * Devuelve el valor del parametro de configuracion que se recibe. Si el valor es null y el parametro tiene un valor
     * por defecto, entonces este valor es el que se devuelve.
     * 
     * @param param - Parametro del que se quiere obtener el valor.
     * @return el valor del parametro.
     */
    public String getValue(final ConfigParam param) {
        if (param == null) {
            return null;
        }
        // Buscamos en las variables del sistema
        Object obj = System.getProperty(param.getName());
        // Si no, se busca en el fichero de configuracion
        if (obj == null) {
            obj = this.props.get(param.getName());
        }
        // Si tampoco esta ahi, y es un valor que tenga un valor por defecto,
        // se devuelve el valor por defecto
        if (obj == null) {
            log.warn("Property [{}] not found", param.getName());
            obj = param.getDefaultValue();
        }
        // Se devuelve el valor del parametro
        return (String) obj;
    }

    /**
     * Configuracion de la aplicacion. Se usa en debug.
     * 
     * @return un String con la informacion del objeto.
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("---- Configuration - ");
        buffer.append(this.configFile).append("\n");
        Enumeration<?> keys = this.props.keys();
        while (keys.hasMoreElements()) {
            String key = String.valueOf(keys.nextElement());
            buffer.append(key);
            buffer.append(" - ");
            buffer.append(this.props.get(key));
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * Devuelve la ruta del fichero de configuracion. Este metodo lo pueden sobreescribir las clases que deriven de
     * SystemConfig para modificar la ruta del fichero de configuracion. La ruta tiene que estar accesible desde el
     * classpath de la aplicacion.
     * 
     * @return Una cadena con la ruta al fichero de configuracion.
     */
    protected String getConfigFilePath() {
        return this.configFile;
    }

    /**
     * Inicializa la configuracion.
     */
    private void init() throws ConfigFileIOException {
        this.props = new Properties();
        log.info("Reading config file: {}", this.configFile);
        boolean ok = true;
        try {
            // El fichero esta en el CLASSPATH
            this.props.load(SystemConfig.class.getResourceAsStream(this.configFile));
        } catch (Exception e) {
            log.warn("File not found in the Classpath");
            try {
                this.props.load(new FileInputStream(this.configFile));
            } catch (IOException ioe) {
                log.error("Can't open file: {}", ioe.getLocalizedMessage());
                ok = false;
                ioe.printStackTrace();
                throw new ConfigFileIOException(ioe.getLocalizedMessage());
            }
        }
        if (ok) {
            log.info("Configuration loaded successfully");
            if (log.isDebugEnabled()) {
                log.debug(this.toString());
            }
        }
    }
}