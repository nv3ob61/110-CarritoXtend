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

import java.awt.Color;
import java.util.Locale;
import org.japo.java.enumerations.EstadoItem;
import org.japo.java.libraries.UtilesEnums;
import org.japo.java.libraries.UtilesValidacion;

/**
 * PARA EL AUTOR
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com, un crack!
 *
 *    aunque no me funciona el regex del nombre.
 */
public final class Item implements Comparable<Item> {

  // Valores Predeterminados
  public static final int DEF_ITEM_ID = 0;
  public static final String DEF_ITEM_NOMBRE = "DEF_ITEM";
  public static final double DEF_ITEM_PRECIO = 0.0;
  public static final Color DEF_ITEM_COLOR = Color.WHITE;
  public static final boolean DEF_LOCKED = false;
  public static final EstadoItem DEF_ESTADO_ITEM = EstadoItem.DISPONIBLE;

  // Expresiones Regulares
  public static final String REG_ITEM_ID = "\\d+";
  public static final String REG_ITEM_NOMBRE = "[ \\p{Alpha}áéíóúüñÁÉÍÓÚÑÜ]{3,}";
  public static final String REG_ITEM_PRECIO = "\\d+\\.\\d+";
  public static final String REG_ITEM_COLOR = "rojo|amarillo|verde|blanco|azul"
          + "|negro|celeste|gris|púrpura|naranja|rosa";

  // Campos´`
  private int id;
  private String nombre;
  private double precio;
  private Color color;
  private boolean locked;
  private EstadoItem estado;

  // Constructor Predeterminado
  public Item() {
    id = DEF_ITEM_ID;
    nombre = DEF_ITEM_NOMBRE;
    precio = DEF_ITEM_PRECIO;
    color = DEF_ITEM_COLOR;
    locked = DEF_LOCKED;
    estado = DEF_ESTADO_ITEM;
  }

  public Item(int id) {
    this.id = id;
    this.nombre = DEF_ITEM_NOMBRE;
    this.precio = DEF_ITEM_PRECIO;
    this.color = DEF_ITEM_COLOR;
    this.locked = DEF_LOCKED;
    this.estado = DEF_ESTADO_ITEM;
  }

  // Constructor Parametrizado
  public Item(int id, String nombre, double precio, Color color, EstadoItem estado) {
    if (UtilesValidacion.validar(id + "", REG_ITEM_ID)) {
      this.id = id;
    } else {
      this.id = DEF_ITEM_ID;
    }

    if (UtilesValidacion.validar(nombre, REG_ITEM_NOMBRE)) {
      this.nombre = nombre;
    } else {
      this.nombre = DEF_ITEM_NOMBRE;
    }

    if (UtilesValidacion.validar(precio + "", REG_ITEM_PRECIO)) {
      this.precio = precio;
    } else {
      this.precio = DEF_ITEM_PRECIO;
    }

    if (UtilesValidacion.validar(UtilesEnums.obtenerNombreColor(color), REG_ITEM_COLOR)) {
      this.color = color;
    } else {
      this.color = DEF_ITEM_COLOR;
    }
    
    this.locked = DEF_LOCKED;
    //Arreglar esto
    this.estado = estado;
  }

  @Override
  public String toString() {
    return String.format(Locale.ENGLISH, "Item %2d - %-16s - %6.2f€ - %-10s "
            + "| %b %s%n",
            getId(), getNombre(),
            getPrecio(), UtilesEnums.obtenerNombreColor(getColor()), isLocked(),
            UtilesEnums.obtenerNombreEstadoItem(getEstado()));
  }

  public void muestraInfoItem() {
    System.out.println(toString());
  }

  public void muestraInfoItemUser() {
    System.out.println(String.format(Locale.ENGLISH, "Item %2d - %-16s - %6.2f€ - %-10s%n",
            getId(), getNombre(),
            getPrecio(), UtilesEnums.obtenerNombreColor(getColor())));
  }

  @Override
  public int compareTo(Item o) {
//        return getId() - o.getId();
    return getNombre().compareTo(o.getNombre());
//        return (int) ((precio - o.precio) * 100);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    if (UtilesValidacion.validar(id + "", REG_ITEM_ID)) {
      this.id = id;
    }
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    if (UtilesValidacion.validar(nombre, REG_ITEM_NOMBRE)) {
      this.nombre = nombre;
    }
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    if (UtilesValidacion.validar(precio + "", REG_ITEM_PRECIO)) {
      this.precio = precio;
    }
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    if (UtilesValidacion.validar(UtilesEnums.obtenerNombreColor(color), REG_ITEM_COLOR)) {
      this.color = color;
    }
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public EstadoItem getEstado() {
    return estado;
  }

  public void setEstado(EstadoItem estado) {
    this.estado = estado;
  }
}
