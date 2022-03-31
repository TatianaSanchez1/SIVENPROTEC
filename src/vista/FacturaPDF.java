/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dominio.controlador.ControladorCliente;
import dominio.controlador.ControladorProducto;
import dominio.controlador.ControladorVenta;
import dominio.modelo.Cliente;
import dominio.modelo.DatosEmpresa;
import dominio.modelo.DetalleVenta;
import dominio.modelo.Producto;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FacturaPDF implements StrategyFactura {

	public void generarFactura() {
		ControladorVenta controladorVenta = new ControladorVenta();

		try {
			int id = controladorVenta.idVenta();
			Cliente datosCliente = new ControladorCliente().buscarClientePorNombre(
				new ControladorVenta().buscarVenta(id).getCliente()
			);

			DatosEmpresa datosEmpresa = new ControladorProducto().buscarDatos();

			FileOutputStream archivo;
			File file = new File("pdf/venta" + id + ".pdf");

			archivo = new FileOutputStream(file);

			Document documento = new Document();
			PdfWriter.getInstance(documento, archivo);
			documento.open();

			//Encabezado del pdf//
			Image imagen = Image.getInstance("src/vista/img/logo-pdf.jpg");
			Paragraph fecha = new Paragraph();
			Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
			fecha.add(Chunk.NEWLINE);
			Date fechaSistema = new Date();
			fecha.add("Factura: " + id + "\nFecha: " + new SimpleDateFormat("dd-MM-yyyy").format(fechaSistema) + "\n\n");

			PdfPTable encabezado = new PdfPTable(4);
			encabezado.setWidthPercentage(100);
			encabezado.getDefaultCell().setBorder(0);

			float[] columnaEncabezado = new float[]{20f, 40f, 70f, 40f};
			encabezado.setWidths(columnaEncabezado);
			encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
			encabezado.addCell(imagen);

			String NITEmpresa = datosEmpresa.getNit();
			String nombreEmpresa = datosEmpresa.getNombre();
			String telefonoEmpresa = datosEmpresa.getTelefono();
			String direccionEmpresa = datosEmpresa.getDireccion();

			encabezado.addCell("");
			encabezado.addCell("NIT: " + NITEmpresa + "\nNombre: "
				+ nombreEmpresa + "\nTeléfono: " + telefonoEmpresa + "\nDirección: "
				+ direccionEmpresa);
			encabezado.addCell(fecha);

			documento.add(encabezado);

			//Informacion cliente
			Paragraph cliente = new Paragraph();
			cliente.add(Chunk.NEWLINE);
			cliente.add("Datos de los clientes" + "\n\n");
			documento.add(cliente);

			PdfPTable tablaCliente = new PdfPTable(5);
			tablaCliente.setWidthPercentage(100);
			tablaCliente.getDefaultCell().setBorder(0);

			float[] columnaCliente = new float[]{30f, 50f, 40f, 40f, 50f};
			tablaCliente.setWidths(columnaCliente);
			tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell cliente1 = new PdfPCell(new Phrase("Documento", negrita));
			PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre", negrita));
			PdfPCell cliente3 = new PdfPCell(new Phrase("Teléfono", negrita));
			PdfPCell cliente4 = new PdfPCell(new Phrase("Dirección", negrita));
			PdfPCell cliente5 = new PdfPCell(new Phrase("Correo", negrita));

			cliente1.setBorder(0);
			cliente2.setBorder(0);
			cliente3.setBorder(0);
			cliente4.setBorder(0);
			cliente5.setBorder(0);

			tablaCliente.addCell(cliente1);
			tablaCliente.addCell(cliente2);
			tablaCliente.addCell(cliente3);
			tablaCliente.addCell(cliente4);
			tablaCliente.addCell(cliente5);

			tablaCliente.addCell(datosCliente.getDocumentoCliente());
			tablaCliente.addCell(datosCliente.getNombreCliente());
			tablaCliente.addCell(datosCliente.getNumTelCliente());
			tablaCliente.addCell(datosCliente.getDireccionCliente());
			tablaCliente.addCell(datosCliente.getCorreoCliente());

			documento.add(tablaCliente);

			documento.add(Chunk.NEWLINE);

			//Informacion productos
			PdfPTable tablaProductos = new PdfPTable(5);
			tablaProductos.setWidthPercentage(100);
			tablaProductos.getDefaultCell().setBorder(0);

			float[] columnaProductos = new float[]{10f, 50f, 10f, 15f, 20f};
			tablaCliente.setWidths(columnaProductos);
			tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaCliente.addCell(imagen);

			PdfPCell producto1 = new PdfPCell(new Phrase("Código", negrita));
			PdfPCell producto2 = new PdfPCell(new Phrase("Nombre", negrita));
			PdfPCell producto3 = new PdfPCell(new Phrase("Cantidad", negrita));
			PdfPCell producto4 = new PdfPCell(new Phrase("Precio", negrita));
			PdfPCell producto5 = new PdfPCell(new Phrase("Precio Total", negrita));

			producto1.setBorder(0);
			producto2.setBorder(0);
			producto3.setBorder(0);
			producto4.setBorder(0);
			producto5.setBorder(0);

			producto1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			producto2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			producto3.setBackgroundColor(BaseColor.LIGHT_GRAY);
			producto4.setBackgroundColor(BaseColor.LIGHT_GRAY);
			producto5.setBackgroundColor(BaseColor.LIGHT_GRAY);

			tablaProductos.addCell(producto1);
			tablaProductos.addCell(producto2);
			tablaProductos.addCell(producto3);
			tablaProductos.addCell(producto4);
			tablaProductos.addCell(producto5);

			List<DetalleVenta> listaDetalles = controladorVenta.listarDetalleVentas(id);
			ControladorProducto controladorProducto = new ControladorProducto();
			listaDetalles.forEach(detalle -> {
				Producto producto = controladorProducto.buscarProducto(detalle.getCodigoProducto());
				tablaProductos.addCell(detalle.getCodigoProducto());
				tablaProductos.addCell(producto.getNombreProducto());
				tablaProductos.addCell(String.valueOf(detalle.getCantidad()));
				tablaProductos.addCell(String.valueOf(producto.getPrecioVenta()));
				tablaProductos.addCell(String.valueOf(detalle.getPrecio()));
			});

			documento.add(tablaProductos);

			Paragraph informacion = new Paragraph();
			informacion.add(Chunk.NEWLINE);
			informacion.add("Total a Pagar: " + String.format("%.2f", totalPagar(id)));
			informacion.setAlignment(Element.ALIGN_RIGHT);
			documento.add(informacion);

			Paragraph mensaje = new Paragraph();
			mensaje.add(Chunk.NEWLINE);
			mensaje.add("GRACIAS POR SU COMPRA");
			mensaje.setAlignment(Element.ALIGN_CENTER);
			documento.add(mensaje);

			documento.close();
			archivo.close();
			Desktop.getDesktop().open(file);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private float totalPagar(int id){
		ControladorVenta controladorVenta = new ControladorVenta();

		float totalPagar = 0.0f;

		List<DetalleVenta> listaDetalles = controladorVenta.listarDetalleVentas(id);

		for (DetalleVenta detalleVenta : listaDetalles) {
			totalPagar += (detalleVenta.getPrecio());
		}
		return totalPagar;
	}
}
