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

import java.util.Calendar;
import java.util.GregorianCalendar;

public final class Hora {

  // Variables
  private int h;
  private int m;
  private int s;

  // Constructor predeterminado
  public Hora() {
    establecerHoraSistema();
  }

  // Constructor parametrizado
  public Hora(int h, int m, int s) {
    if (validarHora(h, m, s)) {
      this.h = h;
      this.m = m;
      this.s = s;
    } else {
      establecerHoraSistema();
    }
  }

  // --- Inicio encapsulación
  //
  public int getH() {
    return h;
  }

  public void setH(int h) {
    if (h >= 0 && h <= 23) {
      this.h = h;
    }
  }

  public int getM() {
    return m;
  }

  public void setM(int m) {
    if (m >= 0 && m <= 59) {
      this.m = m;
    }
  }

  public int getS() {
    return s;
  }

  public void setS(int s) {
    if (s >= 0 && s <= 59) {
      this.s = s;
    }
  }

  // --- Fin encapsulación
  //
  public static boolean validarHora(int h, int m, int s) {
    return h >= 0 && h <= 23
            && m >= 0 && m <= 59
            && s >= 0 && s <= 59;
  }

  public void mostrarHora() {
    System.out.println(toString());
  }

  void establecerHoraSistema() {
    // Instancia objeto GregorianCalendar
    GregorianCalendar gc = new GregorianCalendar();

    // Obtener la hora
    h = gc.get(Calendar.HOUR_OF_DAY);
    m = gc.get(Calendar.MINUTE);
    s = gc.get(Calendar.SECOND);
  }

  // --- Inicio de sobrecarga de métodos
  //
  @Override
  public boolean equals(Object o) {
    // Semaforo de resultado
    boolean testOK;

    // Análisis del objeto
    if (o == null) {
      testOK = false;
    } else if (!(o instanceof Hora)) {
      testOK = false;
    } else {
      Hora hora = (Hora) o;
      testOK = this.h == hora.h
              && this.m == hora.m
              && this.s == hora.s;
    }

    // Devolución del resultado
    return testOK;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + this.h;
    hash = 79 * hash + this.m;
    hash = 79 * hash + this.s;
    return hash;
  }

  @Override
  public String toString() {
    return String.format("%02d:%02d:%02d", h, m, s);
  }

}
