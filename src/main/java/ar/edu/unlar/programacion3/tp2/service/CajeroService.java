package ar.edu.unlar.programacion3.tp2.service;

import ar.edu.unlar.programacion3.tp2.model.*;
import ar.edu.unlar.programacion3.tp2.exception.*;
import ar.edu.unlar.programacion3.tp2.util.GeneradorLog;

public class CajeroService {

    public void consultarSaldo(CuentaBancaria c) throws CuentaInactivaException {
        if (!c.isActiva()) throw new CuentaInactivaException("Cuenta inactiva.");
        registrar(c, TipoTransaccion.CONSULTA, 0, "Consulta de saldo en cajero");
    }

    public void depositar(CuentaBancaria c, double monto) throws CuentaInactivaException {
        if (!c.isActiva()) throw new CuentaInactivaException("Cuenta inactiva.");
        if (monto > 0) {
            c.setSaldo(c.getSaldo() + monto);
            registrar(c, TipoTransaccion.DEPÓSITO, monto, "Depósito en efectivo");
        }
    }

    public void extraer(CuentaBancaria c, double monto) throws Exception {
        if (!c.isActiva()) throw new CuentaInactivaException("Cuenta inactiva.");
        if (monto > 10000) throw new LimiteExtraccionExcedidoException("Límite diario de $10,000 excedido.");
        if (monto > c.getSaldo()) throw new SaldoInsuficienteException("Saldo insuficiente.");

        c.setSaldo(c.getSaldo() - monto);
        registrar(c, TipoTransaccion.EXTRACCIÓN, monto, "Extracción en cajero");
    }

    public void transferir(CuentaBancaria origen, CuentaBancaria destino, double monto) throws Exception {
        if (!origen.isActiva() || !destino.isActiva()) throw new CuentaInactivaException("Una de las cuentas está inactiva.");
        if (monto > origen.getSaldo()) throw new SaldoInsuficienteException("Saldo insuficiente para transferir.");
        
      
        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);
        
        registrar(origen, TipoTransaccion.TRANSFERENCIA, monto, "Transferencia enviada a " + destino.getNumeroCuenta());
        registrar(destino, TipoTransaccion.TRANSFERENCIA, monto, "Transferencia recibida de " + origen.getNumeroCuenta());
    }

    private void registrar(CuentaBancaria c, TipoTransaccion t, double m, String desc) {
        c.getHistorialTransacciones().add(new CuentaBancaria.Transaccion(t, m, desc));
        System.out.println(GeneradorLog.generar(t, m, c.getSaldo()));
    }
}