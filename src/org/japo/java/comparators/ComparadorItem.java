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
import org.japo.java.entities.Item;
import org.japo.java.enumerations.Criterio;
import org.japo.java.libraries.UtilesEnums;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 *
 * @version 0.2 mon_mo
 */
public final class ComparadorItem implements Comparator<Item> {

  private Criterio orden;

  public ComparadorItem(Criterio orden) {
    if (orden != null) {
      this.orden = orden;
    } else {
      this.orden = Criterio.ID;
    }
  }

  @Override
  public int compare(Item o1, Item o2) {
    // Valor de Comparación
    int comparacion;

    // Criterio Ordenacion > Comparacion
    switch (orden) {
      case ID:
        comparacion = o1.getId() - o2.getId();
        break;
      case NOMBRE:
        comparacion = o1.getNombre().compareTo(o2.getNombre());
        break;
      case PRECIO:
        comparacion = (int) ((o1.getPrecio() - o2.getPrecio()) * 100);
        break;
      case COLOR:
        comparacion = UtilesEnums.obtenerNombreColor(o1.getColor()).
                compareTo(UtilesEnums.obtenerNombreColor(o2.getColor()));
        break;
      case LOCKED:
        comparacion = Boolean.compare(o1.isLocked(), o2.isLocked());
        break;
      case DISP_ITEM:
        comparacion = UtilesEnums.obtenerNombreEstadoItem(o1.getEstado()).
                compareTo(UtilesEnums.obtenerNombreEstadoItem(o2.getEstado()));
        break;
      default:
        comparacion = o1.getId() - o2.getId();
        break;
    }

    // Devualve Comparacion
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
