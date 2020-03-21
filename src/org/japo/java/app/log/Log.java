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

import java.io.Serializable;
import org.japo.java.entities.User;
import org.japo.java.entities.datetime.Fecha;
import org.japo.java.entities.datetime.Hora;
import org.japo.java.enumerations.LogLevel;

/**
 *
 * @author Jonsui
 */
public abstract class Log implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final Fecha DEF_FECHA = Fecha.obtenerFechaSistema();
  public static final Hora DEF_HORA = new Hora();

  public static final String DEF_HORARIO = String.format("%s | %s", Fecha.obtenerFechaSistema(),
          new Hora());

  User user;
  Fecha date;
  Hora time;
  LogLevel level;

  public Log() {
  }

  public Log(User user) {
    this.user = user;
    this.date = DEF_FECHA;
    this.time = DEF_HORA;
    this.level = LogLevel.trace;
  }

  public Log(User user, String date, LogLevel level) {
    this.user = user;
    this.date = DEF_FECHA;
    this.time = DEF_HORA;
    this.level = LogLevel.trace;
  }

  public User getUser() {
    return user;
  }

  public Fecha getDate() {
    return date;
  }

  public Hora getTime() {
    return time;
  }

  public LogLevel getLevel() {
    return level;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setDate(Fecha date) {
    this.date = date;
  }

  public void setTime(Hora time) {
    this.time = time;
  }

  public void setLevel(LogLevel level) {
    this.level = level;
  }
}
