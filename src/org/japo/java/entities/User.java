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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.japo.java.enumerations.IdRol;

/**
 *
 * @author Jonsui
 */
public class User extends Persona implements Comparable<User> {

  public static final String REG_NOMBRE_USER = "[a-zA-Z0-9]{1,12}";
  public static final String REG_NOMBRE_PASS = "[a-zA-Z0-9]{1,12}";
  public static final String DEF_NOMBRE_USER = "DEF_IDENTIDAD";
  public static final IdRol DEF_ID_ROL_USER = IdRol.DEF_ROL;
  public static final List<Pedido> DEF_PEDIDOS = new ArrayList<Pedido>();

  private String user;
  private char[] pass;
  private IdRol rol;
  private boolean cuentaActiva;
  private boolean conectado;
  private List<Pedido> pedidos;

  public User() {
    super();
    user = DEF_NOMBRE_USER;
    pass = user.toCharArray();
    rol = DEF_ID_ROL_USER;
    cuentaActiva = false;
    conectado = false;
    pedidos = DEF_PEDIDOS;
  }

  public User(String user, char[] pass) {
    super();
    this.user = user;
    this.pass = pass;
    this.rol = DEF_ID_ROL_USER;
    this.pedidos = DEF_PEDIDOS;
  }

  public User(String user, char[] pass, IdRol rol) {
    super();
    this.user = user;
    this.pass = pass;
    this.rol = rol;
    this.pedidos = DEF_PEDIDOS;
  }

  public User(String user, char[] pass,
          IdRol rol, boolean cuentaActiva, boolean conectado) {
    super();
    this.user = user;
    this.pass = pass;
    this.rol = rol;
    this.cuentaActiva = cuentaActiva;
    this.conectado = conectado;
    this.pedidos = DEF_PEDIDOS;
  }

  public User(String user, char[] pass, IdRol rol,
          boolean cuentaActiva, boolean conectado,
          String nombre, String apellido1, String apellido2, String dni) {
    super(nombre, apellido1, apellido2, dni);
    this.user = user;
    this.pass = pass;
    this.rol = rol;
    this.cuentaActiva = cuentaActiva;
    this.conectado = conectado;
    this.pedidos = DEF_PEDIDOS;
  }

  public User(String user, char[] pass, IdRol rol,
          boolean cuentaActiva, boolean conectado,
          String nombre,
          String apellido1, String apellido2,
          String dni, HashSet<Tarjeta> monedero) {
    super(nombre, apellido1, apellido2, dni);
    this.user = user;
    this.pass = pass;
    this.rol = rol;
    this.cuentaActiva = cuentaActiva;
    this.conectado = conectado;
    this.pedidos = DEF_PEDIDOS;
  }

  public String getUser() {
    return user;
  }

  public char[] getPass() {
    return pass;
  }

  public IdRol getRol() {
    return rol;
  }

  public boolean isCuentaActiva() {
    return cuentaActiva;
  }

  public boolean isConectado() {
    return conectado;
  }

  public void setConectado(boolean conectado) {
    this.conectado = conectado;
  }

  public void setCuentaActiva(boolean cuentaActiva) {
    this.cuentaActiva = cuentaActiva;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPass(char[] pass) {
    this.pass = pass;
  }

  public void setRol(IdRol rol) {
    this.rol = rol;
  }

  public boolean verificarIdentidad(User id) {
    return user.equals(id.getUser())
            && Arrays.equals(pass, id.getPass());
  }

  @Override
  public boolean equals(Object o) {
    boolean igualdadOK = false;
    if (o instanceof User) {
      igualdadOK = verificarIdentidad((User) o);
    }
    return igualdadOK;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 97 * hash + Arrays.hashCode(this.pass);
    return hash;
  }

  @Override
  public String toString() {
    return String.format(
            super.toString()
            + "%nDATOS DE LA ID:%n"
            + "---------------%n"
            + "USER .........: %s%n"
            + "PASSWORD .....: %s%n"
            + "ROL ..........: %s%n"
            + "CUENTA ACTIVA : %b%n"
            + "--------------------%n"
            + "... ONLINE ...: %b%n"
            + "--------------------%n", getUser(), Arrays.toString(getPass()),
            getRol(), isCuentaActiva(), isConectado());
  }

  @Override
  public void muestraPersona() {
    super.toString();
    System.out.println(toString());
  }

  @Override
  public int compareTo(User o) {
    return getUser().compareTo(o.getUser());
  }

}
