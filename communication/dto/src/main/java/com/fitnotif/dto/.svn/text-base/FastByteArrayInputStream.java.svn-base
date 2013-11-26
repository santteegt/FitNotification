package com.fitnotif.dto;

import java.io.InputStream;

/**
 * InputStream rápido para ser usado con el Clonador. No para uso general.
 *
 * @author FitBank CI
 */
public class FastByteArrayInputStream extends InputStream {

    protected byte[] buf = null;

    protected int count = 0;

    protected int pos = 0;

    public FastByteArrayInputStream(byte[] buf, int count) {
        this.buf = buf;
        this.count = count;
    }

    @Override
    public final int available() {
        return count - pos;
    }

    public final int read() {
        return pos < count ? buf[pos++] & 0xff : -1;
    }

    @Override
    public final int read(byte[] b, int off, int len) {
        if (pos >= count) {
            return -1;
        }

        if (pos + len > count) {
            len = count - pos;
        }

        System.arraycopy(buf, pos, b, off, len);
        pos += len;

        return len;
    }

    @Override
    public final long skip(long n) {
        if (pos + n > count) {
            n = count - pos;
        }
        if (n < 0) {
            return 0;
        }
        pos += n;

        return n;
    }

}
