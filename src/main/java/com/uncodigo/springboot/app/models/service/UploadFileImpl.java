package com.uncodigo.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileImpl implements IUploadFileService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final static String UPLOAD_FOLDER = "uploads";

	@Override
	public Resource load(String fileName) throws MalformedURLException {

		Path pathFoto = this.getPath(fileName);

		logger.info("Path Foto: " + pathFoto);

		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: No se puede cargar o leer la imagen: " + pathFoto.toString());
		}

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String nomUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		Path rootPath = this.getPath(nomUnico);

		logger.info("Ruta Absoluta: " + rootPath);
		
		Files.copy(file.getInputStream(), rootPath);
		
		return nomUnico;
	}

	@Override
	public boolean delete(String fileName) {
		Path rootPath = this.getPath(fileName);
		
		File archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String fileName) {
		return Paths.get(UPLOAD_FOLDER).resolve(fileName).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOAD_FOLDER));
	}

}
