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

import java.util.HashSet;
import java.util.Iterator;
import org.japo.java.libraries.UtilesDNI;
import org.japo.java.libraries.UtilesValidacion;

/**
 *
 * @author Jonsui
 *
 * No añadimos la implementación de Comparable a esta entidad ya que es abstract
 */
public abstract class Persona {

  //valores predeterminados de la entidad Persona
  public static final String DEF_NOMBRE = "DEF_NOMBRE";
  public static final String DEF_APELL1 = "DEF_APELL1";
  public static final String DEF_APELL2 = "DEF_APELL2";
  public static final String DEF_DNI = "12345678Z";

  //Expresiones regulares, la primera valida nombre y apellidos, máx 50 caract.
  public static final String REG_NOMBRE_PERS = "[a-zA-ZñÑ áéíóúÁÉÍÓÚüÜ]{1,50}";
  public static final String REG_DNI
          = "[0-9]{8}[T|R|W|A|G|M|Y|F|P|D|X|B|N|J|Z|S|Q|V|H|L|C|K|E]";

  //Campos
  private String nombre;
  private String apellido1;
  private String apellido2;
  private String dni;
  private HashSet<Tarjeta> monedero = new HashSet<>();

  //Constructor Predet. Crea persona con monedero.
  public Persona() {
    nombre = DEF_NOMBRE;
    apellido1 = DEF_APELL1;
    apellido2 = DEF_APELL2;
    dni = DEF_DNI;
    monedero = new HashSet<>();
  }

  //Constructor parametrizado sin pasarle el monedero, viene asignado 
  //automáticamente cuando se genera una persona.
  public Persona(String nombre,
          String apellido1, String apellido2,
          String dni) {
    if (UtilesValidacion.validarNombreReal(nombre)) {
      this.nombre = nombre;
    } else {
      this.nombre = DEF_NOMBRE;
    }

    if (UtilesValidacion.validar(apellido1, REG_NOMBRE_PERS)) {
      this.apellido1 = apellido1;
    } else {
      this.apellido1 = DEF_APELL1;
    }

    if (UtilesValidacion.validar(apellido2, REG_NOMBRE_PERS)) {
      this.apellido2 = apellido2;
    } else {
      this.apellido2 = DEF_APELL2;
    }

    //Pasa el filtro de la expresión reg y luego valida si el num es correcto
    if (UtilesValidacion.validar(dni, REG_DNI)) {
      if (UtilesDNI.validar(Integer.parseInt(dni.substring(0, 8)),
              dni.charAt(dni.length() - 1))) {
        this.dni = dni;
      } else {
        this.dni = DEF_DNI;
      }
    } else {
      this.dni = DEF_DNI;
    }

    this.monedero = new HashSet<>();

  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido1() {
    return apellido1;
  }

  public String getApellido2() {
    return apellido2;
  }

  public String getDni() {
    return dni;
  }

  public void setNombre(String nombre) {
    if (UtilesValidacion.validarNombreReal(nombre)) {
      this.nombre = nombre;
    }
  }

  public void setApellido1(String apellido1) {
    if (UtilesValidacion.validar(apellido1, REG_NOMBRE_PERS)) {
      this.apellido1 = apellido1;
    }
  }

  public void setApellido2(String apellido2) {
    if (UtilesValidacion.validar(apellido2, REG_NOMBRE_PERS)) {
      this.apellido2 = apellido2;
    }
  }

  //añadir validación
  public void setDni(String dni) {
    if (UtilesValidacion.validar(dni, REG_DNI)) {
      if (UtilesDNI.validar(Integer.parseInt(dni.substring(0, 8)),
              dni.charAt(dni.length() - 1))) {
        this.dni = dni;
      }
    }
  }

  //Comprobar esto?
  public HashSet<Tarjeta> getMonedero() {
    return monedero;
  }

  public void setMonedero(HashSet<Tarjeta> monedero) {
    this.monedero = monedero;
  }

  @Override
  public String toString() {
    return String.format("%nDATOS DE LA PERSONA:%n"
            + "---------------%n"
            + "NOMBRE ....: %s%n"
            + "APELLIDOS .: %s %s%n"
            + "DNI .......: %s%n"
            + "TARJETAS ..: %d%n"
            + "SALDO .....: %.2f€%n", getNombre(), getApellido1(),
            getApellido2(), getDni(), monedero.size(), totalSaldoMonedero());
  }

  public void muestraPersona() {
    System.out.println(toString());
  }

  public double totalSaldoMonedero() {
    double total = 0;
    Tarjeta t;

    Iterator<Tarjeta> it = monedero.iterator();
    while (it.hasNext()) {
      t = it.next();
      total += t.getCredito() + t.getDebito();
    }

    return total;
  }

}
