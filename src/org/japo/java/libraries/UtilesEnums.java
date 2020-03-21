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

import java.awt.Color;
import org.japo.java.enumerations.EstadoItem;
import org.japo.java.enumerations.EstadoPedido;
import org.japo.java.enumerations.IdRol;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class UtilesEnums {

  public static final String obtenerNombreColor(Color color) {
    String nombreColor;
    if (color == null) {
      nombreColor = "indefinido";
    } else if (color.equals(Color.BLACK)) {
      nombreColor = "negro";
    } else if (color.equals(Color.BLUE)) {
      nombreColor = "azul";
    } else if (color.equals(Color.CYAN)) {
      nombreColor = "celeste";
    } else if (color.equals(Color.GRAY)) {
      nombreColor = "gris";
    } else if (color.equals(Color.GREEN)) {
      nombreColor = "verde";
    } else if (color.equals(Color.MAGENTA)) {
      nombreColor = "púrpura";
    } else if (color.equals(Color.ORANGE)) {
      nombreColor = "naranja";
    } else if (color.equals(Color.PINK)) {
      nombreColor = "rosa";
    } else if (color.equals(Color.RED)) {
      nombreColor = "rojo";
    } else if (color.equals(Color.WHITE)) {
      nombreColor = "blanco";
    } else if (color.equals(Color.YELLOW)) {
      nombreColor = "amarillo";
    } else {
      nombreColor = "indefinido";
    }
    return nombreColor;
  }

  public static Color generarColor(String nombreColor) {
    Color c;
    if (null == nombreColor) {
      c = Color.BLACK;
    } else {
      switch (nombreColor) {
        case "negro":
          c = Color.BLACK;
          break;
        case "azul":
          c = Color.BLUE;
          break;
        case "celeste":
          c = Color.CYAN;
          break;
        case "gris":
          c = Color.GRAY;
          break;
        case "verde":
          c = Color.GREEN;
          break;
        case "púrpura":
          c = Color.MAGENTA;
          break;
        case "naranja":
          c = Color.ORANGE;
          break;
        case "rosa":
          c = Color.PINK;
          break;
        case "rojo":
          c = Color.RED;
          break;
        case "blanco":
          c = Color.WHITE;
          break;
        case "amarillo":
          c = Color.YELLOW;
          break;
        default:
          c = Color.BLACK;
          break;
      }
    }
    return c;
  }

  public static IdRol generarRol(String rol) {
    IdRol r;
    if (null == rol) {
      r = IdRol.USER;
    } else {
      switch (rol) {
        case "user":
        case "usuario":
        case "Usuario":
        case "User":
          r = IdRol.USER;
          break;
        case "admin":
        case "Admin":
        case "administrador":
        case "Administrador":
          r = IdRol.ADMIN;
          break;
        default:
          r = IdRol.USER;
      }
    }
    return r;
  }

  public static final String obtenerNombreRol(IdRol idRol) {
    String rol;

    if (idRol == null) {
      rol = "indefinido";
    } else if (idRol.equals(IdRol.USER)) {
      rol = "user";
    } else if (idRol.equals(IdRol.ADMIN)) {
      rol = "admin";
    } else {
      rol = "indefinido";
    }
    return rol;
  }

  public static final String obtenerNombreEstadoPedido(EstadoPedido eP) {
    String r;

    if (eP == null) {
      r = "indefinido";
    } else if (eP.equals(EstadoPedido.VERIFICAR_PAGO)) {
      r = "verificar_pago";
    } else if (eP.equals(EstadoPedido.PROCESAR_PEDIDO)) {
      r = "procesar_pedido";
    } else if (eP.equals(EstadoPedido.ENVIADO)) {
      r = "enviado";
    } else {
      r = "indefinido";
    }
    return r;
  }

  public static final String obtenerNombreEstadoItem(EstadoItem ei) {
    String r;

    if (ei == null) {
      r = "indefinido";
    } else if (ei.equals(EstadoItem.DISPONIBLE)) {
      r = "disponible";
    } else if (ei.equals(EstadoItem.NO_DISPONIBLE)) {
      r = "no_disponible";
    } else if (ei.equals(EstadoItem.EN_CARRITO)) {
      r = "en_carrito";
    } else {
      r = "indefinido";
    }
    return r;
  }

  public static final EstadoItem generarEstadoItem(String estado) {
    EstadoItem ei;  // Hey! xD

    if (null == estado) {
      ei = EstadoItem.DISPONIBLE;
    } else {
      switch (estado) {
        case "disp":
        case "disponible":
          ei = EstadoItem.DISPONIBLE;
          break;
        case "nodisp":
        case "no_disp":
        case "nodisponible":
        case "no_disponible":
          ei = EstadoItem.NO_DISPONIBLE;
          break;
        case "en_carrito":
        case "encarrito":
        case "en carrito":
        case "en":
          ei = EstadoItem.EN_CARRITO;
          break;
        default:
          ei = EstadoItem.DISPONIBLE;
      }
    }
    return ei;
  }

  public static EstadoPedido generarEstadoPedido(String rol) {
    EstadoPedido ep;
    if (null == rol) {
      ep = EstadoPedido.VERIFICAR_PAGO;
    } else {
      switch (rol) {
        case "verificar":
        case "verificación":
        case "verificar pago":
        case "verificar_pago":
        case "pago":
          ep = EstadoPedido.VERIFICAR_PAGO;
          break;
        case "procesar":
        case "procesar_pedido":
        case "procesar pedido":
        case "pedido":
          ep = EstadoPedido.PROCESAR_PEDIDO;
          break;
        case "enviado":
          ep = EstadoPedido.ENVIADO;
          break;
        default:
          ep = EstadoPedido.VERIFICAR_PAGO;
      }
    }
    return ep;
  }

  public static final String obtenerNombreActivo(boolean estado) {
    String estando;

    if (estado == true) {
      estando = "true";
    } else {
      estando = "false";
    }
    return estando;
  }
}
