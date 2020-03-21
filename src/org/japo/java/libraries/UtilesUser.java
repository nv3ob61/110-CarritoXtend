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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.japo.java.entities.Tarjeta;
import org.japo.java.entities.User;
import org.japo.java.enumerations.IdRol;

/**
 *
 * @author Jonsui
 */
public class UtilesUser {

  //Método que busca user por el nombre de user
  public static User buscaUser(String nombre, List<User> lista) {
    User i;
    User enc = null;
    boolean exit = false;
    Iterator<User> it = lista.iterator();

    while (exit == false && it.hasNext()) {
      i = it.next();
      if (i.getUser().equals(nombre)) {
        exit = true;
        enc = i;
      }
    }
    return enc;
  }

  public static Tarjeta buscaTarjeta(String nombre, HashSet<Tarjeta> lista) {
    Tarjeta t;
    Tarjeta enc = null;
    boolean exit = false;
    Iterator<Tarjeta> it = lista.iterator();
    while (exit == false && it.hasNext()) {
      t = it.next();
      if (t.getNumero().equals(nombre)) {
        exit = true;
        enc = t;
      }
    }
    return enc;
  }

  //comprueba si el nombre de usuario está en la lista
  public static boolean esIdentidad(String nombre, List<User> lista) {
    boolean isOk = false;
    User u;
    Iterator<User> it = lista.iterator();
    while (isOk == false && it.hasNext()) {
      u = it.next();
      if (u.getUser().equals(nombre)) {
        isOk = true;
      }
    }
    return isOk;
  }

  //comprueba si el nombre de usuario está en la lista  + passOk!
  public static boolean esIdentidad(String nombre, char[] pass, List<User> lista) {
    boolean isOk = false;
    User u;
    Iterator<User> it = lista.iterator();
    while (isOk == false && it.hasNext()) {
      u = it.next();
      if (u.getUser().equals(nombre)
              && Arrays.equals(u.getPass(), pass)) {
        isOk = true;
      }
    }
    return isOk;
  }

  public static final int muestraInactivos(List<User> lista) {
    int cont = 0;
    for (User user : lista) {
      if (!user.isCuentaActiva()) {
        user.muestraPersona();
        System.out.println();
        cont++;
      }
    }
    return cont;
  }

  public static final void activaCuenta(User u) {
    u.setCuentaActiva(true);
  }

  public static final void desactivaCuenta(User u) {
    u.setCuentaActiva(false);
  }

  public static final int totalTarjetas(User u) {
    return u.getMonedero().size();
  }

  public static final double totalSaldoTarjetas(User u, HashSet<Tarjeta> mone) {
    double saldo = 0;
    for (Tarjeta tarjeta : u.getMonedero()) {
      saldo += totalPortarjeta(tarjeta);
    }

    return saldo;
  }

  public static final double totalPortarjeta(Tarjeta t) {
    return t.getCredito() + t.getDebito();
  }

  public static final boolean addTarjeta(User u, Tarjeta t) {
    boolean isOk = false;

    if (totalTarjetas(u) > -1 && totalTarjetas(u) < 2) {
      u.getMonedero().add(t);
      isOk = true;
    }
    return isOk;
  }

  public static final boolean delTarjeta(User u, Tarjeta t) {
    boolean isOk = false;

    if (totalTarjetas(u) > 0) {
      u.getMonedero().remove(t);
      isOk = true;
    }
    return isOk;
  }

  public static void pideDatosUser(User u, User aux) {
    String ent;
    do {
      ent = UtilesEntrada.leerTexto("Nombre ..:");
      if (!ent.equals("")) {
        aux.setNombre(ent);
      }
    } while (aux.getNombre().equals(u.getNombre()) && !ent.equals(""));

    // Consola > 1er Apellido
    do {
      aux.setApellido1(UtilesEntrada.leerTexto("1er Apellido ..:"));
    } while (aux.getApellido1().equals(u.getApellido1()));

    // Consola > 2o Apellido
    do {
      aux.setApellido2(UtilesEntrada.leerTexto("2o Apellido ...:"));
    } while (aux.getApellido2().equals(u.getApellido2()));

    // Consola > DNI
    do {
      aux.setDni(UtilesEntrada.leerTexto("DNI ...:"));
    } while (aux.getDni().equals(u.getDni()));

    // Consola > Rol (Solo para el admin) CAMBIAR EL ROL
    if (aux.getRol().equals(IdRol.ADMIN)) {
      do {
        String nombreRol = UtilesEntrada.leerTexto("Rol ..: ");
        IdRol rol = UtilesEnums.generarRol(nombreRol.toLowerCase());
        aux.setRol(rol);
      } while (aux.getRol().equals(User.DEF_ID_ROL_USER));
    } else {  //el usuario siempre es user.
      aux.setRol(IdRol.USER);
    }
    //Consola > activa/desactiva cuenta 
    if (u.isCuentaActiva()) {
      System.out.println();
      if (UtilesEntrada.confirmarProceso("¿DESACTIVAR cuenta? (S/n) ...: ", true)) {
        aux.setCuentaActiva(false);
        UtilesEntrada.hacerPausa("Cuenta DESACTIVADA correctamente.");
      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Modificación de Estado CANCELADA");
      }
    } else {  //si la cuenta no está activa. para el admin.
      System.out.println();
      if (aux.getRol().equals((IdRol.ADMIN))) {
        if (UtilesEntrada.confirmarProceso("¿ACTIVAR cuenta? (S/n) ...: ", true)) {
          aux.setCuentaActiva(true);
          UtilesEntrada.hacerPausa("Cuenta ACTIVADA correctamente.");
        } else {
          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Modificación de Estado CANCELADA");
        }
      }
    }
    //copiamos el monedero
    aux.setConectado(true);
    aux.setMonedero(u.getMonedero());
    // Separador   
    System.out.println("---");
  }
}
