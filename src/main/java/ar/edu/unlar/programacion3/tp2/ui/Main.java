package ar.edu.unlar.programacion3.tp2.ui;

import ar.edu.unlar.programacion3.tp2.model.CuentaBancaria;
import ar.edu.unlar.programacion3.tp2.service.CajeroService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CajeroService service = new CajeroService();
        
    
        CuentaBancaria cuentaPrincipal = new CuentaBancaria("CBU-001", "Lea", 50000.0);
        CuentaBancaria cuentaSecundaria = new CuentaBancaria("CBU-002", "Ana", 15000.0);
        CuentaBancaria cuentaAhorro = new CuentaBancaria("CBU-003", "Nico", 2000.0);

        System.out.println("=== INICIANDO SIMULACIÓN DE JORNADA (15 TRANSACCIONES) ===");
        try {
           
            service.depositar(cuentaPrincipal, 1500.0);      // 1
            service.extraer(cuentaPrincipal, 2000.0);        // 2
            service.consultarSaldo(cuentaSecundaria);        // 3
            service.transferir(cuentaPrincipal, cuentaAhorro, 500.0); // 4, 5 (Atómica: 2 registros)
            service.depositar(cuentaAhorro, 300.0);          // 6
            service.extraer(cuentaSecundaria, 1000.0);       // 7
            service.depositar(cuentaPrincipal, 200.0);       // 8
            service.consultarSaldo(cuentaAhorro);            // 9
          
            try { service.extraer(cuentaPrincipal, 15000.0); } catch (Exception e) { System.out.println("Aviso: " + e.getMessage()); } // 10 (Límite)
            try { service.extraer(cuentaAhorro, 9000.0); } catch (Exception e) { System.out.println("Aviso: " + e.getMessage()); }     // 11 (Saldo)
            
       
            service.depositar(cuentaPrincipal, 100.0); // 12
            service.depositar(cuentaSecundaria, 100.0);// 13
            service.depositar(cuentaAhorro, 100.0);    // 14
            service.consultarSaldo(cuentaPrincipal);   // 15
            
        } catch (Exception e) {
            System.out.println("Error en simulación: " + e.getMessage());
        }

    
        boolean salir = false;
        System.out.println("\nBienvenido/a: " + cuentaPrincipal.getTitular());
        
        while (!salir) {
            System.out.println("\n--- MENÚ CAJERO AUTOMÁTICO ---");
            System.out.println("1. Consultar Saldo");
            System.out.println("2. Depositar");
            System.out.println("3. Extraer");
            System.out.println("4. Transferir a Cuenta Ahorro");
            System.out.println("5. Ver Historial (Últimas 10)");
            System.out.println("6. Salir");
            System.out.print("Elija una opción: ");

            try {
                int op = sc.nextInt();
                
                
                switch (op) {
                    case 1 -> service.consultarSaldo(cuentaPrincipal);
                    case 2 -> {
                        System.out.print("Ingrese monto a depositar: $");
                        service.depositar(cuentaPrincipal, sc.nextDouble());
                    }
                    case 3 -> {
                        System.out.print("Ingrese monto a extraer: $");
                        service.extraer(cuentaPrincipal, sc.nextDouble());
                    }
                    case 4 -> {
                        System.out.print("Ingrese monto a transferir: $");
                        service.transferir(cuentaPrincipal, cuentaAhorro, sc.nextDouble());
                    }
                    case 5 -> {
                        System.out.println("\n--- ÚLTIMOS MOVIMIENTOS ---");
                        var historial = cuentaPrincipal.getHistorialTransacciones();
                        int inicio = Math.max(0, historial.size() - 10); 
                        for (int i = inicio; i < historial.size(); i++) {
                            CuentaBancaria.Transaccion t = historial.get(i);
                            System.out.printf("[%s] %s: $%,.2f - %s%n", 
                                t.fechaHora.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), 
                                t.tipo, t.monto, t.descripcion);
                        }
                    }
                    case 6 -> salir = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un valor numérico válido.");
                sc.nextLine(); 
            } catch (Exception e) {
                System.out.println("Operación denegada: " + e.getMessage());
            }
        }
        
        System.out.println("Gracias por operar con nosotros.");
        sc.close();
    }
}