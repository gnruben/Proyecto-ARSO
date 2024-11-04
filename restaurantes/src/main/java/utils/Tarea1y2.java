package utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Tarea1y2 {

	private final static String raizUrlgeonames = "http://api.geonames.org/findNearbyWikipedia?";
	private final static String raizUrldbpedia = "https://es.dbpedia.org/";

	private final static String propiedadNombre = "http://xmlns.com/foaf/0.1/name";
	private final static String propiedadResumen = "http://dbpedia.org/ontology/abstract";
	private final static String propiedadEnlaces = "http://dbpedia.org/ontology/wikiPageExternalLink";
	private final static String propiedadImagenes = "http://es.dbpedia.org/property/imagen";
	private final static String propiedadCategorias = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

	///////////////////////////////////////////// TAREA 1
	///////////////////////////////////////////// /////////////////////////////////////////////

	public static ArrayList<String> obtenerSitios(String url)
			throws SAXException, IOException, ParserConfigurationException {

		ArrayList<String> sitios = new ArrayList<String>();

		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		// 2. Pedir a la factoría la construcción del analizador
		DocumentBuilder analizador = factoria.newDocumentBuilder();
		// 3. Analizar el documento
		Document documento = analizador.parse(url);

		NodeList nList = documento.getElementsByTagName("wikipediaUrl");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Element eElement = (Element) nList.item(temp);
			sitios.add(eElement.getTextContent());
		}

		return sitios;
	}

	public static ArrayList<String> getSitiosPorCoordenadas(String lat, String lng)
			throws ParserConfigurationException, SAXException, IOException {

		ArrayList<String> sitios = new ArrayList<String>();
		String url = raizUrlgeonames + "lat=" + lat + "&lng=" + lng + "&username=ruben.garcia3";

		sitios = obtenerSitios(url);

		return sitios;
	}

	public static ArrayList<String> getSitiosPorCodigoPostal(String code)
			throws ParserConfigurationException, SAXException, IOException {

		ArrayList<String> sitios = new ArrayList<String>();
		String url = raizUrlgeonames + "postalcode=" + code + "&country=ES&radius=20&lang=es&username=ruben.garcia3";

		sitios = obtenerSitios(url);

		return sitios;
	}

	///////////////////////////////////////////// TAREA 2
	///////////////////////////////////////////// /////////////////////////////////////////////

	public static JsonObject procesarSitio(String link) throws UnsupportedEncodingException, IOException {

		int indice = link.lastIndexOf("/");
		String sitio = link.substring(indice);

		String urlString = raizUrldbpedia + "data" + sitio + ".json";
		URL url = new URL(urlString);

		InputStreamReader fuente = new InputStreamReader(url.openStream(), "UTF-8");
		JsonReader jsonReader = Json.createReader(fuente);
		JsonObject obj = jsonReader.readObject();

		String decoded = URLDecoder.decode(sitio, StandardCharsets.UTF_8.toString());

		JsonObject propiedades = obj.getJsonObject("http://es.dbpedia.org/resource" + decoded);

		return propiedades;
	}

	public static String getNombre(String linkSitio) throws UnsupportedEncodingException, IOException {

		JsonObject propiedades = procesarSitio(linkSitio);
		JsonArray nombre = propiedades.getJsonArray(propiedadNombre);

		String nombreString = "";

		if (nombre == null)
			return null;
		for (JsonObject valor : nombre.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				nombreString = valor.getString("value");
		}

		return nombreString;
	}

	public static String getResumen(String linkSitio) throws UnsupportedEncodingException, IOException {

		JsonObject propiedades = procesarSitio(linkSitio);
		JsonArray resumen = propiedades.getJsonArray(propiedadResumen);

		String resumenString = "";

		if (resumen == null)
			return null;
		for (JsonObject valor : resumen.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				resumenString = valor.getString("value");
		}

		return resumenString;
	}

	public static ArrayList<String> getCategorias(String linkSitio) throws UnsupportedEncodingException, IOException {

		ArrayList<String> categorias = new ArrayList<String>();

		JsonObject propiedades = procesarSitio(linkSitio);
		JsonArray cat = propiedades.getJsonArray(propiedadCategorias);

		if (cat == null)
			return null;
		for (JsonObject valor : cat.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				categorias.add(valor.getString("value"));
		}

		return categorias;
	}

	public static ArrayList<String> getEnlaces(String linkSitio) throws UnsupportedEncodingException, IOException {

		ArrayList<String> enlaces = new ArrayList<String>();

		JsonObject propiedades = procesarSitio(linkSitio);
		JsonArray links = propiedades.getJsonArray(propiedadEnlaces);

		if (links == null)
			return null;
		for (JsonObject valor : links.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				enlaces.add(valor.getString("value"));
		}

		return enlaces;
	}

	public static String getImagenes(String linkSitio) throws UnsupportedEncodingException, IOException {

		JsonObject propiedades = procesarSitio(linkSitio);
		JsonArray img = propiedades.getJsonArray(propiedadImagenes);

		if (img == null)
			return null;
		for (JsonObject valor : img.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				return valor.getString("value");
		}

		return null;

	}

}
