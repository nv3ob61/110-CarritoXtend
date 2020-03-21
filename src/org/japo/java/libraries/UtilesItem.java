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

import java.util.List;
import java.util.Locale;
import org.japo.java.entities.Item;
import org.japo.java.enumerations.EstadoItem;

/**
 *
 * @author Jonsui
 */
public class UtilesItem {

  public static final void addItemToCarrito(int uds, List<Item> lista, List<Item> carrito) {

    for (int i = 0; i < uds; i++) {
      lista.get(i).setEstado(EstadoItem.EN_CARRITO); //pasa el estado en_carrito
      carrito.add(lista.get(i));   //se añade al carrito.
    }
  }

  public static final void addItemToPedido(List<Item> carrito, List<Item> pedido) {

    for (int i = 0; i < carrito.size(); i++) {
      carrito.get(i).setEstado(EstadoItem.NO_DISPONIBLE);
      carrito.get(i).setLocked(true);
      //pasa el estado en_carrito
      pedido.add(carrito.get(i));   //se añade al carrito.
    }
  }

  public static final double totalCarrito(List<Item> carrito) {
    double total = 0;
    for (Item item : carrito) {
      total += item.getPrecio();
    }
    return total;
  }

  public static final void muestraTotalCarrito(List<Item> carrito) {
    System.out.printf(Locale.ENGLISH, "TOTAL DEL carrito ......: %.2f€%n", totalCarrito(carrito));
  }

  public static final void listarCarrito(List<Item> carrito) {
    UtilesMenus.lineaLong();
    UtilesMenus.muestraBannerListarItems();
    for (Item i : carrito) {
      i.muestraInfoItem();
    }
    UtilesMenus.lineaLong();
    UtilesItem.muestraTotalCarrito(carrito);
    UtilesMenus.lineaLong();
    System.out.printf("%n%n");
  }

  public static final void volverDisponibles(List<Item> carrito) {
    for (Item i : carrito) {
      i.setEstado(EstadoItem.DISPONIBLE);
    }
  }

}
