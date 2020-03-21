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

import java.util.ArrayList;

/**
 *
 * @author Jonsui
 */
public class ListaLog {

  private final ArrayList<Login> logIn;
  private final ArrayList<Logout> logOut;
  private final ArrayList<LogRegistro> logReg;
  private final ArrayList<LogTry> logTrys;

  public ListaLog() {
    logIn = new ArrayList<>();
    logOut = new ArrayList<>();
    logReg = new ArrayList<>();
    logTrys = new ArrayList<>();
  }

  public ListaLog(ArrayList<Login> logIn, ArrayList<Logout> logOut,
          ArrayList<LogRegistro> logReg, ArrayList<LogTry> logTrys) {
    this.logIn = logIn;
    this.logOut = logOut;
    this.logReg = logReg;
    this.logTrys = logTrys;
  }

  public boolean addLogIn(Login logIn) {
    boolean isOk = false;
    if (logIn != null) {
      isOk = this.logIn.add(logIn);
    }
    return isOk;
  }

  public boolean addLogOut(Logout logOut) {
    boolean isOk = false;
    if (logOut != null) {
      isOk = this.logOut.add(logOut);
    }
    return isOk;
  }

  public boolean addLogReg(LogRegistro logReg) {
    boolean isOk = false;
    if (logReg != null) {
      isOk = this.logReg.add(logReg);
    }
    return isOk;
  }

  public boolean addLogTry(LogTry logTry) {
    boolean isOk = false;
    if (logTry != null) {
      isOk = this.logTrys.add(logTry);
    }
    return isOk;
  }

  public void mostrarLogins() {
    logIn.forEach((login) -> {
      login.muestraInfoLogin();
    });
    System.out.println("FIN: MUESTRA LOGIN");
  }

  public void mostrarLogouts() {
    logOut.forEach((logout) -> {
      logout.muestraInfoLogout();
    });
    System.out.println("FIN: MUESTRA LOGOUT");
  }

  public void mostrarLogReg() {
    logReg.forEach((logreg) -> {
      logreg.muestraInfoLogout();
    });
  }

  public void mostrarLogTry() {
    logTrys.forEach((logtry) -> {
      logtry.muestraInfoLogTry();
    });
  }

}
