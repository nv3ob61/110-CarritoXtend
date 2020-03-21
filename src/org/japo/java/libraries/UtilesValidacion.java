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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.japo.java.entities.Item;
import org.japo.java.entities.Persona;
import org.japo.java.entities.User;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class UtilesValidacion {

    // Dato + Expresión Regular
    public static final boolean validar(String dato, String er) {
        // Semáforo de validación
        boolean testOK = false;

        // Proceso de validación
        try {
            // Compila la expresión regular
            Pattern patron = Pattern.compile(er);

            // Genera el motor de búsqueda
            Matcher detector = patron.matcher(dato);

            // Averiguar Coincidencia
            testOK = detector.matches();
        } catch (PatternSyntaxException | NullPointerException e) {
//            System.out.println(e);
        }

        // Devolver Semáforo
        return testOK;
    }

    // Texto + Expresión Regular
    public static final Matcher buscarPatron(String texto, String er) {
        // Compila la expresión regular
        Pattern patron = Pattern.compile(er);

        // Genera el motor de búsqueda
        Matcher detector = patron.matcher(texto);

        // Realiza la comprobación
        detector.find();

        // Retorno del resultado
        return detector;
    }

    // Texto + [Lista]
    public static final boolean validar(String dato, String[] lista) {
        Arrays.sort(lista);
        return Arrays.binarySearch(lista, dato) >= 0;
    }
    
//Booleans del RegExp
  public static boolean validarPrecio(String precio) {
    return validar(precio, Item.REG_ITEM_PRECIO);
  }

  public static final boolean validarNombreReal(String nombre){
    return nombre.matches(Persona.REG_NOMBRE_PERS);
  }
  public static final boolean validarNombreUser(String nombre) {
    return nombre.matches(User.REG_NOMBRE_USER);
  }

  public static final boolean validarPass(String pass) {
    return UtilesValidacion.validar(pass, User.REG_NOMBRE_PASS);
  }

  public static final boolean validaId(String id) {
    return validar(id, Item.REG_ITEM_ID);
  }
}
