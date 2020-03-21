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
package org.japo.java.comparators;

import java.util.Comparator;
import org.japo.java.entities.Tarjeta;
import org.japo.java.enumerations.Criterio;
import org.japo.java.libraries.UtilesUser;

/**
 *
 * @author Jonsui
 */
public final class ComparadorTarjeta implements Comparator<Tarjeta> {

  private Criterio orden;

  public ComparadorTarjeta(Criterio orden) {
    if (orden != null) {
      this.orden = orden;
    } else {
      this.orden = Criterio.MONEDERO;
    }
  }

  @Override
  public int compare(Tarjeta o1, Tarjeta o2) {
    int comparacion;
    switch (orden) {
      case MONEDERO:
        comparacion = (int) (UtilesUser.totalPortarjeta(o1) - UtilesUser.totalPortarjeta(o2));
      default:
        comparacion = (int) (UtilesUser.totalPortarjeta(o1) - UtilesUser.totalPortarjeta(o2));
    }

    return comparacion;
  }

  public Criterio getOrden() {
    return orden;
  }

  public void setOrden(Criterio orden) {
    if (orden != null) {
      this.orden = orden;
    }
  }
}
