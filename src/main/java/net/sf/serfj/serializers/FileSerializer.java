/*
 * Copyright 2012 Eduardo Yáñez Parareda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.serfj.serializers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serializer for files.<br>
 * <br>
 * FileSerializers write a file to an OutputStream.<br>
 * <br>
 * Generally this class is used to write files to the ServletOutputStream of HttpServletResponse in order to
 * send files to the client.
 * 
 * @author Eduardo Yáñez
 */
public class FileSerializer implements Serializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSerializer.class);

    /**
     * Content type that will be used in the response.
     */
    public String getContentType() {
        return "application/octet-stream";
    }

    /**
     * Write a file to an OuputStream.
     * 
     * @param file File that will be written.
     * @param os The target OutputStream.
     * @throws IOException if any error happens.
     */
    public void sendFile(File file, OutputStream os) throws IOException {
        FileInputStream is = null;
        BufferedInputStream buf = null;;
        try {
            is = new FileInputStream(file);
            buf = new BufferedInputStream(is);
            int readBytes = 0;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Writing file...");
            }
            while ((readBytes = buf.read()) != -1) {
                os.write(readBytes);
            }
            os.flush();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("File written");
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (buf != null) {
                buf.close();
            }
        }
    }
}
