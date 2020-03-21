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
package org.japo.java.app;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.japo.java.app.log.ListaLog;
import org.japo.java.app.log.LogTry;
import org.japo.java.app.log.Login;
import org.japo.java.app.log.Logout;
import org.japo.java.comparators.ComparadorItem;
import org.japo.java.comparators.ComparadorUser;
import org.japo.java.entities.Item;
import org.japo.java.entities.Pedido;
import org.japo.java.entities.Persona;
import org.japo.java.entities.Tarjeta;
import org.japo.java.entities.User;
import org.japo.java.enumerations.Criterio;
import org.japo.java.enumerations.EstadoItem;
import org.japo.java.enumerations.EstadoPedido;
import org.japo.java.enumerations.IdRol;
import org.japo.java.enumerations.LogLevel;
import org.japo.java.libraries.UtilesCSV;
import org.japo.java.libraries.UtilesEntrada;
import org.japo.java.libraries.UtilesEnums;
import org.japo.java.libraries.UtilesItem;
import org.japo.java.libraries.UtilesMenus;
import org.japo.java.libraries.UtilesPassword;
import org.japo.java.libraries.UtilesUser;
import org.japo.java.libraries.UtilesValidacion;

/**
 *
 * @author mon_mo
 */
public final class App {

  //Colecciones de usuarios e items
  public static final List<User> USERS = new ArrayList<User>();
  public static final List<User> FILTRO_USERS = new ArrayList<User>();

  public static final List<Item> PRODUCTOS = new ArrayList<Item>();
  public static final List<Item> FILTRO_PRODUCTOS = new ArrayList<Item>();

  public static final List<Item> CARRITO = new ArrayList<Item>();

  //Lista que guarda el pedido a procesar.
  public static final List<Pedido> PEDIDOS = new ArrayList<Pedido>();

  public static final ListaLog LISTA_LOG = new ListaLog();

  //Nombre del archivo CSV con datos de items
  public static final String CATALOGO = "catalogo.csv";

  //Nombre del archivo que contiene los usuarios... codificar!
  public static final String USERSVAULT = "usersvault.csv";
  //La cuenta del supermercado que acumula el dinero de las compras.
  //no funciona en esta vers todavía...
  public static final String CARTILLAVAULT = "cartilla.csv";

  //intentos de login de la aplicación. Se resetea si el login es correcto.
  public static int intentos = 3;

  //Propiedades de la app
  private Properties prp;

  //Criterios varios
  private Criterio criOrd = Criterio.NINGUNO;
  private Criterio criFil = Criterio.NINGUNO;
  private double totalPedido;

  //Constructor
  public App(Properties prp) {
    this.prp = prp;
  }

  //Lógica de la Aplicación
  public final void launchApp() throws IOException {

    UtilesCSV.importarUsers(USERSVAULT, USERS);
//    UtilesCSV.importarUsers(CARTILLAVAULT, USERS);

    UtilesCSV.importarProductos(CATALOGO, PRODUCTOS);

    for (User user : USERS) {
      user.muestraPersona();
    }

    //menú principal
    procesarMenuLogin();

    //despedida
    System.out.println();
    System.out.println("---");
    System.out.println("FIN DEL PROGRAMA");
  }

  private void procesarMenuLogin() throws FileNotFoundException, IOException {
    boolean salirOk = false;

    do {
      char opcion = UtilesEntrada.obtenerOpcion(UtilesMenus.TXT_MENU_LOGIN,
              UtilesMenus.TXT_MENU_ERROR, UtilesMenus.OPC_MENU_LOGIN);

      System.out.println("---");
      //Gestión
      switch (opcion) {
        case 'l':
        case 'L':
          intentos = procesarLoginUsuario();
          UtilesMenus.muestrIntentos(intentos);
          break;
        case 'r':
        case 'R':
          procesarRegistroUsuario();
          break;
        case 'x':
        case 'X':
          salirOk = true;
          break;
        default:
          System.out.println("ERR: Operación NO disponible.");
          System.out.println("---");
      }
    } while (!salirOk && intentos > 0);
  }

  private void procesarMenuAdminMain(User admin) throws FileNotFoundException, IOException {

    System.out.println("MAIN MENÚ ADMIN ... CUENTA: " + admin.getUser());
    System.out.println("===============================================");
    UtilesEntrada.hacerPausa();
    boolean salirOk = false;

    do {
      char opcion = UtilesEntrada.obtenerOpcion(UtilesMenus.TXT_MENU_ADMIN,
              UtilesMenus.TXT_MENU_ERROR, UtilesMenus.OPC_MENU_ADMIN);

      System.out.println("---");
      //Gestión
      switch (opcion) {
        case 'i':
        case 'I':
          procesarMenuAdminItems();
          break;
        case 'u':
        case 'U':
          procesarMenuAdminUsers();
          break;
        case 'o':
        case 'O':
          listarPedidosAdmin();
          break;
        case 'c':
        case 'C':
          procesarMenuContenidoAdmin();
          break;
        case 'p':
        case 'P':
          procesarPersistencia();
          break;
        case 'x':
        case 'X':
          salirOk = true;
          break;
        default:
          System.out.println("ERR: Operación NO disponible.");
          System.out.println("---");
      }
    } while (!salirOk && intentos > 0);
//al acabar desconectamos el estado
    admin.setConectado(false);
  }

  private void procesarMenuAdminItems() {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_ADMIN_ITEM,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_ADMIN_ITEM);

      System.out.println("---");

