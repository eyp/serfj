package net.sf.serfj.serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default base64 serializer/deserializer.
 * 
 * @author eduardo.yanez
 */
public class Base64Serializer implements Serializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Base64Serializer.class);

	/**
	 * Serialize object to an encoded base64 string.
	 */
	public String serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			return new String(Base64.encodeBase64(bos.toByteArray()));
		} catch (IOException e) {
			LOGGER.error("Can't serialize data on Base 64", e);
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			LOGGER.error("Can't serialize data on Base 64", e);
			throw new IllegalArgumentException(e);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				LOGGER.error("Can't close ObjetInputStream used for serialize data on Base 64", e);
			}
		}
	}

	/**
	 * Deserialze base 64 encoded string data to Object.
	 */
	public Object deserialize(String data) {
		if ((data == null) || (data.length() == 0)) {
			return null;
		}
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(Base64.decodeBase64(data.getBytes()));
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			LOGGER.error("Can't deserialize data from Base64", e);
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			LOGGER.error("Can't deserialize data from Base64", e);
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			LOGGER.error("Can't deserialize data from Base64", e);
			throw new IllegalArgumentException(e);
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {
				LOGGER.error("Can't close ObjetInputStream used for deserialize data from Base64", e);
			}
		}
	}

	/**
	 * @see net.sf.serfj.serializers.Serializer#getContentType()
	 */
	public String getContentType() {
		return "application/octect-stream";
	}

	/**
	 * @see net.sf.serfj.serializers.Serializer#getExtension()
	 */
	public String getExtension() {
		return "64";
	}

	/**
	 * Returns 'base64' as content-transfer-encoding.
	 */
	public String getContentTransferEncoding() {
		return "base64";
	}
}
