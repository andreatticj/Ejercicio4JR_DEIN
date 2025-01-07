package eu.andreatt.ejercicio4jr_dein.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import eu.andreatt.ejercicio4jr_dein.application.Ejercicio4;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controlador para la aplicación Informe Médico.
 * Gestiona los eventos y lógica de los botones y campos de texto del formulario.
 */
public class Ejercicio4Controller {

	@FXML
	private Button botonGenerarinforme;

	@FXML
	private Button botonLimpiar;

	@FXML
	private Button botonSalir;

	@FXML
	private TextField textoCodigoMedico;

	@FXML
	private TextField textoDireccionPaciente;

	@FXML
	private TextField textoEspecialidadMedico;

	@FXML
	private TextField textoNombreMedico;

	@FXML
	private TextField textoNombrePaciente;

	@FXML
	private TextField textoNumeroPaciente;

	@FXML
	private TextArea textoTratamiento;

	/**
	 * Acción que se ejecuta al pulsar el botón "Generar Informe".
	 * Válida los datos del formulario, genera un informe y limpia los campos si no hay errores.
	 *
	 * @param event el evento de acción generado por el botón
	 */
	@FXML
	void actionBotonGenerarInforme(ActionEvent event) {
		// Validar campos
		String errores = validarDatos();

		// Mostrar errores si los hay
		if (!errores.isEmpty()) {
			Alert alerta = generarVentana(AlertType.ERROR, errores, "ERROR");
			alerta.show();
		} else {
			// Generar informe
			try {
				InputStream jasper = Ejercicio4.class.getResourceAsStream("/eu/andreatt/ejercicio4jr_dein/reportes/ejercicio4.jasper");
				try {
					JasperReport report = (JasperReport) JRLoader.loadObject(jasper);
					JasperPrint jprint = JasperFillManager.fillReport(report, crearParametros(), new JREmptyDataSource());
					JasperViewer viewer = new JasperViewer(jprint, false);
					viewer.setVisible(true);
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("ERROR");
					alert.setContentText("Ha ocurrido un error");
					alert.showAndWait();
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Limpiar campos
			limpiarCampos();
		}
	}

	/**
	 * Acción que se ejecuta al pulsar el botón "Limpiar".
	 * Limpia todos los campos del formulario.
	 *
	 * @param event el evento de acción generado por el botón
	 */
	@FXML
	void actionBotonLimpiar(ActionEvent event) {
		limpiarCampos();
	}

	/**
	 * Acción que se ejecuta al pulsar el botón "Salir".
	 * Cierra la ventana actual.
	 *
	 * @param event el evento de acción generado por el botón
	 */
	@FXML
	void actionBotonSalir(ActionEvent event) {
		// Cerrar ventana
		Stage stage = (Stage) botonSalir.getScene().getWindow();
		stage.close();
	}

	/**
	 * Crea un mapa con los parámetros necesarios para generar el informe.
	 *
	 * @return un mapa con los parámetros del informe
	 */
	private Map<String, Object> crearParametros() {
		Map<String, Object> parameters = new HashMap<>(7);

		parameters.put("NOM_MEDICO", textoNombreMedico.getText().trim());
		parameters.put("TRATAMIENTO", textoTratamiento.getText().trim());
		parameters.put("COD_MEDICO", Integer.parseInt(textoCodigoMedico.getText()));
		parameters.put("ESP_MEDICO", textoEspecialidadMedico.getText().trim());
		parameters.put("NUM_PACIENTE", Integer.parseInt(textoNumeroPaciente.getText()));
		parameters.put("NOM_PACIENTE", textoNombrePaciente.getText().trim());
		parameters.put("DIR_PACIENTE", textoDireccionPaciente.getText().trim());
		String imageBasePath = getClass().getResource("/eu/andreatt/ejercicio4jr_dein/images/").toString();
		parameters.put("REPORT_IMAGE", imageBasePath);

		return parameters;
	}

	/**
	 * Limpia todos los campos del formulario.
	 */
	private void limpiarCampos() {
		textoCodigoMedico.setText("");
		textoDireccionPaciente.setText("");
		textoEspecialidadMedico.setText("");
		textoNombreMedico.setText("");
		textoNombrePaciente.setText("");
		textoNumeroPaciente.setText("");
		textoTratamiento.setText("");
	}

	/**
	 * Válida los datos ingresados en los campos del formulario.
	 *
	 * @return una cadena con los mensajes de error si hay errores; de lo contrario, una cadena vacía
	 */
	private String validarDatos() {
		String errores = "";

		// Validar Número Paciente
		if (textoNumeroPaciente.getText().isEmpty()) {
			errores += "Se debe escribir el NÚMERO DE PACIENTE\n";
		} else {
			if (!esNumeroEntero(textoNumeroPaciente.getText())) {
				errores += "El NÚMERO DE PACIENTE debe ser un número\n";
			}
		}

		// Validar Nombre Paciente
		if (textoNombrePaciente.getText().isEmpty()) {
			errores += "Se debe escribir el NOMBRE DEL PACIENTE\n";
		}

		// Validar Dirección Paciente
		if (textoDireccionPaciente.getText().isEmpty()) {
			errores += "Se debe escribir la DIRECCIÓN DEL PACIENTE\n";
		}

		// Validar Código Médico
		if (textoCodigoMedico.getText().isEmpty()) {
			errores += "Se debe escribir el CÓDIGO DEL MÉDICO\n";
		} else {
			if (!esNumeroEntero(textoCodigoMedico.getText())) {
				errores += "El CÓDIGO DEL MÉDICO debe ser un número\n";
			}
		}

		// Validar Nombre Médico
		if (textoNombreMedico.getText().isEmpty()) {
			errores += "Se debe escribir el NOMBRE DEL MÉDICO\n";
		}

		// Validar Especialidad Médico
		if (textoEspecialidadMedico.getText().isEmpty()) {
			errores += "Se debe escribir la ESPECIALIDAD DEL MÉDICO\n";
		}

		// Validar Tratamiento
		if (textoTratamiento.getText().isEmpty()) {
			errores += "Se debe escribir el TRATAMIENTO";
		}

		return errores;
	}

	/**
	 * Verifica si una cadena es un número entero válido.
	 *
	 * @param valor la cadena a verificar
	 * @return true si la cadena es un número entero; false en caso contrario
	 */
	private static boolean esNumeroEntero(String valor) {
		return valor.matches("-?\\d+");
	}

	/**
	 * Genera una ventana de alerta con un tipo, mensaje y título especificados.
	 *
	 * @param tipoDeAlerta el tipo de alerta (INFO, WARNING, ERROR, etc.)
	 * @param mensaje el mensaje que se mostrará en la alerta
	 * @param title el título de la ventana de alerta
	 * @return la instancia de la alerta generada
	 */
	private Alert generarVentana(AlertType tipoDeAlerta, String mensaje, String title) {
		Alert alerta = new Alert(tipoDeAlerta);
		alerta.setContentText(mensaje);
		alerta.setHeaderText(null);
		alerta.setTitle(title);
		return alerta;
	}
}
