package com.elpaso.serfj;

import java.io.IOException;
import java.util.Map;

/**
 * Interfaz básico para hacer un controlador REST. No es necesario usar esta interfaz
 * para que la librería funcione, pero te asegura el tener las acciones básicas.
 * 
 * @author eyp
 *
 */
public interface BasicRestController {
    /**
     * Es la acción por defecto cuando no se pide ningún recurso en concreto.
     * HTTP_METHOD => GET
     * 
     * @param params - Parámetros (identificadores de recursos) que venían en la URL. 
     */
    void index(ResponseHelper response, Map<String, String> params) throws IOException;
    
    /**
     * Muestra los datos el recurso solicitado.
     * HTTP_METHOD => GET
     * 
     * @param params - Parámetros (identificadores de recursos) que venían en la URL. 
     */
    void show(ResponseHelper response, Map<String, String> params) throws IOException;

    /**
     * Muestra la pantalla con el formulario adecuado para insertar los datos que se usarán
     * para crear un nuevo recurso.
     * HTTP_METHOD => GET
     * 
     * @param params - Parámetros (identificadores de recursos) que venían en la URL. 
     */
    void newResource(ResponseHelper response, Map<String, String> params) throws IOException;
    
    /**
     * Muestra la pantalla con el formulario adecuado para editar los datos que se usarán
     * para modificar un recurso concreto.
     * HTTP_METHOD => GET
     * 
     * @param params - Parámetros (identificadores de recursos) que venían en la URL. 
     */
    void edit(ResponseHelper response, Map<String, String> params) throws IOException;
    
    /**
     * Cera un nuevo recurso.
     * HTTP_METHOD => POST
     * 
     * @param params - Parámetros (identificadores de recursos) que venían en la URL. 
     */
    void create(ResponseHelper response, Map<String, String> params) throws IOException;
    
    /**
     * Modifica un recurso.
     * HTTP_METHOD => PUT
     * 
     * @param params - Parámetros (identificadores de recursos) que venían en la URL. 
     */
    void update(ResponseHelper response, Map<String, String> params) throws IOException;
    
    /**
     * Elimina un recurso.
     * HTTP_METHOD => DELETE
     * 
     * @param params - Parámetros (identificadores de recursos) que venían en la URL. 
     */
    void delete(ResponseHelper response, Map<String, String> params) throws IOException;
}
