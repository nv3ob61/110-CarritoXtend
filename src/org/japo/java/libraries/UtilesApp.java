/* 
 * Copyright (C) 2020 mon_mode   0mon.mode@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.japo.java.libraries;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class UtilesApp {

    // Valores por Defecto
    public static final String DEF_FICHERO_PRP = "app.properties";
    public static final String DEF_FICHERO_XML = "app.xml";
    public static final String DEF_PUERTO_BLOQUEO = "54321";

    // Fichero (Por defecto) > Propiedades    
    public static final Properties importarPropiedades() {
        return importarPropiedades(DEF_FICHERO_PRP);
    }

    // Fichero XML (Por defecto) > Propiedades    
    public static final Properties importarPropiedadesXML() {
        return importarPropiedadesXML(DEF_FICHERO_XML);
    }

    // Fichero Propiedades > Objeto Propiedades
    public static final Properties importarPropiedades(String fichero) {
        // Objeto de Propiedades Vacio
        Properties prp = new Properties();

        // Cargar Fichero de Propiedades 
        try (FileReader fr = new FileReader(fichero)) {
            prp.load(fr);
        } catch (Exception e) {
            System.out.println("ERROR: Acceso al fichero " + fichero);
        }

        // Devolver Propiedades
        return prp;
    }

    // Fichero Propiedades XML > Objeto Propiedades
    public static final Properties importarPropiedadesXML(String fichero) {
        // Objeto de Propiedades Vacio
        Properties prp = new Properties();

        // Cargar Fichero de Propiedades 
        try (FileInputStream fisXml = new FileInputStream(fichero)) {
            // Carga las propiedades
            prp.loadFromXML(fisXml);
        } catch (Exception e) {
            System.out.println("ERROR: Acceso al fichero " + fichero);
        }

        // Devolver Propiedades
        return prp;
    }

    // Propiedades > Fichero (Por defecto)
    public static final boolean exportarPropiedades(Properties prp) {
        return exportarPropiedades(prp, DEF_FICHERO_PRP);
    }

    // Propiedades > Fichero XML (Por defecto)
    public static final boolean exportarPropiedadesXML(Properties prp) {
        return exportarPropiedadesXML(prp, DEF_FICHERO_PRP);
    }

    // Propiedades > Fichero
    public static final boolean exportarPropiedades(Properties prp, String fichero) {
        // Semáforo Estado
        boolean procesoOK = false;

        // Proceso de salvaguarda de propiedades
        try (FileWriter fw = new FileWriter(fichero)) {
            // Guarda las propiedades
            prp.store(fw, null);

            // Proceso OK
            procesoOK = true;
        } catch (Exception e) {
            // Mensaje de error
            System.out.println("ERROR: Acceso al fichero " + fichero);
        }

        // Devuelve Estado
        return procesoOK;
    }

    // Propiedades > Fichero XML
    public static final boolean exportarPropiedadesXML(Properties prp, String fichero) {
        // Semáforo Estado
        boolean procesoOK = false;

        // Proceso de salvaguarda de propiedades
        try (FileOutputStream fosXml = new FileOutputStream(fichero)) {
            // Guarda las propiedades
            prp.storeToXML(fosXml, null);

            // Proceso OK
            procesoOK = true;
        } catch (Exception e) {
            // Mensaje de error
            System.out.println("ERROR: Acceso al fichero " + fichero);
        }

        // Devuelve Estado
        return procesoOK;
    }

    // Activa Instancia Única
    public static final boolean activarInstancia(Properties prp) {
        // Semaforo Estado
        boolean instanciaOK = false;

        try {
            // Obtener dato
            String dato = prp.getProperty("puerto_bloqueo", DEF_PUERTO_BLOQUEO);

            // Puerto de bloqueo
            int puerto = Integer.parseInt(dato);

            // Abre un ServerSocket al puerto de bloqueo
            ServerSocket ss = new ServerSocket(puerto);

            // Marca Semáforo
            instanciaOK = true;
        } catch (IOException | NumberFormatException e) {
            System.out.println("ERROR: Activación ejecución única");
        }

        // Devuelve Estado
        return instanciaOK;
    }

    // Activa Instancia Única
    public static final boolean activarInstancia(String txtPuerto) {
        // Semaforo Estado
        boolean instanciaOK = false;

        try {
            // Conversión numérica
            int puerto = Integer.parseInt(txtPuerto);

            // Abre un ServerSocket al puerto de bloqueo
            ServerSocket ss = new ServerSocket(puerto);

            // Marca Semáforo
            instanciaOK = true;
        } catch (IOException | NumberFormatException e) {
            System.out.println("ERROR: Activación ejecución única");
        }

        // Devuelve Estado
        return instanciaOK;
    }
}
