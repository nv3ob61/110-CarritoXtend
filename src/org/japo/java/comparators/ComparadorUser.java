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
import org.japo.java.entities.User;
import org.japo.java.enumerations.Criterio;

/**
 *
 * @author Jonsui
 */
public final class ComparadorUser implements Comparator<User> {

  private Criterio orden;

  public ComparadorUser(Criterio orden) {
    if (orden != null) {
      this.orden = orden;
    } else {
      this.orden = Criterio.USER;
    }
  }

  @Override
  public int compare(User o1, User o2) {
    int comparacion;
    switch (orden) {
      case USER:
        comparacion = o1.getUser().compareTo(o2.getUser());
        break;
      case ROL:
        comparacion = o1.getRol().compareTo(o2.getRol());
        break;
      case CUENTA_ACTIVA:
        comparacion = Boolean.compare(o1.isCuentaActiva(), o2.isCuentaActiva());
      case MONEDERO:
        comparacion = (int) ((o1.totalSaldoMonedero() - o2.totalSaldoMonedero()) * 100);
      default:
        comparacion = o1.getUser().compareTo(o2.getUser());
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
