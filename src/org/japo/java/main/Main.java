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
package org.japo.java.main;

import java.io.IOException;
import java.util.Properties;
import org.japo.java.app.App;
import org.japo.java.libraries.UtilesApp;

/**
 *
 * @author mon-mode
 */
public final class Main {

    //clave de acceso: Declaramos la constante del serial como private para que 
    //no pueda ser accesible desde fuera.
    private static final String ACCESS_UID = "mon_mode29082020";

    //Punto de entrada al programa
    public static final void main(String[] args) throws IOException {

      Properties prp = UtilesApp.importarPropiedades();
      
        if (args.length == 1 && args[0].equals(ACCESS_UID)) {
            // Crear aplicación
            final App APP = new App(prp);
            //Lanzar aplicación
            APP.launchApp();
        } else {
            System.out.println("Acceso Denegado");
            System.out.println("---");
            System.out.println("Contacte con su servicio técnico");
        }
    }
}
