package com.example.modbustest0309.jlibmodbus.net.stream.base;

//com.example.modbustest0309.jlibmodbus.Modbus
import com.example.modbustest0309.jlibmodbus.Modbus;
import com.example.modbustest0309.jlibmodbus.utils.ByteFifo;
import com.example.modbustest0309.jlibmodbus.utils.DataUtils;

import java.io.IOException;
import java.io.OutputStream;

/*
 * Copyright (C) 2016 "Invertor" Factory", JSC
 * [http://www.sbp-invertor.ru]
 *
 * This file is part of JLibModbus.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Vladislav Y. Kochedykov, software engineer.
 * email: vladislav.kochedykov@gmail.com
 */
abstract public class ModbusOutputStream extends OutputStream {

    private final ByteFifo fifo = new ByteFifo(Modbus.MAX_TCP_ADU_LENGTH);

    @Override
    public void write(byte[] b) throws IOException {
        fifo.write(b);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        fifo.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        fifo.write(b);
    }

    /**
     * it should have invoked last
     */
    @Override
    public void flush() throws IOException {
        fifo.reset();
    }

    public void writeShortBE(int s) throws IOException {
        write(DataUtils.byteHigh(s));
        write(DataUtils.byteLow(s));
    }

    public void writeShortLE(int s) throws IOException {
        write(DataUtils.byteLow(s));
        write(DataUtils.byteHigh(s));
    }

    public byte[] toByteArray() {
        return fifo.toByteArray();
    }

    public ByteFifo getFifo() {
        return fifo;
    }
}
