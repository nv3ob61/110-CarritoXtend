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
package org.japo.java.app.log;

import org.japo.java.entities.User;
import org.japo.java.enumerations.LogLevel;

/**
 *
 * @author Jonsui
 */
public class LogRegistro extends Log {

  private static final long serialVersionUID = 5L;

  public LogRegistro() {
  }
  
  public LogRegistro(User i){
    super();
    this.level = LogLevel.NEW_USER;
  }
  
  @Override
  public String toString() {
    return String.format("-----------------------------------------------------%n"
            + "INFO DE REGISTRO%n"
            + "NOMBRE DE USUARIO: %s%n"
            + "FECHA DE REGISTRO..: %s %s %n"
            + "TIPO DE ALARMA ..: %s %n"
            + "-----------------------------------------------------%n",
            getUser().getUser(), getDate(), getTime(), LogLevel.NEW_USER);
  }

  public void muestraInfoLogout() {
    System.out.println(toString());
  }
}
