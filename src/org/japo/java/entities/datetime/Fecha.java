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
package org.japo.java.entities.datetime;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class Fecha implements Serializable {

    // Separador de fecha
    public static final String SEPARADOR = "/";

    // Nombres de meses
    public static final String MESES[] = {"enero", "febrero", "marzo",
        "abril", "mayo", "junio", "julio", "agosto", "septiembre",
        "octubre", "noviembre", "diciembre"};

    // Nombres de dias
    public static final String DIAS[] = {"lunes", "martes", "miércoles",
        "jueves", "viernes", "sábado", "domingo"};

    // Campos de la fecha
    private int dia;
    private int mes;
    private int any;

    public Fecha() {
        // Instancia un objeto GregorianCalendar
        GregorianCalendar gc = new GregorianCalendar();

        // Obtiene la fecha del sistema
        dia = gc.get(Calendar.DAY_OF_MONTH);
        mes = gc.get(Calendar.MONTH) + 1;
        any = gc.get(Calendar.YEAR);
    }

    public Fecha(int dia, int mes, int any) {
        if (validarFecha(dia, mes, any)) {
            this.dia = dia;
            this.mes = mes;
            this.any = any;
        } else {
            // Instancia un objeto GregorianCalendar
            GregorianCalendar gc = new GregorianCalendar();

            // Obtiene la fecha del sistema
            this.dia = gc.get(Calendar.DAY_OF_MONTH);
            this.mes = gc.get(Calendar.MONTH) + 1;
            this.any = gc.get(Calendar.YEAR);
        }
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        if (validarFecha(dia, mes, any)) {
            this.dia = dia;
        }
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if (validarFecha(dia, mes, any)) {
            this.mes = mes;
        }
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        if (validarFecha(dia, mes, any)) {
            this.any = any;
        }
    }

    public static Fecha parseFecha(String textoFecha) throws NumberFormatException {
        // Referencia al objeto a devolver
        Fecha fecha = new Fecha();

        // Crea un objeto StringTokenizer a partir del dato
        StringTokenizer st = new StringTokenizer(textoFecha.trim(), SEPARADOR);

        // Analiza el objeto creado
        if (st.countTokens() == 3) {
            // Dia
            int diaAct = Integer.parseInt(st.nextToken().trim());

            // Mes
            int mesAct = Integer.parseInt(st.nextToken().trim());

            // Año
            int anyAct = Integer.parseInt(st.nextToken().trim());

            // Valida la fecha
            if (validarFecha(diaAct, mesAct, anyAct)) {
                fecha = new Fecha(diaAct, mesAct, anyAct);
            } else {
                throw new NumberFormatException();
            }
        } else {
            throw new NumberFormatException();
        }

        // Devuelve la fecha generada
        return fecha;
    }

    public static boolean validarFecha(int diaTest, int mesTest, int anytest) {
        boolean fechaOK = false;
        if (mesTest == 2) {
            if (validarBisiesto(anytest) && diaTest == 29) {
                fechaOK = true;
            } else if (diaTest >= 1 && diaTest <= 28) {
                fechaOK = true;
            }
        } else if (mesTest == 4 || mesTest == 6 || mesTest == 9
                || mesTest == 11) {
            if (diaTest >= 1 && diaTest <= 30) {
                fechaOK = true;
            }
        } else if (mesTest == 1 || mesTest == 3 || mesTest == 5
                || mesTest == 7 || mesTest == 8 || mesTest == 10
                || mesTest == 12) {
            if (diaTest >= 1 && diaTest <= 31) {
                fechaOK = true;
            }
        }
        return fechaOK;
    }

    public static boolean validarBisiesto(int anytest) {
        return anytest % 400 == 0 || anytest % 100 != 0 && anytest % 4 == 0;
    }

    public static Fecha obtenerFechaSistema() {
        // Instancia un objeto GregorianCalendar
        GregorianCalendar gc = new GregorianCalendar();

        // Obtiene la fecha del sistema
        int diaAct = gc.get(Calendar.DAY_OF_MONTH);
        int mesAct = gc.get(Calendar.MONTH) + 1;
        int anyAct = gc.get(Calendar.YEAR);

        // Instancia un fecha con los valores obtenidos
        return new Fecha(diaAct, mesAct, anyAct);
    }

    public String obtenerFechaCorta() {
        return String.format("%02d" + SEPARADOR + "%02d" + SEPARADOR + "%d",
                dia, mes, any);
    }

    public String obtenerFechaLarga() {
        return String.format("%d de %s de %d\n", dia, MESES[mes - 1], any);
    }
    
    public String obtenerNombreDia() {
        // Instancia un objeto GregorianCalendar
        GregorianCalendar gc = new GregorianCalendar(any, mes, dia);
        
        // Almacena el dia de la semana: dom = 1 - sab = 7
        int diaSemana = gc.get(Calendar.DAY_OF_WEEK);
        
        // Obtiene el nombre del dia
        String nombreDia = "desconocido";
        switch (diaSemana) {
            case Calendar.MONDAY:
                nombreDia = DIAS[0];
                break;
            case Calendar.TUESDAY:
                nombreDia = DIAS[1];
                break;
            case Calendar.WEDNESDAY:
                nombreDia = DIAS[2];
                break;
            case Calendar.THURSDAY:
                nombreDia = DIAS[3];
                break;
            case Calendar.FRIDAY:
                nombreDia = DIAS[4];
                break;
            case Calendar.SATURDAY:
                nombreDia = DIAS[5];
                break;
            case Calendar.SUNDAY:
                nombreDia = DIAS[6];
        }
        
        
        // Devuelve el nombre del dia
        return nombreDia;

        // Convierte el dia de la semana lun = 1 - dom = 7
//        diaSemana = (diaSemana + 5) % 7 + 1;
        
        // Obtiene el nombre del dia en la cuantificacion de España
//        return DIAS[diaSemana - 1];
    }

    @Override
    public String toString() {
        return obtenerFechaCorta();
    }

    
    @Override
    public boolean equals(Object o) {
        // Semáforo de señalización
        boolean fechaOK = false;

        // Análisis del objeto pasado
        if (o instanceof Fecha) {
            // Convierte el objeto actual a fecha
            Fecha f = (Fecha) o;

            // Compara los valores de la fecha
            fechaOK = dia == f.dia && mes == f.mes && any == f.any;
        }

        // DEvuelve el resultado del análisis
        return fechaOK;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.dia;
        hash = 67 * hash + this.mes;
        hash = 67 * hash + this.any;
        return hash;
    }
}
