package com.example.modbustest0309.jlibmodbus.msg.response;

import com.example.modbustest0309.jlibmodbus.Modbus;
import com.example.modbustest0309.jlibmodbus.data.ModbusCoils;
import com.example.modbustest0309.jlibmodbus.exception.ModbusNumberException;
import com.example.modbustest0309.jlibmodbus.msg.base.AbstractReadResponse;
import com.example.modbustest0309.jlibmodbus.net.stream.base.ModbusInputStream;
import com.example.modbustest0309.jlibmodbus.net.stream.base.ModbusOutputStream;
import com.example.modbustest0309.jlibmodbus.utils.DataUtils;
import com.example.modbustest0309.jlibmodbus.utils.ModbusFunctionCode;

import java.io.IOException;
import java.util.Arrays;

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
public class ReadCoilsResponse extends AbstractReadResponse {

    private byte[] buffer = new byte[0];

    public ReadCoilsResponse() {
        super();
    }

    static public int calcByteCount(boolean[] coils) {
        return calcByteCount(coils.length);
    }

    static public int calcByteCount(int coilCount) {
        return (int) Math.ceil((double) coilCount / 8);
    }

    /**
     * returns a copy of the raw byte-buffer
     *
     * @return registers bytes
     */
    final public byte[] getBytes() {
        return Arrays.copyOf(buffer, buffer.length);
    }

    @Deprecated
    final public boolean[] getCoils() {
        return DataUtils.toBitsArray(buffer, buffer.length * 8);
    }
    
    final public ModbusCoils getModbusCoils() {
        return new ModbusCoils(buffer);
    }

    final public void setCoils(boolean[] coils) throws ModbusNumberException {
        this.buffer = DataUtils.toByteArray(coils);
        setByteCount(calcByteCount(coils));
    }

    @Override
    final protected void readData(ModbusInputStream fifo) throws IOException {
        buffer = new byte[getByteCount()];
        int size;
        if ((size = fifo.read(buffer)) < buffer.length)
            Modbus.log().warning(buffer.length + " bytes expected, but " + size + " received.");
    }

    @Override
    final protected void writeData(ModbusOutputStream fifo) throws IOException {
        fifo.write(buffer);
    }

    @Override
    public int getFunction() {
        return ModbusFunctionCode.READ_COILS.toInt();
    }
}
