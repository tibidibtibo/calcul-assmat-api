package fr.deboissieu.calculassmat.commons.mocks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.Resource;

public class ResourceMock implements Resource {

	@Override
	public InputStream getInputStream() throws IOException {

		return null;
	}

	@Override
	public boolean exists() {

		return false;
	}

	@Override
	public URL getURL() throws IOException {

		return null;
	}

	@Override
	public URI getURI() throws IOException {

		return null;
	}

	@Override
	public File getFile() throws IOException {

		return null;
	}

	@Override
	public long contentLength() throws IOException {

		return 0;
	}

	@Override
	public long lastModified() throws IOException {

		return 0;
	}

	@Override
	public Resource createRelative(String relativePath) throws IOException {

		return null;
	}

	@Override
	public String getFilename() {

		return null;
	}

	@Override
	public String getDescription() {

		return null;
	}

}
