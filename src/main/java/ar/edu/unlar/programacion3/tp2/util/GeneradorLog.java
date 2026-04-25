package ar.edu.unlar.programacion3.tp2.util;

import ar.edu.unlar.programacion3.tp2.model.TipoTransaccion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeneradorLog {
    public static String generar(TipoTransaccion tipo, double monto, double saldoFinal) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        sb.append("[").append(LocalDateTime.now().format(dtf)).append("] ");
        sb.append(tipo).append(": ");
        sb.append(String.format("$%,.2f", monto));
        sb.append(" | Saldo: ");
        sb.append(String.format("$%,.2f", saldoFinal));
        
        return sb.toString();
    }
}