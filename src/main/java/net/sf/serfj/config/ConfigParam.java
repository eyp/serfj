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

/**
 * Clase en la que se definen los diferentes parametros de configuracion que se van a utilizar en el cliente, y los que
 * son comunes a los dos.
 * 
 * @author Eduardo Yáñez
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