      switch (opcion) {
        case 'a':
        case 'A':
          agregarItem();
          break;
        case 'b':
        case 'B':
          borrarItem();
          break;
        case 'c':
        case 'C':
          consultarItem();
          break;
        case 'm':
        case 'M':
          modificarItem();
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);
  }

  private void procesarMenuAdminUsers() {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_ADMIN_USER,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_ADMIN_USER);

      System.out.println("---");

      switch (opcion) {
        case 'a':
        case 'A':
          agregarUser();
          break;
        case 'b':
        case 'B':
          borrarUser();
          break;
        case 'c':
        case 'C':
          consultarUser();
          //hacemos pausa aquí para
          UtilesEntrada.hacerPausa();
          break;
        case 'h':
        case 'H':
          habilitarUser();    //cambia estado de cuentas activas/inactivas
          break;
        case 'm':
        case 'M':
          modificarUser();
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);
  }

  private void procesarMenuContenidoAdmin() {
    // Semaforo control bucle
    boolean salirOK = false;
    boolean isOk;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_CONT,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_CONT);

      System.out.println("---");

      switch (opcion) {
        //listado de Items o Users.
        case 'l':
        case 'L':
          isOk = false;
          do {
            // Consola + Opciones > Opcion
            char listar = UtilesEntrada.obtenerOpcion(
                    UtilesMenus.TXT_MENU_ADMIN_DESEA,
                    UtilesMenus.TXT_MENU_ERROR,
                    UtilesMenus.OPC_MENU_ADMIN_DESEA);
            switch (listar) {
              //listado de items
              case 'i':
              case 'I':
                listarItems();
                break;
              //listado de Users
              case 'u':
              case 'U':
                listarUsers();
                break;
              case 'x':
              case 'X':
                isOk = true;
                break;
              default:
                System.out.println("ERROR: Opción NO disponible");
                System.out.println("---");
            }
          } while (!isOk);
          break;
        case 'f':
        case 'F':
          isOk = false;
          do {
            // Consola + Opciones > Opcion
            char listar = UtilesEntrada.obtenerOpcion(
                    UtilesMenus.TXT_MENU_ADMIN_DESEA,
                    UtilesMenus.TXT_MENU_ERROR,
                    UtilesMenus.OPC_MENU_ADMIN_DESEA);
            switch (listar) {
              //listado de items
              case 'i':
              case 'I':
                //pasamos al menú filtrado de Items
                procesarMenuFiltroItems();
                break;
              //listado de Users
              case 'u':
              case 'U':
                procesarMenuFiltroUsers();
                break;
              case 'x':
              case 'X':
                isOk = true;
                break;
              default:
                System.out.println("ERROR: Opción NO disponible");
                System.out.println("---");
            }
          } while (!isOk);
          break;
        case 'o':
        case 'O':
          isOk = false;
          do {
            // Consola + Opciones > Opcion
            char listar = UtilesEntrada.obtenerOpcion(
                    UtilesMenus.TXT_MENU_ADMIN_DESEA,
                    UtilesMenus.TXT_MENU_ERROR,
                    UtilesMenus.OPC_MENU_ADMIN_DESEA);
            switch (listar) {
              //listado de items
              case 'i':
              case 'I':
                //pasamos al menú filtrado de Items
                procesarMenuOrdenItems();
                break;
              //listado de Users
              case 'u':
              case 'U':
                procesarMenuOrdenUsers();
                break;
              case 'x':
              case 'X':
                isOk = true;
                break;
              default:
                System.out.println("ERROR: Opción NO disponible");
                System.out.println("---");
            }
          } while (!isOk);
          break;

        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);
  }

  private void listarPedidosAdmin() {
    // Cabecera
    System.out.println("Listado de Pedidos");
    System.out.println("================");

    // Filtrado > Selección Colección
    List<Pedido> lista = criFil.equals(Criterio.NINGUNO)
            ? PEDIDOS : PEDIDOS;

    for (Pedido pedido : lista) {
      UtilesMenus.lineaLong();
      System.out.println(pedido.toString());
      System.out.println();
    }
    // Pausai
    UtilesEntrada.hacerPausa();
    UtilesMenus.lineaLong();
    UtilesMenus.lineaLong();
  }

  /* 
      
          PROCESADO DE LOS MENÚS DE USUARIO
  
   */
  private void procesarMenuUserMain(User user) {
    boolean salirOk = false;
    System.out.println("USUARIO ...: " + user.getUser());
    System.out.printf("===============================================%n%n");

    do {  //si salirOk = true y otras condiciones de la entidad user.
      do {
        char opcion = UtilesEntrada.obtenerOpcion(UtilesMenus.TXT_MENU_USER,
                UtilesMenus.TXT_MENU_ERROR, UtilesMenus.OPC_MENU_USER);

        System.out.println("---");
        //Gestión
        switch (opcion) {
          case 'a':
          case 'A':
            procesarMenuUserAddItem(user);
            break;
          case 'b':
          case 'B':
            procesarMenuUserDelItem();
            break;
          case 'c':
          case 'C':
            procesarMenuContenidoUser();
            break;
          case 'l':
          case 'L':
            UtilesItem.listarCarrito(CARRITO);
            break;
          case 'm':
          case 'M':
            salirOk = procesarModificarDatosUser(user);    //Modifica datos de user.
            break;
          case 't':
          case 'T':
            procesarAddTarjeta(user);
            break;
          case 'e':
          case 'E':
            procesarDelTarjeta(user);
            break;
          case 'r':
          case 'R':
            procesarPedido(user);
            break;
          case 'x':
          case 'X':
            //desconectar user
            UtilesItem.volverDisponibles(CARRITO);
            //al salir de momento borramos el filtro de productos y el carrito...
            FILTRO_PRODUCTOS.clear();
            CARRITO.clear();
            //añade logout del User
            Logout logOut = new Logout(user, LogLevel.LOGOUT_LEVEL);
            logOut.muestraInfoLogout();
            LISTA_LOG.addLogOut(logOut);
            user.setConectado(false);
            salirOk = true;
            break;
          default:
            System.out.println("ERR: Operación NO disponible.");
            System.out.println("---");
        }
      } while (!salirOk);

    } //salida del while 
    while (user.getRol().equals(IdRol.USER)
            && user.isConectado() && user.isCuentaActiva() && salirOk == false);
  }

  private void procesarMenuUserAddItem(User u) {
    boolean isOk = false;
    if (u.totalSaldoMonedero() > 0) {
//      FILTRO_PRODUCTOS.clear();
      activarFiltroItemNombre(activarFiltroItemDisp());
      listarItemsUser();
//      listaItemsUser(); //esto para qué sirve?
      if (cantidadItemsDisp() > 0) {
        do {
          //preguntar la cantidad que desea añadr
          int uds = UtilesEntrada.leerEntero("Introduzca cantidad ...: ",
                  "ERROR: Dato introducido incorrecto");
//
//          Comparator<Item> cpm = new ComparadorItem(Criterio.ID);
//          Collections.sort(FILTRO_PRODUCTOS, cpm);

          if (uds <= 0) {
            if (uds == 0) {
              System.out.println("Has introducido 0 artículos nuevos.");
              isOk = true;
            } else {
              System.out.println("Cantidad introducida incorrecta.");
            }
          } else if (uds > cantidadItemsDisp()) {
            System.out.println("Cantidad introducida MAYOR que el nº de items en stock");
          } else {
            //aplicar la lógica aquí.
            for (int i = 0; i < uds; i++) {
              for (Item item : PRODUCTOS) {
                if (FILTRO_PRODUCTOS.get(i).getId() == (item.getId())) {
                  FILTRO_PRODUCTOS.get(i).setEstado(EstadoItem.EN_CARRITO); //pasa el estado en_carrito
                  CARRITO.add(FILTRO_PRODUCTOS.get(i));   //se añade al carrito.
                }
              }
            }
//            for (int i = 0; i < uds; i++) {
//              lista.get(i).setLocked(true);   //pasa el estado bloqueado por pedido.
//              CARRITO.add(lista.get(i));   //se añade al carrito.
//            }
            System.out.printf("%nSe han añadido %d productos a su cesta.%n%n", uds);
            isOk = true;
            FILTRO_PRODUCTOS.clear();
          }
        } while (!isOk);
      }
    } else {
      System.out.println("ERROR: Saldo insuficiente");
    }
  }

  private void procesarMenuUserDelItem() {
    // Cabecera
    System.out.println("Borrado de Item del Carrito");
    System.out.println("===============");

    // Comparador de Búsqueda
    Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

    // Ordenacion
    Collections.sort(CARRITO, cmp);

    //muestra items ordenados por ID
    for (Item i : CARRITO) {
      i.muestraInfoItemUser();
    }
    UtilesMenus.lineaLong();   //Separador largo.

    // Consola > Clave
    int id = UtilesEntrada.leerEntero(
            "Id ..... 101 = Borrado TOTAL : ",
            "ERROR: Entrada Incorrecta");

    if (id == 101) {
      CARRITO.forEach((i) -> {
        CARRITO.remove(i);
      });
    } else {
      // Clave de Busqueda
      Item clave = new Item(id);

      // Localización
      int posicion = Collections.binarySearch(CARRITO, clave, cmp);

      if (posicion < 0) {
        UtilesEntrada.hacerPausa("ERROR: Ítem NO encontrado");
      } else {
        // Obtiene Item
        Item i = CARRITO.get(posicion);

        // Separador
        System.out.println("---");

        // Muestra Datos
        System.out.println("Item Seleccionado");
        System.out.println("-----------------");
        System.out.printf("Id .....: %d%n", i.getId());
        System.out.printf("Nombre .: %s%n", i.getNombre());
        System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
        System.out.printf("Color ..: %s%n",
                UtilesEnums.obtenerNombreColor(i.getColor()));

        // Separador
        System.out.println("---");

        // Confirmar Proceso
        if (UtilesEntrada.confirmarProceso("Confirmar Borrado (s/N) ...: ", false)) {
          // Eliminación Ítem
          i.setLocked(false);
          i.setEstado(EstadoItem.DISPONIBLE);
          CARRITO.remove(posicion);

          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Ítem BORRADO del carrito correctamente");
        } else {
          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Eliminación de ítem CANCELADO");
        }
      }
    }
  }

  private void procesarAddTarjeta(User user) {

    if (UtilesUser.totalTarjetas(user) > -1 && UtilesUser.totalTarjetas(user) < 2) {

      HashSet<Tarjeta> m = user.getMonedero();

      String numero = UtilesEntrada.leerTexto("Número .......: ");

      if (UtilesUser.buscaTarjeta(numero, m) == null) { //si no encuentra el valor
        double credito = UtilesEntrada.leerReal("Crédito ......: ",
                "ERROR: Formato introducido erróneo.", 0.0, 5000.0);

        //separador
        System.out.println();
        System.out.println("---");
        System.out.println("INFO: Se añade por defecto debito: 500€");
        Tarjeta t = new Tarjeta(numero, 500.0, credito);
        if (UtilesUser.addTarjeta(user, t)) {
          System.out.println();
          t.muestraTarjeta();
          System.out.println("Tarjeta AÑADIDA con éxito.");
        } else {
          System.out.println("ERROR. FAllo al añadir tarjeta. TESTTESTTEST.");
        }
      } else {
        System.out.println("ERROR: Número de tarjeta ya está en la lista.");
      }
    } else {  //no debería pasar que el totalTarjetas estuviera fuera de rango...
      System.out.println("ERROR: Número de tarjetas fuera de rango. VER MÉTODO.");
    }
  }

  private void procesarDelTarjeta(User user) {

    if (UtilesUser.totalTarjetas(user) > 0) {
      //buscar tarjetas
      for (Tarjeta tarjeta : user.getMonedero()) {
        tarjeta.muestraTarjeta();
      }

      //separda
      System.out.println();
      System.out.println("---");

      String num = UtilesEntrada.leerTexto("Introduzca el número ...: ");
      //mostrar qué tarjeta se quiere borrar, por nombre.
      Tarjeta t = UtilesUser.buscaTarjeta(num, user.getMonedero());
      if (t != null) {
        if (UtilesUser.delTarjeta(user, t)) {
          System.out.println("Tarjeta borrada con éxito");
        } else {
          System.out.println("ERROR: fallo al borrar la tarjeta");
        }
      } else {
        System.out.printf("%nERROR: Tarjeta no encontrada%n%n");
      }
    } else {  //no debería pasar que el totalTarjetas estuviera fuera de rango...
      System.out.println("ERROR: Número de tarjetas fuera de rango. VER MÉTODO.");
    }
  }

  private void procesarMenuContenidoUser() {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_CONT,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_CONT);

      System.out.println("---");

      switch (opcion) {
        //listado de Items o Users.
        case 'l':
        case 'L':
          listarItemsUser();
          break;
        case 'f':
        case 'F':
          procesarMenuFiltroItems();
          break;
        case 'o':
        case 'O':
          //pasamos al menú filtrado de Items
          procesarMenuOrdenItems();
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);
  }

  private boolean procesarModificarDatosUser(User us) {
    boolean isOk = false;
    // Cabecera
    System.out.println("Modificación de Usuario");
    System.out.println("=======================");

    // Consola > Clave
    String user = us.getUser();

    // Comparador de Búsqueda
    Comparator<User> cmp = new ComparadorUser(Criterio.USER);

    // Ordenacion
    Collections.sort(USERS, cmp);

    // Clave de Busqueda
    User clave = new User(user, null);

    // Proceso de Busqueda
    int posicion = Collections.binarySearch(USERS, clave, cmp);

    if (posicion < 0) {
      UtilesEntrada.hacerPausa("ERROR: Usuario NO encontrado");
    } else {
      // Obtiene Usuario
      User u = USERS.get(posicion);

      // Separador
      System.out.println("---");

      // Muestra Datos
      u.muestraPersona();

      // Separador
      System.out.println("---");

      // Confirmar Proceso
      if (UtilesEntrada.confirmarProceso("Modificar Usuario (s/N) ...: ", false)) {
        // Separador
        System.out.println("---");

        // User Vacío
        User aux = new User(user, null);

        // Item Modificado
        System.out.println("Usuario Modificado");
        System.out.println("------------------");

        // Consola > Password | RECICLAMOS código del registro de usuarios aquí.
        aux.setPass(UtilesPassword.pidePass(user, u));

        UtilesUser.pideDatosUser(u, aux);

        aux.muestraPersona();

        // Confirmar Sustitución
        if (UtilesEntrada.confirmarProceso("Guardar cambios (s/N) ...: ", false)) {
          if (aux.getRol().equals((IdRol.ADMIN))) {
            // Sustitución Item
            USERS.set(posicion, aux);
            // Mensaje Informacivo
            UtilesEntrada.hacerPausa("ADMIN: Usuario MODIFICADO correctamente");
          } else {
            // Sustitución Item
            USERS.set(posicion, aux);
            // Mensaje Informacivo
            UtilesEntrada.hacerPausa("Usuario MODIFICADO correctamente");
            isOk = true;
          }
        } else {
          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Modificación de Usuario CANCELADA");
        }

      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Modificación de Usuario CANCELADA");
      }
    }
    return isOk;
  }

  private void procesarPedido(User user) {
    List<Item> protoCarrito = new ArrayList<>();
    Pedido p = new Pedido();
    totalPedido = UtilesItem.totalCarrito(CARRITO);
    double totalRestante;

    UtilesItem.listarCarrito(CARRITO);
    //separador
    System.out.printf("TIENE EN LA CUENTA: %.2f%n",
            UtilesUser.totalSaldoTarjetas(user, user.getMonedero()));
    System.out.println("---");
    System.out.printf(Locale.ENGLISH, "TOTAL A PAGAR: %.2f €%n", totalPedido);
    System.out.println();
    // Confirmar Sustitución
    if (UtilesEntrada.confirmarProceso("Procesar pedido (S/n) ...: ", true)) {
//          PEDIDOS.add(new Pedido(user, CARRITO, EstadoPedido.VERIFICAR_PAGO));

      if (UtilesUser.totalTarjetas(user) > 0) {  //si el user tiene al menos 1
        if (user.totalSaldoMonedero() >= totalPedido) { //saldo > totalPedido
          for (Tarjeta t : user.getMonedero()) {
            if (UtilesUser.totalPortarjeta(t) >= totalPedido) {
              if (t.getDebito() > 0) {
                if (t.getDebito() >= totalPedido) {
                  protoCarrito.clear();
                  totalRestante = t.getDebito() - totalPedido;
                  t.setDebito(totalRestante);

                  UtilesItem.addItemToPedido(CARRITO, protoCarrito);
                  p.setUser(user);
                  p.setCesta(protoCarrito);
                  //muestra la cesta, test.
                  p.getCesta().toString();
                  //Separador pausa test para ver lo que muestra la cesta
                  UtilesEntrada.hacerPausa("Pausa TEST para ver lo que ha mostrado la cesta.");
                  p.setEstado(EstadoPedido.VERIFICAR_PAGO);
                  p.muestraInfoPedido();
                  PEDIDOS.add(p);
                  CARRITO.clear();
                  System.out.println("TEST PAGA 1");
                } else if (t.getDebito() < totalPedido) {
                  protoCarrito.clear();
                  totalRestante = totalPedido - t.getDebito();
                  totalRestante = t.getCredito() - totalRestante;
                  t.setDebito(0);
                  t.setCredito(totalRestante);
                  UtilesItem.addItemToPedido(CARRITO, protoCarrito);
                  p.setUser(user);
                  p.setCesta(protoCarrito);
                  p.setEstado(EstadoPedido.VERIFICAR_PAGO);
                  PEDIDOS.add(p);
                  CARRITO.clear();
                  System.out.println("TEST PAGA 2");
                }
                System.out.println("TEST PAGA 3");
                //si el credito es > 0
              } else if (t.getCredito() >= totalPedido) {
                protoCarrito.clear();
                totalRestante = totalPedido - t.getCredito();
                t.setCredito(totalRestante);
                UtilesItem.addItemToPedido(CARRITO, protoCarrito);
                p.setUser(user);
                p.setCesta(protoCarrito);
                p.setEstado(EstadoPedido.VERIFICAR_PAGO);
                PEDIDOS.add(p);
                CARRITO.clear();
                System.out.println("TEST PAGA 4");
              } else {
                System.out.println("ERROR: Credito Insuficiente??? TEST");
              }
              System.out.println("TEST PAGA 5");
            } else {
              System.out.println("ERROR TEST: No se puede cobrar"
                      + " de varias tarjetas a la vez");
              System.out.println("PASAMOS A la siguiente.");
            }
            System.out.println("TEST PAGA 6");
          }
          System.out.println("TEST PAGA 7");
        } else {
          System.out.println("ERROR: Saldo insuficiente.");
        }
        System.out.println("TEST PAGA 8");
      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("ERROR: Usuario NO TIENE tarjetas asociadas.");
      }
    }
  }

  /*  
          
        MÉTODOS QUE PROCESAN LOS MENÚS PRINCIPALES + PERSISTENCIA
  
   */
  private int procesarLoginUsuario() throws FileNotFoundException, IOException {
    //Creamos dos usuarios, uno que intenta conectar y si lo hace pasa los 
    //datos al segundo (id) buscando en el método de UtilesUser.buscaUser, isOK?
    User u = new User();
    User id;
    //Crea un login y añade entrada en LISTA_LOG
    Login logIn;
    LogTry logTry;

    u.setUser(UtilesEntrada.pideUser());
    u.setPass(UtilesEntrada.pidePass());

    //si el user y pass son correctos
    if (UtilesUser.esIdentidad(u.getUser(), u.getPass(), USERS)) {
      //carga el User con los datos a partir de las credenciales obtenidas.
      id = UtilesUser.buscaUser(u.getUser(), USERS);
      //cambia el estado a conectado.
      id.setConectado(true);

      //separador
      System.out.println("---");
      System.out.printf("%nLOGIN CORRECTO...%n");

      //mostramos id del User
      id.muestraPersona();

      if (id.isCuentaActiva() == false) {
        System.out.println("ERROR: Cuenta NO activa. SALIDA del login.");
        System.out.println();
      } else {
        //ponemos el contador de intentos a 3 otra vez si el login es ok.
        //lo ponemos aquí por si la cuenta no está activa, impidiendo al user
        //acceder más veces...
        intentos = 3;
        if (id.getRol().equals(IdRol.USER)) {
          //Añadimos que se ha logueado un usuario a su panel de control.
          logIn = new Login(id, LogLevel.USERPANEL_CON);
          LISTA_LOG.addLogIn(logIn);

          //Muestra la info del login
          logIn.muestraInfoLogin();
          procesarMenuUserMain(id);
        } else if (id.getRol().equals(IdRol.ADMIN)) {
          //Añadimos que se ha logueado un admin a su panel de control.
          logIn = new Login(id, LogLevel.ADMINPANEL_CON);
          LISTA_LOG.addLogIn(logIn);
          //Muestra la info del login
          logIn.muestraInfoLogin();
          procesarMenuAdminMain(id);
          //mensaje que filtra a las cuentas DEF_ROL
        } else if (id.getRol().equals(IdRol.DEF_ROL)) {
          System.out.println("CUENTA NO ACTIVA DE MOMENTO.");
          System.out.println("CONSULTAR AL ADMINISTRADOR.PACIENCIA");
          System.out.println("---");
          System.out.println();
        }
      }
    } else {  //Si el user y pass introducidos no coinciden.
      intentos--;
      System.out.printf("%nERROR: fallan las credenciales en el login...%n%n");
      //log de intento fallido...
      logTry = new LogTry(u);
      LISTA_LOG.addLogTry(logTry);
      logTry.muestraInfoLogTry();
    }
    return intentos;
  }

  private void procesarRegistroUsuario() {
    String user;
    //userComp sirve para validar con el password que no se introducen
    // user y pass iguales.
    char[] userComp;

    char[] pass;
    char[] passComp;
    boolean passOk = false;
    boolean isOk = false;
    //muestra banner
    UtilesMenus.muestraBannerRegUsers();

    user = UtilesEntrada.pideUser();
    userComp = user.toCharArray();
    //comprueba si el user ya existe
    if (!UtilesUser.esIdentidad(user, USERS)) {

      //validar pass....
      do {
        System.out.printf("%nNUEVO PASSWORD%n");
        pass = UtilesEntrada.pideNuevoPass();

        passComp = UtilesEntrada.pideRepitePass();

        //Si al comparar los dos pass coinciden y este es distinto del username.
        if (Arrays.toString(pass).equals(Arrays.toString(passComp))
                && !Arrays.toString(userComp).equals(Arrays.toString(pass))) {
          passOk = true;
          System.out.printf("%nPASSWORD VALIDADO CON ÉXITO%n%n");
        } else if (Arrays.toString(userComp).equals(Arrays.toString(pass))) {
          System.out.printf("%nERROR: El password no puede coincidir "
                  + "con el nombre de usuario.%n");
        } else {
          System.out.printf("%nERROR: Password no coincide. Por favor...%n%n");
          System.out.println("---");
        }
      } while (!passOk);

      //Creamos el nuevo user simple con los datos recibidos...
      User u = new User(user, pass);

      if (USERS.add(u)) {
        System.out.printf("%nUSER creado con éxito%n%n"
                + "Debes esperar a que el admin active tu cuenta para loguear.");
        System.out.println("---");
      } else {
        System.out.printf("%nERROR: Operación NO completada.%n");
        System.out.println("---");
      }
    } else {
      System.out.printf("%nEL USUARIO YA EXISTE....%n%n");
      System.out.println("---");
    }
  }

  private void procesarPersistencia() throws FileNotFoundException, IOException {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_PERS,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_PERS);

      // Separador
      System.out.println("---");

      // Registro Criterio Ordenacion
      switch (opcion) {
        case 'i':
        case 'I':
          importarDatos();
          salirOK = true;
          break;
        case 'e':
        case 'E':
          exportarDatos();
          salirOK = true;
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);
  }

  private void importarDatos() throws IOException {
    UtilesEntrada.hacerPausa("Importar Datos");
    System.out.println("==============");
    try {
      UtilesCSV.importarProductos(CATALOGO, PRODUCTOS);
    } catch (IOException e) {
      System.out.println("Fallo en la importación del CATÁLOGO de productos");
    }
    UtilesCSV.importarUsers(USERSVAULT, USERS);

  }

  private void exportarDatos() throws FileNotFoundException {
    UtilesEntrada.hacerPausa("Exportar Datos");
    System.out.println("==============");
    UtilesCSV.exportarProductos(CATALOGO, PRODUCTOS);
    UtilesCSV.exportarUsers(USERSVAULT, USERS);
  }

  /* 
          
          SECCIÓN MENU ADMIN ITEMS
  
   */
  private void agregarItem() {
    // Cabecera
    System.out.println("Inserción de Item");
    System.out.println("=================");

    // Consola > Clave
    int id = UtilesEntrada.leerEntero(
            "Id .....: ",
            "ERROR: Entrada Incorrecta");

    // Comparador de Búsqueda
    Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

    // Ordenacion
    Collections.sort(PRODUCTOS, cmp);

    // Clave de Busqueda
    Item clave = new Item(id);

    // Proceso de Busqueda
    int posicion = Collections.binarySearch(PRODUCTOS, clave, cmp);

    // Analisis Resultado Busqueda
    if (posicion < 0) {
      // Obtiene Item
      Item i = new Item(id);

      // Separador
      System.out.println("---");

      // Consola > Nombre
      do {
        i.setNombre(UtilesEntrada.leerTexto("Nombre .: "));
      } while (i.getNombre().equals(Item.DEF_ITEM_NOMBRE));

      // Consola > Precio
      do {
        i.setPrecio(UtilesEntrada.leerReal("Precio .: ", "ERROR: Entrada incorrecta"));
      } while (i.getPrecio() == Item.DEF_ITEM_PRECIO);

      // Consola > Color
      do {
        String nombreColor = UtilesEntrada.leerTexto("Color ..: ");

        Color color = UtilesEnums.generarColor(nombreColor.toLowerCase());

        i.setColor(color);
      } while (i.getColor().equals(Item.DEF_ITEM_COLOR));

      System.out.println("---");

      // Consola > Datos Nuevo Item
      System.out.println("Item a insertar");
      System.out.println("---------------");
      System.out.printf("Id .....: %d%n", i.getId());
      System.out.printf("Nombre .: %s%n", i.getNombre());
      System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
      System.out.printf("Color ..: %s%n",
              UtilesEnums.obtenerNombreColor(i.getColor()));

      // Separador
      System.out.println("---");

      // Confirmar Proceso
      if (UtilesEntrada.confirmarProceso("Confirmar Inserción (S/n) ...: ", true)) {
        // Insercion Ítem
        PRODUCTOS.add(i);

        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Ítem INSERTADO correctamente");
      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Inserción de ítem CANCELADA");
      }
    } else {
      UtilesEntrada.hacerPausa("ERROR: Ítem ya existe");
    }
  }

  private void borrarItem() {
    // Cabecera
    System.out.println("Borrado de Item");
    System.out.println("===============");

    // Consola > Clave
    int id = UtilesEntrada.leerEntero(
            "Id .....: ",
            "ERROR: Entrada Incorrecta");

    // Comparador de Búsqueda
    Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

    // Ordenacion
    Collections.sort(PRODUCTOS, cmp);

    // Clave de Busqueda
    Item clave = new Item(id);

    // Localización
    int posicion = Collections.binarySearch(PRODUCTOS, clave, cmp);

    if (posicion < 0) {
      UtilesEntrada.hacerPausa("ERROR: Ítem NO encontrado");
    } else {
      // Obtiene Item
      Item i = PRODUCTOS.get(posicion);

      // Separador
      System.out.println("---");

      // Muestra Datos
      System.out.println("Item Seleccionado");
      System.out.println("-----------------");
      System.out.printf("Id .....: %d%n", i.getId());
      System.out.printf("Nombre .: %s%n", i.getNombre());
      System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
      System.out.printf("Color ..: %s%n",
              UtilesEnums.obtenerNombreColor(i.getColor()));

      // Separador
      System.out.println("---");

      // Confirmar Proceso
      if (UtilesEntrada.confirmarProceso("Confirmar Borrado (s/N) ...: ", false)) {
        // Eliminación Ítem
        PRODUCTOS.remove(posicion);

        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Ítem BORRADO correctamente");
      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Eliminación de ítem CANCELADO");
      }
    }
  }

  private void consultarItem() {
    // Cabecera
    System.out.println("Consultar Item");
    System.out.println("==============");

    // Consola > Clave
    int id = UtilesEntrada.leerEntero(
            "Id .....: ",
            "ERROR: Entrada Incorrecta");

    // Comparador de Búsqueda
    Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

    // Ordenacion
    Collections.sort(PRODUCTOS, cmp);

    // Clave de Busqueda
    Item clave = new Item(id);

    // Localización
    int posicion = Collections.binarySearch(PRODUCTOS, clave, cmp);

    if (posicion < 0) {
      UtilesEntrada.hacerPausa("ERROR: Ítem NO encontrado");
    } else {
      // Obtiene Item
      Item i = PRODUCTOS.get(posicion);

      // Separador
      System.out.println("---");

      // Muestra Datos
      System.out.println("Item Seleccionado");
      System.out.println("-----------------");
      System.out.printf("Id .....: %d%n", i.getId());
      System.out.printf("Nombre .: %s%n", i.getNombre());
      System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
      System.out.printf("Color ..: %s%n",
              UtilesEnums.obtenerNombreColor(i.getColor()));

      // Mensaje Informacivo
      UtilesEntrada.hacerPausa();
    }
  }

  private void modificarItem() {
    // Cabecera
    System.out.println("Modificación de Item");
    System.out.println("====================");

    // Consola > Clave
    int id = UtilesEntrada.leerEntero(
            "Id .....: ",
            "ERROR: Entrada Incorrecta");

    // Comparador de Búsqueda
    Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

    // Ordenacion
    Collections.sort(PRODUCTOS, cmp);

    // Clave de Busqueda
    Item clave = new Item(id);

    // Localización
    int posicion = Collections.binarySearch(PRODUCTOS, clave, cmp);

    if (posicion < 0) {
      UtilesEntrada.hacerPausa("ERROR: Ítem NO encontrado");
    } else {
      // Obtiene Item
      Item i = PRODUCTOS.get(posicion);

      // Separador
      System.out.println("---");

      // Muestra Datos
      System.out.println("Item Seleccionado");
      System.out.println("-----------------");
      System.out.printf("Id .....: %d%n", i.getId());
      System.out.printf("Nombre .: %s%n", i.getNombre());
      System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
      System.out.printf("Color ..: %s%n",
              UtilesEnums.obtenerNombreColor(i.getColor()));

      // Separador
      System.out.println("---");

      // Confirmar Proceso
      if (UtilesEntrada.confirmarProceso("Modificar ítem (s/N) ...: ", false)) {
        // Separador
        System.out.println("---");

        // Item Vacío
        Item aux = new Item(id);

        // Item Modificado
        System.out.println("Item Modificado");
        System.out.println("---------------");

        // Consola > Nombre
        do {
          aux.setNombre(UtilesEntrada.leerTexto("Nombre .: "));
        } while (aux.getNombre().equals(Item.DEF_ITEM_NOMBRE));

        // Consola > Precio
        do {
          aux.setPrecio(UtilesEntrada.leerReal("Precio .: ", "ERROR: Entrada incorrecta"));
        } while (aux.getPrecio() == Item.DEF_ITEM_PRECIO);

        // Consola > Color
        do {
          String nombreColor = UtilesEntrada.leerTexto("Color ..: ");

          Color color = UtilesEnums.generarColor(nombreColor.toLowerCase());

          aux.setColor(color);
        } while (aux.getColor().equals(Item.DEF_ITEM_COLOR));

        // Separador
        System.out.println("---");

        // Confirmar Sustitución
        if (UtilesEntrada.confirmarProceso("Guardar cambios (s/N) ...: ", false)) {
          // Sustitución Item
          PRODUCTOS.set(posicion, aux);

          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Ítem MODIFICADO correctamente");
        } else {
          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Modificación de ítem CANCELADA");
        }
      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Modificación de ítem CANCELADA");
      }
    }
  }

  private void listarItems() {
    // Cabecera
    System.out.println("Listado de Items");
    System.out.println("================");

    // Criterio de Ordenación/Filtrado
    System.out.printf("Criterio de Ordenación .: %S%n", criOrd.getNombre());
    System.out.printf("Criterio de Filtrado ...: %S%n", criFil.getNombre());

    // Separados
    System.out.println("---");
    UtilesMenus.muestraBannerListarItems();
    // Filtrado > Selección Colección
    List<Item> lista = criFil.equals(Criterio.NINGUNO)
            ? PRODUCTOS : FILTRO_PRODUCTOS;

    // Recorrido Colección
    for (Item item : lista) {
      System.out.println(item.toString());
    }
    // Pausai
    UtilesEntrada.hacerPausa();
  }

  private void listarItemsUser() {
    // Cabecera
    System.out.println("Listado de Items");
    System.out.println("================");

    // Criterio de Ordenación/Filtrado
    System.out.printf("Criterio de Ordenación .: %S%n", criOrd.getNombre());
    System.out.printf("Criterio de Filtrado ...: %S%n", criFil.getNombre());

    // Separados
    System.out.println("---");
    UtilesMenus.muestraBannerListarItemsUsers();
    // Filtrado > Selección Colección
    List<Item> lista = criFil.equals(Criterio.DISP_ITEM)
            ? PRODUCTOS : FILTRO_PRODUCTOS;

    try {
      // Recorrido Colección
      lista.forEach((item) -> {
        if (!item.isLocked() && item.getEstado().equals(EstadoItem.DISPONIBLE)) {
          item.muestraInfoItemUser();
        } else {
          lista.remove(item);
        }
      });
    } catch (Exception e) {
      System.out.println("La lista está vacía.");
    }
    // Pausai
    UtilesEntrada.hacerPausa();
  }

  //cuenta los items del mismo tipo
  private int cantidadItemsDisp() {
    int cont = 0;

    // Filtrado > Selección Colección
    List<Item> lista = criFil.equals(Criterio.NINGUNO)
            ? PRODUCTOS : FILTRO_PRODUCTOS;

    // Recorrido Colección
    for (Item item : lista) {
      if (!item.isLocked()) {
        cont++;
      }
    }

    return cont;
  }

  /* 
          
          SECCIÓN MENU ADMIN USER 
  
   */
  private void agregarUser() {
    // Cabecera
    System.out.println("Inserción de Usuario");
    System.out.println("=================");

    // Consola > Clave
    String user = UtilesEntrada.leerTexto("Username .....: ");

    // Comparador de Búsqueda
    Comparator<User> cmp = new ComparadorUser(Criterio.USER);

    // Ordenacion
    Collections.sort(USERS, cmp);

    // Clave de Busqueda
    User clave = new User(user, null);

    // Proceso de Busqueda
    int posicion = Collections.binarySearch(USERS, clave, cmp);

    // Analisis Resultado Busqueda
    if (posicion < 0) {
      // Obtiene Item
      User u = new User(user, null);

      // Separador
      System.out.println("---");

      // Consola > Nombre  - Como ya tenemos el campo user simplemente se añade.
      u.setUser(user);

      // Consola > Password | RECICLAMOS código del registro de usuarios aquí.
      u.setPass(UtilesPassword.pidePass(user, u));

      // Consola > Nombre
      do {
        u.setNombre(UtilesEntrada.leerTexto("Nombre ..:"));
      } while (u.getNombre().equals(Persona.DEF_NOMBRE));

      // Consola > 1er Apellido
      do {
        u.setApellido1(UtilesEntrada.leerTexto("1er Apellido ..:"));
      } while (u.getApellido1().equals(Persona.DEF_APELL1));

      // Consola > 2o Apellido
      do {
        u.setApellido2(UtilesEntrada.leerTexto("2o Apellido ...:"));
      } while (u.getApellido2().equals(Persona.DEF_APELL2));

      // Consola > DNI
      do {
        u.setDni(UtilesEntrada.leerTexto("DNI ...:"));
      } while (u.getDni().equals(Persona.DEF_DNI));

      // Consola > Rol
      do {
        String nombreRol = UtilesEntrada.leerTexto("Rol ..: ");

        IdRol rol = UtilesEnums.generarRol(nombreRol.toLowerCase());

        u.setRol(rol);
      } while (u.getRol().equals(User.DEF_ID_ROL_USER));

      u.setCuentaActiva(true);
      /*  
      
      ¿Tiene sentido aquí que el ADMIN pueda crear una cuenta con estado NO
       ACTIVA si no puede hacer ni login aparentemente...? 
      
      Pendiente, de momento...
       */
      System.out.println("---");

      // Consola > Datos Nuevo Item
      System.out.println("User a insertar");
      System.out.println("---------------");
      System.out.printf("USER ..........: %s%n", u.getUser());
      System.out.printf("PASSWORD ......: %s%n", Arrays.toString(u.getPass()));
      // Separador
      System.out.println("---");
      System.out.printf("NOMBRE ........: %s%n", u.getNombre());
      System.out.printf("APELLIDOS .....: %s %s%n",
              u.getApellido1(), u.getApellido2());
      System.out.printf("DNI ...........: %s%n", u.getDni());
      System.out.printf("Rol ...........: %s%n",
              UtilesEnums.obtenerNombreRol(u.getRol()));
      System.out.printf("Cuenta activa : %b%n", u.isCuentaActiva());
      System.out.println();
      System.out.println("Se va a proceder a crear un usuario con cuenta ACTIVA");

      // Separador
      System.out.println("---");

      // Confirmar Proceso
      if (UtilesEntrada.confirmarProceso("Confirmar Inserción (S/n) ...: ", true)) {
        // Insercion Ítem
        USERS.add(u);

        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Usuario INSERTADO correctamente");
      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Inserción de Usuario CANCELADA");
      }
    } else {
      UtilesEntrada.hacerPausa("ERROR: Usuario ya existe");
    }
  }

  private void borrarUser() {
    // Cabecera
    System.out.println("Borrado de User");
    System.out.println("===============");

    // Consola > Clave
    String user = UtilesEntrada.leerTexto("Username .....: ");

    // Comparador de Búsqueda
    Comparator<User> cmp = new ComparadorUser(Criterio.USER);

    // Ordenacion
    Collections.sort(USERS, cmp);

    // Clave de Busqueda
    User clave = new User(user, null);

    // Proceso de Busqueda
    int posicion = Collections.binarySearch(USERS, clave, cmp);

    if (posicion < 0) {
      UtilesEntrada.hacerPausa("ERROR: Usuario NO encontrado");
    } else {
      // Obtiene Item
      User u = USERS.get(posicion);

      // Separador
      System.out.println("---");

      // Muestra Datos
      System.out.println("Usuario Seleccionado");
      System.out.println("--------------------");
      System.out.printf("Username ....: %s%n", u.getUser());
      System.out.printf("Nombre ......: %s%n", u.getNombre());
      System.out.printf("Apellidos ...: %s %s%n", u.getApellido1(), u.getApellido2());
      System.out.printf("DNI .........: %s%n", u.getDni());

      // Separador
      System.out.println("---");

      // Confirmar Proceso
      if (UtilesEntrada.confirmarProceso("Confirmar Borrado (s/N) ...: ", false)) {
        // Eliminación Ítem
        USERS.remove(posicion);

        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("User BORRADO correctamente");
      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Eliminación de User CANCELADO");
      }
    }
  }

  private void consultarUser() {
    // Cabecera
    System.out.println("Consultar User");
    System.out.println("==============");

    // Consola > Clave
    String user = UtilesEntrada.leerTexto("Username .....: ");

    // Comparador de Búsqueda
    Comparator<User> cmp = new ComparadorUser(Criterio.USER);

    // Ordenacion
    Collections.sort(USERS, cmp);

    // Clave de Busqueda
    User clave = new User(user, null);

    // Proceso de Busqueda
    int posicion = Collections.binarySearch(USERS, clave, cmp);

    if (posicion < 0) {
      UtilesEntrada.hacerPausa("ERROR: User NO encontrado");
    } else {
      // Obtiene User
      User u = USERS.get(posicion);

      // Separador
      System.out.println("---");

      // Muestra Datos
      System.out.println("Usuario Seleccionado");
      System.out.println("--------------------");
      System.out.printf("Username ....: %s%n", u.getUser());
      System.out.printf("Nombre ......: %s%n", u.getNombre());
      System.out.printf("Apellidos ...: %s %s%n", u.getApellido1(), u.getApellido2());
      System.out.printf("DNI .........: %s%n", u.getDni());
    }
  }

  private void habilitarUser() {
    if (UtilesUser.muestraInactivos(USERS) == 0) {
      System.out.printf("ERROR: No hay usuarios INACTIVOS ahora mismo.%n%n");
    } else {
      //Separador
      System.out.println();

      //ConsultamosUser
      // Cabecera
      System.out.println("Habilitar User");
      System.out.println("==============");

      // Consola > Clave
      String user = UtilesEntrada.leerTexto("Username .....: ");

      // Comparador de Búsqueda
      Comparator<User> cmp = new ComparadorUser(Criterio.USER);

      // Ordenacion
      Collections.sort(USERS, cmp);

      // Clave de Busqueda
      User clave = new User(user, null);

      // Proceso de Busqueda
      int posicion = Collections.binarySearch(USERS, clave, cmp);

      if (posicion < 0) {
        UtilesEntrada.hacerPausa("ERROR: User NO encontrado");
      } else {
        // Obtiene User
        User u = USERS.get(posicion);

        //si por lo que sea se introduce un user válido y activo.
        if (u.isCuentaActiva()) {
          System.out.printf("%nERROR EN LA BÚSQUEDA: Cuenta ya activa%n%n");
        } else {
          // Separador
          System.out.println("---");

          // Muestra Datos
          System.out.println("Usuario Seleccionado");
          System.out.println("--------------------");
          System.out.printf("Username ....: %s%n", u.getUser());
          System.out.println("---");
          System.out.printf("Nombre ......: %s%n", u.getNombre());
          System.out.printf("Apellidos ...: %s %s%n", u.getApellido1(), u.getApellido2());
          System.out.printf("DNI .........: %s%n", u.getDni());
          System.out.printf("ACTIVO ......: %b%n", u.isCuentaActiva());
          System.out.println();
          // Confirmar Sustitución
          if (UtilesEntrada.confirmarProceso("Activar Usuario (S/n) ...: ", true)) {
            // Sustitución Item
            u.setCuentaActiva(true);
            u.setRol(IdRol.USER);

            // Mensaje Informacivo
            UtilesEntrada.hacerPausa("Usuario ACTIVADO correctamente");
          } else {
            // Mensaje Informacivo
            UtilesEntrada.hacerPausa("Activación del Usuario CANCELADA");
          }
        }
      }
    }
  }

  private void modificarUser() {
    // Cabecera
    System.out.println("Modificación de Usuario");
    System.out.println("=======================");

    // Consola > Clave
    String user = UtilesEntrada.leerTexto("Username .....: ");

    // Comparador de Búsqueda
    Comparator<User> cmp = new ComparadorUser(Criterio.USER);

    // Ordenacion
    Collections.sort(USERS, cmp);

    // Clave de Busqueda
    User clave = new User(user, null);

    // Proceso de Busqueda
    int posicion = Collections.binarySearch(USERS, clave, cmp);

    if (posicion < 0) {
      UtilesEntrada.hacerPausa("ERROR: Usuario NO encontrado");
    } else {
      // Obtiene Usuario
      User u = USERS.get(posicion);

      // Separador
      System.out.println("---");

      // Muestra Datos
      u.muestraPersona();

      // Separador
      System.out.println("---");

      // Confirmar Proceso
      if (UtilesEntrada.confirmarProceso("Modificar Usuario (s/N) ...: ", false)) {
        // Separador
        System.out.println("---");

        // User Vacío
        User aux = new User(user, null);

        // Item Modificado
        System.out.println("Usuario Modificado");
        System.out.println("---------------");

        // Consola > Password | RECICLAMOS código del registro de usuarios aquí.
        aux.setPass(UtilesPassword.pidePass(user, u));

        // Consola > Nombre
        do {
          aux.setNombre(UtilesEntrada.leerTexto("Nombre ..:"));
        } while (aux.getNombre().equals(u.getNombre()));

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

        // Consola > Rol
        do {
          String nombreRol = UtilesEntrada.leerTexto("Rol ..: ");
          IdRol rol = UtilesEnums.generarRol(nombreRol.toLowerCase());
          aux.setRol(rol);
        } while (aux.getRol().equals(User.DEF_ID_ROL_USER));

        //Consola > activa/desactiva cuenta 
        if (u.isCuentaActiva()) {
          System.out.println();
          if (UtilesEntrada.confirmarProceso("¿DESACTIVAR cuenta? (S/n) ...: ", true)) {
            aux.setCuentaActiva(false);
            UtilesEntrada.hacerPausa("Cuenta DESACTIVADA correctamente.");
          } else {
            // Mensaje Informacivo
            UtilesEntrada.hacerPausa("No se desactiva la cuenta.");
          }
        } else {  //si la cuenta no está activa.
          System.out.println();
          if (UtilesEntrada.confirmarProceso("¿ACTIVAR cuenta? (S/n) ...: ", true)) {
            aux.setCuentaActiva(true);
            UtilesEntrada.hacerPausa("Cuenta ACTIVADA correctamente.");
          } else {
            // Mensaje Informacivo
            UtilesEntrada.hacerPausa("Se cancela la activación de cuentas.");
          }
        }
        // Separador   
        System.out.println("---");

        // Confirmar Sustitución
        if (UtilesEntrada.confirmarProceso("Guardar cambios (s/N) ...: ", false)) {
          // Sustitución Item
          USERS.set(posicion, aux);

          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Usuario MODIFICADO correctamente");
        } else {
          // Mensaje Informacivo
          UtilesEntrada.hacerPausa("Modificación de Usuario CANCELADA");
        }

      } else {
        // Mensaje Informacivo
        UtilesEntrada.hacerPausa("Modificación de Usuario CANCELADA");
      }
    }
  }

  private void listarUsers() {
    // Cabecera
    System.out.println("Listado de Users");
    System.out.println("================");

    // Criterio de Ordenación/Filtrado
    System.out.printf("Criterio de Ordenación .: %S%n", criOrd.getNombre());
    System.out.printf("Criterio de Filtrado ...: %S%n", criFil.getNombre());

    // Separados
    System.out.println("---");

    // Filtrado > Selección Colección
    List<User> lista = criFil.equals(Criterio.NINGUNO)
            ? USERS : FILTRO_USERS;

    // Recorrido Colección
    for (User u : lista) {
      System.out.println(u.toString());
    }

    // Pausai
    UtilesEntrada.hacerPausa();
  }

  /* 
      
          PROCESADO DE LOS FILTROS DE ITEMS
  
   */
  private void procesarMenuFiltroItems() {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_FILT_ITEMS,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_FILT_ITEMS);

      // Separador
      System.out.println("---");

      // Registro Criterio
      switch (opcion) {
        case 'd':
        case 'D':
          desactivarFiltroItems(true);
          salirOK = true;
          break;
        case 'i':
        case 'I':
          activarFiltroItemId();
          salirOK = true;
          break;
        case 'n':
        case 'N':
          criFil = Criterio.NOMBRE;
          activarFiltroItemNombre();
          salirOK = true;
          break;
        case 'p':
        case 'P':
          criFil = Criterio.PRECIO;
          activarFiltroPrecio();
          salirOK = true;
          break;
        case 'c':
        case 'C':
          criFil = Criterio.COLOR;
          activarFiltroColor();
          salirOK = true;
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);

  }

  private void desactivarFiltroItems(boolean pausaOK) {
    // Registra Estado Filtro
    criFil = Criterio.NINGUNO;

    // Vacia Filtro
    FILTRO_PRODUCTOS.clear();

    // Mensaje Informativo
    if (pausaOK) {
      UtilesEntrada.hacerPausa("Filtro DESACTIVADO");
    } else {
      System.out.println("Filtro DESACTIVADO");
    }
  }

  private void activarFiltroItemId() {
    System.out.println("Activación Filtro ID");
    System.out.println("====================");

    // Registrar Filtro
    criFil = Criterio.ID;

    // Establecer Rango
    int idMin = UtilesEntrada.leerEntero("Id mínima ....: ",
            "ERROR: Entrada incorrecta", 0, Integer.MAX_VALUE);
    int idMax = UtilesEntrada.leerEntero("Id máxima ....: ",
            "ERROR: Entrada incorrecta", idMin, Integer.MAX_VALUE);

    // Vaciar Filtro
    FILTRO_PRODUCTOS.clear();

    // Filtrado de Datos
    for (Item item : PRODUCTOS) {
      if (item.getId() >= idMin && item.getId() <= idMax) {
        FILTRO_PRODUCTOS.add(item);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro pod ID establecido");
  }

  private void activarFiltroItemNombre() {
    String nombre = "";
    System.out.println("Activación Filtro NOMBRE");
    System.out.println("========================");

    // Registrar Filtro
    criFil = Criterio.NOMBRE;

    do {
      // Establecer Patrón
      nombre = UtilesEntrada.leerTexto("Patrón Nombre ...: ");
      if (nombre.equals("")) {
        System.out.println("ERROR: El patrón no puede estar vacío.");
        System.out.println("---");
      }
    } while (nombre.equals(""));

    // Expresion Regular
    String patron = String.format(".*%s.*", nombre);

    // Vaciar Filtro
    FILTRO_PRODUCTOS.clear();

    // Filtrado de Datos
    for (Item item : PRODUCTOS) {
      if (UtilesValidacion.validar(item.getNombre(), patron)
              && !item.isLocked()) {
        FILTRO_PRODUCTOS.add(item);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por NOMBRE establecido");
  }

  private void activarFiltroItemNombre(List<Item> filtro) {
    String nombre = "";
    System.out.println("Activación Filtro NOMBRE");
    System.out.println("========================");

    // Registrar Filtro
    criFil = Criterio.NOMBRE;

    do {
      // Establecer Patrón
      nombre = UtilesEntrada.leerTexto("Patrón Nombre ...: ");
      if (nombre.equals("")) {
        System.out.println("ERROR: El patrón no puede estar vacío.");
        System.out.println("---");
      }
    } while (nombre.equals(""));

    // Expresion Regular
    String patron = String.format(".*%s.*", nombre);

    // Vaciar Filtro
    filtro.clear();

    // Filtrado de Datos
    for (Item item : PRODUCTOS) {
      if (UtilesValidacion.validar(item.getNombre(), patron)
              && !item.isLocked()
              && item.getEstado().equals(EstadoItem.DISPONIBLE)) {
        filtro.add(item);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por NOMBRE establecido");
  }

  private void activarFiltroPrecio() {
    System.out.println("Activación Filtro PRECIO");
    System.out.println("========================");

    // Registrar Filtro
    criFil = Criterio.PRECIO;

    // Establecer Rango
    double precioMin = UtilesEntrada.leerReal("Precio mínimo .: ",
            "ERROR: Entrada incorrecta", 0, Double.MAX_VALUE);
    double precioMax = UtilesEntrada.leerReal("Precio máximo .: ",
            "ERROR: Entrada incorrecta", precioMin, Double.MAX_VALUE);

    // Vaciar Filtro
    FILTRO_PRODUCTOS.clear();

    // Filtrado de Datos
    for (Item item : PRODUCTOS) {
      if (item.getPrecio() >= precioMin && item.getPrecio() <= precioMax) {
        FILTRO_PRODUCTOS.add(item);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por PRECIO establecido");
  }

  private void activarFiltroColor() {
    System.out.println("Activación Filtro COLOR");
    System.out.println("=======================");

    // Registrar Filtro
    criFil = Criterio.COLOR;

    // Establecer Patrón
    String color = UtilesEntrada.leerTexto("Patrón Color ...: ");

    // Expresion Regular
    String patron = String.format(".*%s.*", color);

    // Vaciar Filtro
    FILTRO_PRODUCTOS.clear();

    // Filtrado de Datos
    for (Item item : PRODUCTOS) {
      if (UtilesValidacion.validar(UtilesEnums.
              obtenerNombreColor(item.getColor()), patron)) {
        FILTRO_PRODUCTOS.add(item);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por COLOR establecido");
  }

  private List<Item> activarFiltroItemDisp() {
    System.out.println("Activación Filtro DISPONIBILIDAD");
    System.out.println("=======================");

    // Registrar Filtro
    criFil = Criterio.DISP_ITEM;

    // Establecer Patrón
    String color = UtilesEnums.obtenerNombreEstadoItem(EstadoItem.DISPONIBLE);

    // Expresion Regular
    String patron = String.format(".*%s.*", color);

    // Vaciar Filtro
    FILTRO_PRODUCTOS.clear();

    // Filtrado de Datos
    for (Item item : PRODUCTOS) {
      if (UtilesValidacion.validar(UtilesEnums.
              obtenerNombreEstadoItem(item.getEstado()), patron)) {
        FILTRO_PRODUCTOS.add(item);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro mostrando productos EstadoItem.DISPONIBLE");
    System.out.println("DEVUELVE List<Item> con filtroproductos.");
    return FILTRO_PRODUCTOS;
  }

  /* 
      
          PROCESADO DE LOS FILTROS DE USUARIOS
  
   */
  private void procesarMenuFiltroUsers() {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_FILT_USER,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_FILT_USER);

      // Separador
      System.out.println("---");

      // Registro Criterio
      switch (opcion) {
        case 'd':
        case 'D':
          desactivarFiltroUsers(true);
          salirOK = true;
          break;
        case 'u':
        case 'U':
          activarFiltroUser();
          salirOK = true;
          break;
        case 'r':
        case 'R':
          criFil = Criterio.ROL;
          activarFiltroRol();
          salirOK = true;
          break;
        case 'e':
        case 'E':
          criFil = Criterio.CUENTA_ACTIVA;
          activarFiltroEstadoCuenta();
          salirOK = true;
          break;
        case 'm':
        case 'M':
          criFil = Criterio.MONEDERO;
          activarFiltroMonedero();
          salirOK = true;
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);

  }

  private void desactivarFiltroUsers(boolean pausaOK) {
    // Registra Estado Filtro
    criFil = Criterio.NINGUNO;

    // Vacia Filtro
    FILTRO_USERS.clear();

    // Mensaje Informativo
    if (pausaOK) {
      UtilesEntrada.hacerPausa("Filtro DESACTIVADO");
    } else {
      System.out.println("Filtro DESACTIVADO");
    }
  }

  private void activarFiltroUser() {
    System.out.println("Activación Filtro User");
    System.out.println("==========================");

    // Registrar Filtro
    criFil = Criterio.USER;

    // Establecer Patrón
    String nombre = UtilesEntrada.leerTexto("Patrón User ...: ");

    // Expresion Regular
    String patron = String.format(".*%s.*", nombre);

    // Vaciar Filtro
    FILTRO_USERS.clear();

    // Filtrado de Datos
    for (User u : USERS) {
      if (UtilesValidacion.validar(u.getNombre(), patron)) {
        FILTRO_USERS.add(u);
      }
    }
    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por NOMBRE establecido");
  }

  private void activarFiltroRol() {
    System.out.println("Activación Filtro ROL");
    System.out.println("=======================");

    // Registrar Filtro
    criFil = Criterio.ROL;

    // Establecer Patrón
    String rol = UtilesEntrada.leerTexto("Patrón Rol ...: ");

    // Expresion Regular
    String patron = String.format(".*%s.*", rol);

    // Vaciar Filtro
    FILTRO_USERS.clear();

    // Filtrado de Datos
    for (User u : USERS) {
      if (UtilesValidacion.validar(UtilesEnums.
              obtenerNombreRol(u.getRol()), patron)) {
        FILTRO_USERS.add(u);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por ROL establecido");
  }

  private void activarFiltroEstadoCuenta() {
    System.out.println("Activación Filtro CUENTA ACTIVA");
    System.out.println("=================================");

    // Registrar Filtro
    criFil = Criterio.CUENTA_ACTIVA;

    // Establecer Patrón
    boolean cuenta = UtilesEntrada.leerBoolean("Patrón cuenta activa ...: ",
            "ERROR: Formato de filtro incorrecto");

    // Expresion Regular
    String patron = String.format(".*%s.*", cuenta);

    // Vaciar Filtro
    FILTRO_USERS.clear();

    // Filtrado de Datos
    for (User u : USERS) {
      if (UtilesValidacion.validar(
              UtilesEnums.obtenerNombreActivo(u.isCuentaActiva()), patron)) {
        FILTRO_USERS.add(u);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por ESTADO establecido");
  }

  private void activarFiltroMonedero() {
    System.out.println("Activación Filtro MONEDERO");
    System.out.println("=======================");

    // Registrar Filtro
    criFil = Criterio.MONEDERO;

    // Establecer Rango
    double min = UtilesEntrada.leerReal("Valor mínimo ....: ",
            "ERROR: Entrada incorrecta", 0, Integer.MAX_VALUE);
    double max = UtilesEntrada.leerReal("Valor máximo ....: ",
            "ERROR: Entrada incorrecta", min, Integer.MAX_VALUE);

    // Vaciar Filtro0
    FILTRO_USERS.clear();

    // Filtrado de Datos
    for (User u : USERS) {
      if (u.totalSaldoMonedero() >= min && u.totalSaldoMonedero() <= max) {
        FILTRO_USERS.add(u);
      }
    }

    // Mensaje Informativo
    UtilesEntrada.hacerPausa("Filtro por VALOR establecido");
  }

  /* 
      
          PROCESADO DE LA ORDENACIÓN DE ITEMS
  
   */
  private void procesarMenuOrdenItems() {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_ORDE_ITEMS,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_ORDE_ITEMS);

      // Separador
      System.out.println("---");

      // Registro Criterio Ordenacion
      switch (opcion) {
        case 'i':
        case 'I':
          ordenarItems(Criterio.ID);
          salirOK = true;
          break;
        case 'n':
        case 'N':
          ordenarItems(Criterio.NOMBRE);
          salirOK = true;
          break;
        case 'p':
        case 'P':
          ordenarItems(Criterio.PRECIO);
          salirOK = true;
          break;
        case 'c':
        case 'C':
          ordenarItems(Criterio.COLOR);
          salirOK = true;
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);
  }

  // Lista > Ordenada por ID
  private void ordenarItems(Criterio criOrd) {
    // Ordenación > Desactivación Filtro
    desactivarFiltroItems(false);

    // Registra Criterio Ordenación
    this.criOrd = criOrd;

    // Ordenación Colección
    Collections.sort(PRODUCTOS, new ComparadorItem(criOrd));

    // Mensaje
    System.out.printf("Items ordenados por %s%n", criOrd.getNombre());

    // Pausa
    UtilesEntrada.hacerPausa();
  }

  /* 
      
          PROCESADO DE LA ORDENACIÓN DE USERS
  
   */
  private void procesarMenuOrdenUsers() {
    // Semaforo control bucle
    boolean salirOK = false;

    // Bucle Menú
    do {
      // Consola + Opciones > Opcion
      char opcion = UtilesEntrada.obtenerOpcion(
              UtilesMenus.TXT_MENU_ORDE_USER,
              UtilesMenus.TXT_MENU_ERROR,
              UtilesMenus.OPC_MENU_ORDE_USER);

      // Separador
      System.out.println("---");

      // Registro Criterio Ordenacion
      switch (opcion) {
        case 'u':
        case 'U':
          ordenarUsers(Criterio.USER);
          salirOK = true;
          break;
        case 'r':
        case 'R':
          ordenarUsers(Criterio.ROL);
          salirOK = true;
          break;
        case 'e':
        case 'E':
          ordenarUsers(Criterio.CUENTA_ACTIVA);
          salirOK = true;
          break;
        case 'x':
        case 'X':
          salirOK = true;
          break;
        default:
          System.out.println("ERROR: Opción NO disponible");
          System.out.println("---");
      }
    } while (!salirOK);
  }

  // Lista > Ordenada por ID
  private void ordenarUsers(Criterio criOrd) {
    // Ordenación > Desactivación Filtro
    desactivarFiltroUsers(false);

    // Registra Criterio Ordenación
    this.criOrd = criOrd;

    // Ordenación Colección
    Collections.sort(USERS, new ComparadorUser(criOrd));

    // Mensaje
    System.out.printf("Users ordenados por %s%n", criOrd.getNombre());

    // Pausa
    UtilesEntrada.hacerPausa();
  }

}
