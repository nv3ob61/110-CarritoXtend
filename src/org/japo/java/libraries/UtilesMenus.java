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

/**
 *
 * @author mon_mo
 */
public class UtilesMenus {

  // separador de Línea
  public static final String LS = System.getProperty("line.separator");

  // Opciones Menús
  public static final String OPC_MENU_LOGIN = "lLRrxX";

  public static final String OPC_MENU_ADMIN = "uUiIcCoOpPxX";
  public static final String OPC_MENU_USER = "aAbBcClLmMtTreERxX";

  public static final String OPC_MENU_ADMIN_USER = "AaBbCchHMmXx";
  public static final String OPC_MENU_ADMIN_ITEM = "AaBbCcMmXx";
  public static final String OPC_MENU_ADMIN_DESEA = "iIuUxX";

  public static final String OPC_MENU_CONT = "LlFfOoXx";

  public static final String OPC_MENU_FILT_ITEMS = "DdIiNnPpCcXx";
  public static final String OPC_MENU_FILT_USER = "dDuUrReEmMxX";

  public static final String OPC_MENU_ORDE_ITEMS = "IiNnPpCcXx";
  public static final String OPC_MENU_ORDE_USER = "uUrReExX";

  public static final String OPC_MENU_PERS = "IiEeXx";

  //Err.msg.
  public static final String TXT_MENU_ERROR = "ERROR: Opción no válida";

  // Texto Menú Login
  public static final String TXT_MENU_LOGIN
          = "LOGIN INICIAL" + LS
          + "=============" + LS
          + "[ L ] Login de Usuario" + LS
          + "[ R ] Registro de nuevo Usuario" + LS
          + "---" + LS
          + "[ X ] Salir" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Admin
  public static final String TXT_MENU_ADMIN
          = "MENÚ DE ADMINISTRACIÓN" + LS
          + "======================" + LS
          + "[ I ] Gestión de Items" + LS
          + "[ U ] Gestión de Usuarios" + LS
          + "[ O ] Gestión de Pedidos" + LS
          + "---" + LS
          + "[ C ] Gestión Contenido" + LS
          + "[ P ] Gestión Persistencia" + LS
          + "---" + LS
          + "[ X ] Salir" + LS
          + "---" + LS
          + "Introducir opción: ";

  //Texto Menú User
  public static final String TXT_MENU_USER
          = "MENÚ USUARIO" + LS
          + "==========" + LS
          + "[ A ] Añadir item al carrito (ID)" + LS
          + "[ B ] Borrar item del carrito (ID)" + LS
          + "[ L ] Listar carrito" + LS
          + "---" + LS
          + "[ C ] Gestión Contenido" + LS
          + "[ M ] Modificar Datos" + LS
          + "---" + LS
          + "[ T ] Añadir tarjeta" + LS
          + "[ E ] Borrar tarjeta" + LS
          + "---" + LS
          + "[ R ] Realizar compra" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Administración Usuarios
  public static final String TXT_MENU_ADMIN_USER
          = "MENÚ ADMINISTRACIÓN USUARIOS" + LS
          + "============================" + LS
          + "[ A ] Agregar Usuario" + LS
          + "[ B ] Borrar Usuario" + LS
          + "[ C ] Consultar Usuario" + LS
          + "[ M ] Modificar Usuario" + LS
          + "---" + LS
          + "[ H ] Habilitar Usuario" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Administración Items
  public static final String TXT_MENU_ADMIN_ITEM
          = "MENÚ ADMINISTRACIÓN ITEMS" + LS
          + "=========================" + LS
          + "[ A ] Agregar item" + LS
          + "[ B ] Borrar Item" + LS
          + "[ C ] Consultar Item" + LS
          + "[ M ] Modificar Item" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Contenido, compartido por admins y users.
  public static final String TXT_MENU_CONT
          = "MENÚ CONTENIDO" + LS
          + "==============" + LS
          + "[ L ] Listar" + LS
          + "[ F ] Filtrar" + LS
          + "[ O ] Ordenar" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Admin qué desea filtrar? Items o Usuarios
  public static final String TXT_MENU_ADMIN_DESEA
          = "¿Qué desea procesar?" + LS
          + "==============" + LS
          + "[ I ] Items" + LS
          + "[ U ] Users" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Filtrado Items
  public static final String TXT_MENU_FILT_ITEMS
          = "MENÚ FILTRADO ITEMS" + LS
          + "===================" + LS
          + "[ D ] Desactivar" + LS
          + "---" + LS
          + "[ I ] Por ID" + LS
          + "[ N ] Por NOMBRE" + LS
          + "[ P ] Por PRECIO" + LS
          + "[ C ] Por COLOR" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Filtrado Usuarios
  public static final String TXT_MENU_FILT_USER
          = "MENÚ FILTRADO USUARIOS" + LS
          + "=============" + LS
          + "[ D ] Desactivar" + LS
          + "---" + LS
          + "[ U ] Por Usuario" + LS
          + "[ R ] Por ROL" + LS
          + "[ E ] Por Estado Cuenta" + LS
          + "[ M ] Por Valor de Monedero" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Ordenación Items
  public static final String TXT_MENU_ORDE_ITEMS
          = "MENÚ ORDENACIÓN ITEMS" + LS
          + "=====================" + LS
          + "[ I ] Por ID" + LS
          + "[ N ] Por NOMBRE" + LS
          + "[ P ] Por PRECIO" + LS
          + "[ C ] Por COLOR" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Ordenación Usuarios
  public static final String TXT_MENU_ORDE_USER
          = "MENÚ ORDENACIÓN USUARIOS" + LS
          + "========================" + LS
          + "[ U ] Por Usuario" + LS
          + "[ R ] Por ROL" + LS
          + "[ E ] Por Estado Cuenta" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  // Texto Menú Persistencia
  public static final String TXT_MENU_PERS
          = "MENÚ PERSISTENCIA" + LS
          + "=================" + LS
          + "[ I ] Importar Datos" + LS
          + "[ E ] Exportar Datos" + LS
          + "---" + LS
          + "[ X ] Menú Anterior" + LS
          + "---" + LS
          + "Introducir opción: ";

  public static final void muestrIntentos(int intentos) {
    //Si al comprobar las credenciales devuelve false...
    switch (intentos) {
      case 2:
        System.out.printf("Te quedan %d intentos%n%n", intentos);
        System.out.println("---");
        break;
      case 1:
        System.out.printf("Te queda %d intento%n%n", intentos);
        System.out.println("---");
        break;
      case 0:
        System.out.println("MENSAJE FIN DESDE muestraIntentos() cuando es 0.");
        System.out.println("---");
    }
  }

  public static void muestraBannerRegUsers() {
    System.out.println("-----------------------------------------------");
    System.out.println("          REGISTRO DE  NUEVO USUARIO");
    System.out.println("-----------------------------------------------");
    System.out.println();
  }

  public static final void muestraBannerListarItems() {
    //print de una cabecera
    System.out.printf("%n  NÚM   -  ITEM            -  PRECIO - COLOR"
            + "      | locked%n");
    lineaLong();
  }

  public static final void muestraBannerListarItemsUsers() {
    //print de una cabecera
    System.out.printf("%n  NÚM   -  ITEM            -  PRECIO - COLOR"
            + "      %n");
    lineaLong();
  }

  public static final void lineaLong() {
    System.out.println("-----------------------------------------------"
            + "-----------");
  }
}
