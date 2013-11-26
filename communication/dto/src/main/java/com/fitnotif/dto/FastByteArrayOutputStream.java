package com.fitnotif.dto;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * OutputStream rápido para ser usado con el Clonador. No para uso general.
 *
 * @author FitBank CI
 */
public class FastByteArrayOutputStream extends OutputStream {

    protected byte[] buf = null;

    protected int size = 0;

    public FastByteArrayOutputStream() {
        this(0);
    }

    public FastByteArrayOutputStream(int initSize) {
        this.size = 0;
        this.buf = new byte[initSize];
    }

    private void verifyBufferSize(int sz) {
        if (sz > buf.length) {
            byte[] old = buf;
            buf = new byte[Math.max(sz, 2 * buf.length)];
            System.arraycopy(old, 0, buf, 0, old.length);
            old = null;
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public final void write(byte b[]) {
        verifyBufferSize(size + b.length);
        System.arraycopy(b, 0, buf, size, b.length);
        size += b.length;
    }

    @Override
    public final void write(byte b[], int off, int len) {
        verifyBufferSize(size + len);
        System.arraycopy(b, off, buf, size, len);
        size += len;
    }

    public final void write(int b) {
        verifyBufferSize(size + 1);
        buf[size++] = (byte) b;
    }

    public void reset() {
        size = 0;
    }

    public InputStream getInputStream() {
        return new FastByteArrayInputStream(buf, size);
    }

}
