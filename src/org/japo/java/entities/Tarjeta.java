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

/**
 *
 * @author Jonsui
 */
public class Tarjeta {

  public static final String DEF_NUMERO = "DEF_NUMERO";
  public static final double DEF_CREDITO = 500;
  public static final double DEF_DEBITO = 500;

  private String numero;
  private double debito;
  private double credito;

  public Tarjeta() {
    numero = DEF_NUMERO;
    debito = DEF_DEBITO;
    credito = DEF_CREDITO;
  }

  public Tarjeta(String numero) {
    this.numero = numero;
  }

  public Tarjeta(String numero, double debito, double credito) {
    this.numero = numero;
    this.debito = debito;
    this.credito = credito;
  }

  public String getNumero() {
    return numero;
  }

  public double getDebito() {
    return debito;
  }

  public double getCredito() {
    return credito;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public void setDebito(double debito) {
    this.debito = debito;
  }

  public void setCredito(double credito) {
    this.credito = credito;
  }

  @Override
  public String toString() {
    return String.format("%nDATOS DE TARJETA:%n"
            + "---------------%n"
            + "Número .........: %s%n"
            + "Débito ........: %.2f%n"
            + "Crédito .......: %.2f%n", getNumero(),
            getDebito(), getCredito());
  }

  public void muestraTarjeta() {
    System.out.println(toString());
  }
}
