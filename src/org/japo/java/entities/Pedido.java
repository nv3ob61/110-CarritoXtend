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
import java.util.List;
import java.util.Locale;
import org.japo.java.entities.datetime.Fecha;
import org.japo.java.entities.datetime.Hora;
import org.japo.java.enumerations.EstadoPedido;
import org.japo.java.libraries.UtilesItem;

/**
 *
 * @author Jonsui
 */
public class Pedido {

  //Valores predet
  public static final String DEF_NUMPEDIDO = NumPedido.generaNumPedido();
  public static final User DEF_USER = new User();
  public static final List<Item> DEF_CESTA = new ArrayList<>();
  public static final Fecha DEF_FECHA = Fecha.obtenerFechaSistema();
  public static final Hora DEF_HORA = new Hora();
  public static final EstadoPedido DEF_ESTADO_PEDIDO = EstadoPedido.VERIFICAR_PAGO;

  //Campos
  private String numPedido;
  private User user;
  private List<Item> cesta;
  private Fecha fecha;
  private Hora hora;
  private EstadoPedido estado;

  public Pedido() {
    numPedido = DEF_NUMPEDIDO;
    user = DEF_USER;
    cesta = DEF_CESTA;
    fecha = DEF_FECHA;
    hora = DEF_HORA;
    estado = DEF_ESTADO_PEDIDO;
  }

  //si estos atr. se supone que ya vienen de anteriormente en el programa
  //¿Haría falta validarlos? Gracias.
  public Pedido(User user, List<Item> cesta, EstadoPedido estado) {
    this.numPedido = DEF_NUMPEDIDO;
    this.user = user;
    this.cesta = cesta;
    this.fecha = DEF_FECHA;
    this.hora = DEF_HORA;
    this.estado = estado;
  }

  public User getUser() {
    return user;
  }

  public List<Item> getCesta() {
    return cesta;
  }

  public Fecha getFecha() {
    return fecha;
  }

  public Hora getHora() {
    return hora;
  }

  public EstadoPedido getEstado() {
    return estado;
  }

  public String getNumPedido() {
    return numPedido;
  }

  public void setNumPedido(String numPedido) {
    this.numPedido = numPedido;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setCesta(List<Item> cesta) {
    this.cesta = cesta;
  }

  public void setFecha(Fecha fecha) {
    this.fecha = fecha;
  }

  public void setHora(Hora hora) {
    this.hora = hora;
  }

  public void setEstado(EstadoPedido estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return String.format(Locale.ENGLISH,
            "INFO DEL PEDIDO%n"
            + "===============%n"
            + "Nº ..: %s%n"
            + "USER: %s%n"
            + "CESTA: %n"
            + "%s%n"
            + "---%n"
            + "TOTAL: %.2f €%n"
            + "Fecha: %s %s%n"
            + "Estado: %s%n", getNumPedido(), getUser(), muestraCestaUser(cesta),
            totalPedido(cesta),
            getFecha(), getHora(), getEstado());
  }

  public void muestraInfoPedido() {
    this.cesta.stream().map((item) -> {
      item.muestraInfoItem();
      return item;
    }).forEachOrdered((_item) -> {
      System.out.println();
    });
  }

  private double totalPedido(List<Item> cesta){
    return UtilesItem.totalCarrito(cesta);
  }
  
  private String muestraCestaUser(List<Item> cesta){
    String c = "";
    for (Item i : cesta) {
      i.muestraInfoItem();
    }
    
    return c;
  }
}
