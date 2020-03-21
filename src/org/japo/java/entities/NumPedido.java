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
package org.japo.java.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jonsui, reciclando Matrícula. Sin definir todavía...
 * 
 */
public class NumPedido implements Serializable {

  private static final long serialVersionUID = 4052803894942037078L;

  public static final int MATRICULA_LEN = 8;
  public static final int DIGI_LEN = 4;
  public static final int CHAR_LEN = 3;
  public static final String LISTA_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String LISTA_SHOR = "ABCDEFGHIJKL";
  public static final String LISTA_DIGI = "0123456789";

  //                  formato de la matrícula: 1111-XXX
  public static final String EXP_MATRICULA = "^[0-9]{4}-[A-L]{2}|ENV|$";

  String numPedido;

  public NumPedido() {
    numPedido = generaNumPedido();
  }
//En este constr se debería validar la matrícula

  public NumPedido(String numPedido) {
    if (numPedido.matches(EXP_MATRICULA)) {
      this.numPedido = numPedido;
    } else {
      this.numPedido = generaNumPedido();
    }
  }

  public String getNumPedido() {
    return numPedido;
  }

  public void setNumPedido(String numPedido) {
    if (numPedido.matches(EXP_MATRICULA)) {
      this.numPedido = numPedido;
    }
  }

  @Override
  public boolean equals(Object o) {
    boolean testOk;
    if (o == null) {
      testOk = false;
    } else if (!(o instanceof NumPedido)) {
      testOk = false;
    } else {
      testOk = numPedido.equals(((NumPedido) o).getNumPedido());
    }
    return testOk;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 89 * hash + Objects.hashCode(this.numPedido);
    return hash;
  }

  @Override
  public String toString() {
    return "NumPedido [" + numPedido + "]";
  }
  
  public String verNumPedido(){
    return this.numPedido; 
  }
  
  
  //Se genera un codigo llamando al constructor por defecto o cuando
  //al parametrizado se le pasa un formato fuera del correcto.
  public static final String generaNumPedido() {
    StringBuilder sb = new StringBuilder(MATRICULA_LEN);
    //Generamos 4 números 
    for (int i = 0; i < MATRICULA_LEN - CHAR_LEN - 1; i++) {
      int index = (int) (LISTA_DIGI.length() * Math.random());
      sb.append(LISTA_DIGI.charAt(index));
    }
    //Añadimos el guión 
    sb.append('-');
    //Resto
    for (int i = 0; i < MATRICULA_LEN - DIGI_LEN - 2; i++) {
      int index = (int) (LISTA_SHOR.length() * Math.random());
      sb.append(LISTA_CHAR.charAt(index));
    }
    sb.append("|ENV|");
    return sb.toString();
  }


}