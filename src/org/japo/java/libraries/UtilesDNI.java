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

/**
 *
 * @author mon_mo
 */
public class UtilesDNI {

    // Secuencia letras DNI
    public static final String SECUENCIA = "TRWAGMYFPDXBNJZSQVHLCKE";
    public static final int DNI_MINCHAR = 1;
    public static final int DNI_MAXCHAR = 8;
    public static final int NUM_MIN = 10000000;
    public static final int NUM_MAX = 99999999;

    // Calcula letra a partir del número de DNI
    public static final char calcular(int dni) {
        return SECUENCIA.charAt(dni % SECUENCIA.length());
    }

    //Método que valida la long del DNI.
    public static final boolean validar(int num) {
        return num <= DNI_MAXCHAR
                && num >= DNI_MINCHAR;
    }

    //método que valida que la letra está en la secuencia
    public static final boolean validar(char ctr) {
        boolean testOk = false;

        for (int i = 0; i < SECUENCIA.length(); i++) {
            if (SECUENCIA.charAt(i) == ctr) {
                testOk = true;
            }
        }
        return testOk;
    }

    //métdo que pasa un boolean si el DNI es correcto (en número y letra)
    public static final boolean validar(int num, char ctr) {
        return calcular(num) == ctr;
    }
}
