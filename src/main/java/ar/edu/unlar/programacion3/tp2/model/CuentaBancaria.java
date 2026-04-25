package ar.edu.unlar.programacion3.tp2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CuentaBancaria {
    private final String numeroCuenta;
    private double saldo;
    private String titular;
    private boolean activa;
    private ArrayList<Transaccion> historialTransacciones;

    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.activa = true;
        this.historialTransacciones = new ArrayList<>();
    }

  
    public static class Transaccion {
        public TipoTransaccion tipo;
        public double monto;
        public LocalDateTime fechaHora;
        public String descripcion;

        public Transaccion(TipoTransaccion tipo, double monto, String descripcion) {
            this.tipo = tipo;
            this.monto = monto;
            this.fechaHora = LocalDateTime.now();
            this.descripcion = descripcion;
        }
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getTitular() { return titular; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public ArrayList<Transaccion> getHistorialTransacciones() { return historialTransacciones; }
}

