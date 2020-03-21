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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.japo.java.entities.Item;
import org.japo.java.entities.Tarjeta;
import org.japo.java.entities.User;
import org.japo.java.enumerations.IdRol;

/**
 *
 * @author CicloM
 */
public class UtilesCSV {

  /* METODO DE LA ÚLTIMA CLASE ANTES DEL APOCALIPSIS  */
  public static final void exportarProductos(String archivo, List<Item> carrito)
          throws FileNotFoundException {

    try (PrintWriter pw = new PrintWriter(archivo)) {
      for (Item item : carrito) {
        String linea = String.format(Locale.ENGLISH, "%d,%s,%.2f,%s",
                item.getId(), item.getNombre(), item.getPrecio(),
                UtilesEnums.obtenerNombreColor(item.getColor()));
        pw.println(linea);
      }
      System.out.println("Exportado productos OK");
    } catch (Exception e) {
      System.out.println("ERROR: Lectura de fichero cancelada");
    }
  }

  //Exportar e importar esta info codificada, pa practicar.
  public static final void exportarUsers(String archivo, List<User> listaUser) {

    try (PrintWriter pw = new PrintWriter(archivo)) {
      for (User user : listaUser) {
        String linea = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%b,%b",
                user.getNombre(),
                user.getApellido1(),
                user.getApellido2(),
                user.getDni(),
                exportarMonedero(user),
                user.getUser(),
                String.valueOf(user.getPass()),
                user.getRol(),
                user.isCuentaActiva(),
                user.isConectado());
        pw.println(linea);
      }
      System.out.println("Proceso de Exportación Users finalizado con éxito");
    } catch (Exception e) {
      System.out.println("ERROR: Lectura del archivo cancelada.");
    }
  }

  public static final void importarProductos(String archivo, List<Item> carrito)
          throws IOException {
    List<Item> lista = new ArrayList<>();

    try (
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr)) {

      boolean leerOk = true;

      do {
        Item i = new Item();
        String linea = br.readLine();

        if (linea != null) {
          String[] items = linea.split(",");
          i.setId(Integer.parseInt(items[0]));
          i.setNombre(items[1]);
          i.setPrecio(Double.parseDouble(items[2]));
          i.setColor(UtilesEnums.generarColor(items[3]));
          lista.add(i);
        } else {
          leerOk = false;
        }
      } while (leerOk);
      carrito.clear();
      carrito.addAll(lista);
    } catch (Exception e) {
      System.out.println("ERROR: Lectura de fichero cancelada");
    }
  }

  public static final void importarUsers(String archivo, List<User> users) {
    List<User> lista = new ArrayList<>();

    try (
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr)) {

      boolean leerOk = true;

      do {
        User u = new User();
        String linea = br.readLine();

        if (linea != null) {
          String[] items = linea.split(",");
          u.setNombre(items[0]);
          u.setApellido1(items[1]);
          u.setApellido2(items[2]);
          u.setDni(items[3]);
          u.setUser(items[5]);
          u.setMonedero(importarMonedero(u, linea));
          u.setPass(items[6].toCharArray());
          u.setRol(IdRol.valueOf(items[7]));
          u.setCuentaActiva(Boolean.valueOf(items[8]));
          u.setConectado(Boolean.valueOf(items[9]));
          lista.add(u);
        } else {
          leerOk = false;
        }
      } while (leerOk);
      users.clear();
      users.addAll(lista);
      System.out.println("IMPORT OK");
    } catch (Exception e) {
      System.out.println("ERROR: Fallo importación Usuarios");
    }
  }

  public static final String exportarMonedero(User user) {
    String monedero = "";
    Tarjeta t;

    Iterator<Tarjeta> it = user.getMonedero().iterator();
    while (it.hasNext()) {
      t = it.next();
      if(!t.getNumero().equals("")){    
      monedero = String.valueOf(
              t.getNumero() + "|"
              + t.getDebito() + "|"
              + t.getCredito()) + "|".concat(monedero);
      } else {
        monedero = null;
      }
      //Hay otra forma de quitar el "|" final?
      monedero = monedero.substring(0, monedero.length() - 1);
    } 
    return monedero;
  }

  public static final HashSet<Tarjeta> importarMonedero(User u, String tarjetas)
          throws FileNotFoundException, IOException {

    HashSet<Tarjeta> monedero = new HashSet<>();

    try {
      boolean leerOk = true;

      do {

        if (tarjetas != null) {
          String[] users = tarjetas.split(",");
          //Si la pos 5 equivale a la del usuario de entrada.
          if (users[5].equals(u.getUser())) {
            String[] trj = users[4].replace('|', ',').split(",");
            //descompone el campo de donde se almacenan las tarjetas.
            for (int i = 0; i < trj.length; i = i + 3) {
              Tarjeta t = new Tarjeta();
              t.setNumero(trj[i]);
              t.setDebito(Double.parseDouble(trj[i + 1]));
              t.setCredito(Double.parseDouble(trj[i + 2]));
              monedero.add(t);
            }
            leerOk = false;

          } else {
            System.out.println("ERROR: USER " + users[5] + " Falla...");
            leerOk = false;
          }
        } else {
          leerOk = false;
        }
      } while (leerOk);
    } catch (Exception e) {
      System.out.println("ERR: importarMonedero(), probablemente esté vacio.");
    }

    return monedero;
  }
}
