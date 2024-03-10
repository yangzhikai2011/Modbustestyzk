package com.example.modbustest0309.jlibmodbus.msg.request;

import com.example.modbustest0309.jlibmodbus.data.DataHolder;
import com.example.modbustest0309.jlibmodbus.exception.ModbusNumberException;
import com.example.modbustest0309.jlibmodbus.exception.ModbusProtocolException;
import com.example.modbustest0309.jlibmodbus.msg.base.ModbusRequest;
import com.example.modbustest0309.jlibmodbus.msg.base.ModbusResponse;
import com.example.modbustest0309.jlibmodbus.msg.response.ReadExceptionStatusResponse;
import com.example.modbustest0309.jlibmodbus.net.stream.base.ModbusInputStream;
import com.example.modbustest0309.jlibmodbus.net.stream.base.ModbusOutputStream;
import com.example.modbustest0309.jlibmodbus.utils.ModbusFunctionCode;

import java.io.IOException;

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
final public class ReadExceptionStatusRequest extends ModbusRequest {

    public ReadExceptionStatusRequest() {
        super();
    }

    @Override
    protected Class getResponseClass() {
        return ReadExceptionStatusResponse.class;
    }

    @Override
    public void writeRequest(ModbusOutputStream fifo) throws IOException {
        //no operation
    }

    @Override
    public int requestSize() {
        return 0;
    }

    @Override
    public ModbusResponse process(DataHolder dataHolder) throws ModbusNumberException {
        ReadExceptionStatusResponse response = (ReadExceptionStatusResponse) getResponse();
        try {
            int exceptionStatus = dataHolder.readExceptionStatus();
            response.setExceptionStatus(exceptionStatus);
        } catch (ModbusProtocolException e) {
            response.setException();
            response.setModbusExceptionCode(e.getException().getValue());
        }
        return response;
    }

    @Override
    public boolean validateResponseImpl(ModbusResponse response) {
        return true;
    }

    @Override
    public void readPDU(ModbusInputStream fifo) throws ModbusNumberException, IOException {
        //no operation
    }

    @Override
    public int getFunction() {
        return ModbusFunctionCode.READ_EXCEPTION_STATUS.toInt();
    }
}
