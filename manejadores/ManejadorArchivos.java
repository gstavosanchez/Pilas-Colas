/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.manejadores;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Cancelado;
import org.gustavosanchez.bean.Carrito;
import org.gustavosanchez.bean.Factura;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.bean.TipoOferta;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.estructura.ListaCircular;
import org.gustavosanchez.estructura.ListaDobleCircular;

/**
 *
 * @author elmer
 */
public class ManejadorArchivos {

    private static ManejadorArchivos instancia;
    private ArrayList<ProductoOferta> listaProOferta = new ArrayList<>();
    private int contador = 1;

    private ManejadorArchivos() {
    }

    public static synchronized ManejadorArchivos getInstance() {
        if (instancia == null) {
            instancia = new ManejadorArchivos();
        }
        return instancia;
    }

    public boolean readInput(String file, String tipo) {
        int contador = 0;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String temp = "";
            while (temp != null) {
                temp = bufferedReader.readLine();
                if (temp == null) {
                    break;
                }
                System.out.println(temp.trim());
                if (tipo.equalsIgnoreCase("P")) {
                    addProducto(temp);
                } else if (tipo.equalsIgnoreCase("O")) {
                    addOferta(temp);

                }
                contador++;

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addProducto(String cadena) {
        String[] vector = cadena.split(",");
        if (vector != null) {
            int contador = ManejadorProducto.getInstance().getContadorProducto();
            String nombre = vector[0].trim();
            String descripcion = vector[1].trim();
            String precio = vector[2].trim();
            String existencia = vector[3].trim();
            String imagen = vector[4].trim();
            ManejadorProducto.getInstance().agregarProducto(String.valueOf(contador), nombre, precio, descripcion, existencia, imagen);
            contador++;
            ManejadorProducto.getInstance().setContadorProducto(contador);
        }

    }

    public void addOferta(String cadena) {
        String[] vectorOferta = cadena.split("%");
        String descripcion;
        String descuento;
        String producto;
        String prioridad;
        Producto pro;

        if (vectorOferta != null) {
            for (int i = 0; i < vectorOferta.length; i++) {
                System.out.println("Cadena NO:" + i + " Texto :" + vectorOferta[i]);
                String[] vectorAtributo = vectorOferta[i].split(",");
                descripcion = vectorAtributo[0];
                System.out.println("Descripcion: " + descripcion);
                descuento = vectorAtributo[1];
                System.out.println("Descuento: " + descuento);
                prioridad = vectorAtributo[3].toLowerCase();
                TipoOferta tipoOferta = ManejadorOferta.getInstance().getTipoOferta(prioridad);
                System.out.println("Prioridad: " + prioridad);
                producto = vectorAtributo[2];
                System.out.println("Producto: " + producto);
                String[] listaProducto = producto.split(";");
                LCircular<Producto> listaCircular = new LCircular<>();
                listaCircular.clear();
                for (String p : listaProducto) {
                    if (p != null) {
                        pro = ManejadorProducto.getInstance().seachProducto(p.trim());
                        if (pro != null) {
                            System.out.println(p);
                            System.out.println("Se agrego a lista No." + i + " El producto: " + pro.getNombre() + " con identificar: " + pro.getIdentificador());
                            listaCircular.add(pro);
                            double decimal = ((Double.parseDouble(descuento)) / 100);
                            double des = ((pro.getPreicio()) * (decimal));
                            double precioTotal = ((pro.getPreicio()) - (des));
                            //ProductoOferta proOferta = new ProductoOferta(contador, pro, precioTotal);
                            System.out.println("Antes del if de agregar ofertaController:" + precioTotal);
                            agregarProductoOferta(pro, precioTotal);

                        }

                    }

                }
                if (tipoOferta != null && !listaCircular.isEmpty() && ManejadorProducto.getInstance().isInteger(descuento.trim())) {
                    ManejadorOferta.getInstance().agregarOfertaInterfaz(descripcion, descuento, listaCircular, tipoOferta);
                    System.out.println("Se agrego la oferta No." + i);
                }

            }

        }

    }

    public void agregarProductoOferta(Producto pro, double precioTotal) {

        ProductoOferta nuevo = new ProductoOferta(contador, pro, precioTotal, pro.getPreicio());
        System.out.println("=======================================");
        System.out.println("Nuevo precio:" + nuevo.getPrecioOferta());
        listaProOferta.add(nuevo);
        contador++;

    }

    public ArrayList<ProductoOferta> getListaProOferta() {
        return listaProOferta;
    }

    public void escribirArchivo(LCircular<ProductoOferta> lista,File file) {
        System.out.println("Esta en el metod escribir");
        //File archivo = new File("C:\\Users\\elmer\\OneDrive\\Documents\\NetBeansProjects\\ProyectoFinal\\src\\org\\gustavosanchez\\grafix\\Carrito.dot");
        String ruta = file.getAbsolutePath();
        File archivo = new File(ruta);
        String bloqueUno = "";
        String bloqueDos = "";
        String bloqueTres = "";
        String bloqueCuatro = "";
        int contadores = 0;
        int p = 0;
        try {
            FileWriter w = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(w);

            for (int i = 0; i < lista.size(); i++) {

                String parametro = "Producto :"
                        + lista.getNodo(i).getElemento().getProducto().getNombre() + " ";

                bloqueUno += "p" + (i) + "[label=\"{<anterior>|<data>" + parametro + "|<next>}\"];\n";
                contadores++;
                if (contadores < lista.size()) {

                    bloqueDos += "p" + i + ":next:c ->" + "p" + contadores + ":anterior;\n";

                }
            }
            for (int i = 0; i < lista.size(); i++) {
                p++;
                if (p < lista.size()) {
                    bloqueDos += "p" + (i + 1) + ":anterior:c ->" + "p" + (p - 1) + ":next;\n";
                }
                if (i == lista.size() - 1) {
                    bloqueTres += "p" + (i) + ":next:c ->" + "p" + (0) + ":anterior [constraint=false];\n";
                    bloqueCuatro += "p" + (0) + ":anterior:c ->" + "p" + (i) + ":next [constraint=false];\n";
                }
            }

            String parametros = "digraph D {"
                    + "\r\n"
                    + "node[shape=record];"
                    + "\r\n"
                    + "graph[pencolor=transparent];"
                    + "\r\n"
                    + "rankdir=LR;"
                    + "\r\n"
                    + bloqueUno
                    + "\r\n"
                    + "edge[tailclip=false,arrowtail=dot,dir=both];"
                    + "\r\n"
                    + bloqueDos
                    + "\r\n"
                    + bloqueTres
                    + "\r\n"
                    + bloqueCuatro
                    + "\r\n"
                    + "}";
            bw.write(parametros);
            bw.close();
            w.close();
            generarCMD();
        } catch (IOException e) {
        }

    }

    public void generarCMD() {
        System.out.println("Esta en el metod escrib para CMD");
        File archivo = new File("src\\org\\gustavosanchez\\grafix\\comandoChtml.cmd");
        File imagen = new File("src\\org\\gustavosanchez\\grafix\\Carrito.png");
        String bloqueUno = "";
        String bloqueDos = "";
        try {
            FileWriter w = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(w);
            bloqueUno = "cd C:\\Users\\elmer\\OneDrive\\Documents\\NetBeansProjects\\ProyectoFinal\\src\\org\\gustavosanchez\\grafix";
            bloqueDos = "dot -Tpng \"C:\\Users\\elmer\\OneDrive\\Documents\\NetBeansProjects\\ProyectoFinal\\src\\org\\gustavosanchez\\grafix\\Carrito.dot\" -o \"C:\\Users\\elmer\\OneDrive\\Documents\\NetBeansProjects\\ProyectoFinal\\src\\org\\gustavosanchez\\grafix\\Carrito.png\"";
            // bloqueDos = "dot -Tpng \"\\src\\org\\gustavosanchez\\grafix\\Carrito.dot\" -o \"\\src\\org\\gustavosanchez\\grafix\\Carrito.png\"";

            String parametros = bloqueUno
                    + "\r\n"
                    + bloqueDos;
            bw.write(parametros);
            bw.close();
            Desktop.getDesktop().open(archivo);
            Desktop.getDesktop().open(imagen);
        } catch (IOException e) {
        }

    }

    public void pdfGenerar(Factura factura, LCircular<ProductoOferta> lista, File file) {
        System.out.println("Esta en el metod escrib factura");
        //String ruta = "src\\org\\gustavosanchez\\grafix\\FacturaDe" + factura.getNombre() + ".pdf";
        String ruta = file.getAbsolutePath();
        String tarjeta = "";
        int mitad = (factura.getUsuario().getTarjeta().length()) / 2;
        String mi = factura.getUsuario().getTarjeta().substring(0, mitad);
        tarjeta = mi + "- XXXXXX";

        String bloqueUno = "";
        String bloqueDos = "";
        String bloqueTres = "";

        bloqueUno = "---------------------------->FACTURA<---------------------------";

        bloqueDos = "\r\n"
                + "Fecha:" + factura.getFecha()
                + "\r\n"
                + "Nombre: " + factura.getNombre()
                + "\r\n"
                + "Nit:" + factura.getNit()
                + "\r\n"
                + "Dirección: " + factura.getDireccion()
                + "\r\n"
                + "Tarjeta:" + tarjeta
                + "\r\n"
                + "Listado Comprado:"
                + "\r\n"
                + "No.   Producto           Precio";
        for (int i = 0; i < lista.size(); i++) {
            ProductoOferta pro = lista.getNodo(i).getElemento();
            bloqueTres += (i + 1) + "       " + pro.getProducto().getNombre() + "             Q." + pro.getPrecioOferta() + "\n";
        }

        String parametros = bloqueUno
                + "\r\n"
                + bloqueDos
                + "\r\n"
                + bloqueTres
                + "\r\n"
                + "SubTotal:                   Q." + factura.getSubTotal()
                + "\r\n"
                + "Descuento:                  Q." + factura.getDescuento()
                + "\r\n"
                + "----------------------------------------------------"
                + "\r\n"
                + "Total:                      Q." + factura.getTotal();
        System.out.println(parametros);
        try {
            FileOutputStream archivo = new FileOutputStream(ruta);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            doc.add(new Paragraph(parametros));
            doc.close();
            File path = new File(ruta);
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void pdfGenerarAdmin(Factura factura) {

        LCircular<ProductoOferta> lista = factura.getLista();
        System.out.println("Esta en el metod escrib factura");
        String ruta = "src\\org\\gustavosanchez\\grafix\\FacturaAdminDe" + factura.getNombre() + ".pdf";
        String tarjeta = "";
        int mitad = (factura.getUsuario().getTarjeta().length()) / 2;
        String mi = factura.getUsuario().getTarjeta().substring(0, mitad);
        tarjeta = mi + "- XXXXXX";

        String bloqueUno = "";
        String bloqueDos = "";
        String bloqueTres = "";

        bloqueUno = "---------------------------->FACTURA<---------------------------";

        bloqueDos = "\r\n"
                + "Fecha:" + factura.getFecha()
                + "\r\n"
                + "Nombre: " + factura.getNombre()
                + "\r\n"
                + "Nit:" + factura.getNit()
                + "\r\n"
                + "Dirección: " + factura.getDireccion()
                + "\r\n"
                + "Tarjeta:" + tarjeta
                + "\r\n"
                + "Listado Comprado:"
                + "\r\n"
                + "No.   Producto           Precio";
        for (int i = 0; i < lista.size(); i++) {
            ProductoOferta pro = lista.getNodo(i).getElemento();
            bloqueTres += (i + 1) + "       " + pro.getProducto().getNombre() + "             Q." + pro.getPrecioOferta() + "\n";
        }

        String parametros = bloqueUno
                + "\r\n"
                + bloqueDos
                + "\r\n"
                + bloqueTres
                + "\r\n"
                + "SubTotal:                   Q." + factura.getSubTotal()
                + "\r\n"
                + "Descuento:                  Q." + factura.getDescuento()
                + "\r\n"
                + "----------------------------------------------------"
                + "\r\n"
                + "Total:                      Q." + factura.getTotal();
        System.out.println(parametros);
        try {
            FileOutputStream archivo = new FileOutputStream(ruta);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            doc.add(new Paragraph(parametros));
            doc.close();
            File path = new File(ruta);
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public boolean pdfGenerarCancelado(LCircular<Cancelado> lista,File fil) {

        System.out.println("Esta en el metodo escriber cancelado");
        //String ruta = "src\\org\\gustavosanchez\\grafix\\ReporteCancelado.pdf";
        String ruta = fil.getAbsolutePath();
        String parametros = "";
        String bloqueUno = "";
        String bloqueDos = "";
        bloqueUno = "------------------------------>Cancelado<------------------------------"
                + "\r\n"
                + "Nombre:          Apellido         Usuario        No.Veces"
                + "\r\n";
        for (int i = 0; i < lista.size(); i++) {
            Cancelado can = lista.getNodo(i).getElemento();
            bloqueDos += can.getUsuario().getNombre() + "        " + can.getUsuario().getApellido() + "         " + can.getUsuario().getNick() + "              " + can.getVeces() + "\n";
        }

        parametros = bloqueUno
                + "\r\n"
                + bloqueDos;
        try {
            FileOutputStream archivo = new FileOutputStream(ruta);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            doc.add(new Paragraph(parametros));
            doc.close();
            File path = new File(ruta);
            Desktop.getDesktop().open(path);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public void reporteHTML(Carrito car, File fil) {
        try {

            File file = new File(fil.getPath());
            //File file = new File(fil.getCanonicalPath());
            LCircular<ProductoOferta> lista = car.getListaProducto();
            String comando = "<!DOCTYPE html>\n"
                    + "\n"
                    + "<html>\n"
                    + "\n"
                    + "<head>\n"
                    + "\n"
                    + "<title>Reporte</title>\n"
                    + "\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "<h1>Reporte de productos comprados por cada usuario</h1>\n"
                    + "<table class=\"egt\">\n"
                    + "\n"
                    + "    <tr>\n"
                    + "\n"
                    + "        <th style=\"background:  #C0392B; border: 2px solid #2e7ab8;\" > Id Usuario </th>\n"
                    + "\n"
                    + "        <th style=\"background:  #C0392B; border: 2px solid #2e7ab8;\" > Usuario</th>\n"
                    + "\n"
                    + "        <th style=\"background: #C0392B; border: 2px solid #2e7ab8;\"> Producto </th>\n"
                    + "\n"
                    + "        <th style=\"background: #C0392B; border: 2px solid #2e7ab8;\"> Precio </th>\n"
                    + "\n"
                    + "    </tr>\n"
                    + "\n";

            for (int i = 0; i < lista.size(); i++) {
                ProductoOferta pro = lista.getNodo(i).getElemento();
                comando
                        += "<tr>\n"
                        + "\n" + "<td style=\"background:  #ebeae8;\"> " + car.getUsuario().getId() + " </td>\n"
                        + "\n"
                        + "        <td style=\"background:  #ebeae8;\"> " + car.getUsuario().getNick() + "</td>\n"
                        + "\n"
                        + "        <td style=\"background:  #ebeae8;\"> " + pro.getProducto().getNombre() + "</td>\n"
                        + "\n"
                        + "        <td style=\"background:  #ebeae8;\"> " + pro.getPrecioOferta() + "</td>\n"
                        + "\n"
                        + " </tr>\n";
            }

            comando
                    += "\n"
                    + "\n"
                    + "</table>\n"
                    + "\n"
                    + "</body>\n"
                    + "\n"
                    + "</html>";

            try {
                FileWriter w = new FileWriter(fil);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write(comando);
                wr.close();
                w.flush();
                bw.close();
                Desktop.getDesktop().open(fil);
            } catch (IOException e) {
            }
        } catch (Exception e) {
        }
    }

    public void abrir(Carrito car) {
        Stage stage = new Stage();
        Writer writer = null;
        File fil = null;
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (.html)", ".html");
            fileChooser.getExtensionFilters().addAll(extFilter);
            fileChooser.setInitialFileName("reporte");
            fil = fileChooser.showSaveDialog(stage);

            try {
                writer = new BufferedWriter(new FileWriter(fil));
                String text = "";
                writer.write(text);
                reporteHTML(car, fil);
            } catch (NullPointerException e) {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {

            }
        }

    }

    public void abrirPdfGenerar(Factura factura, LCircular<ProductoOferta> lista) {
        Stage stage = new Stage();
        Writer writer = null;
        File fil = null;
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (.pdf)", ".pdf");
            fileChooser.getExtensionFilters().addAll(extFilter);
            fileChooser.setInitialFileName("FacturaDe" + factura.getNombre());
            fil = fileChooser.showSaveDialog(stage);
            //File file = new File("C:\\Users\\Cris\\Documents\\NetBeansProjects\\FXColegio2\\Usuario.csv.");
            try {
                writer = new BufferedWriter(new FileWriter(fil));
                String text = "";
                writer.write(text);
                pdfGenerar(factura, lista, fil);

            } catch (NullPointerException e) {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {

            }
        }

    }

    public void abrirDOTGenerar(LCircular<ProductoOferta> lista) {
        Stage stage = new Stage();
        Writer writer = null;
        File fil = null;
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DOT files (.dot)", ".dot");
            fileChooser.getExtensionFilters().addAll(extFilter);
            fileChooser.setInitialFileName("Carrito");
            fil = fileChooser.showSaveDialog(stage);
            //File file = new File("C:\\Users\\Cris\\Documents\\NetBeansProjects\\FXColegio2\\Usuario.csv.");
            try {
                writer = new BufferedWriter(new FileWriter(fil));
                String text = "";
                writer.write(text);
                
                escribirArchivo(lista, fil);

            } catch (NullPointerException e) {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {

            }
        }

    }

    
    public void abrirPDFCanceladoGenerar(LCircular<Cancelado> lista) {
        Stage stage = new Stage();
        Writer writer = null;
        File fil = null;
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (.pdf)", ".pdf");
            fileChooser.getExtensionFilters().addAll(extFilter);
            fileChooser.setInitialFileName("ReporteCancelado");
            fil = fileChooser.showSaveDialog(stage);
            //File file = new File("C:\\Users\\Cris\\Documents\\NetBeansProjects\\FXColegio2\\Usuario.csv.");
            try {
                writer = new BufferedWriter(new FileWriter(fil));
                String text = "";
                writer.write(text);
                
                pdfGenerarCancelado(lista, fil);
                   
                
                

            } catch (NullPointerException e) {
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {

            }
        }
        
    }
    
}
