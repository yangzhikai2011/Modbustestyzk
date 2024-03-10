package com.example.modbustest0309.jlibmodbus.msg.request;

import com.example.modbustest0309.jlibmodbus.Modbus;
import com.example.modbustest0309.jlibmodbus.data.DataHolder;
import com.example.modbustest0309.jlibmodbus.exception.ModbusNumberException;
import com.example.modbustest0309.jlibmodbus.exception.ModbusProtocolException;
import com.example.modbustest0309.jlibmodbus.msg.base.AbstractMultipleRequest;
import com.example.modbustest0309.jlibmodbus.msg.base.ModbusResponse;
import com.example.modbustest0309.jlibmodbus.msg.response.ReadHoldingRegistersResponse;
import com.example.modbustest0309.jlibmodbus.utils.ModbusFunctionCode;

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

public class ReadHoldingRegistersRequest extends AbstractMultipleRequest {

    public ReadHoldingRegistersRequest() {
        super();
    }

    @Override
    protected Class getResponseClass() {
        return ReadHoldingRegistersResponse.class;
    }

    @Override
    public ModbusResponse process(DataHolder dataHolder) throws ModbusNumberException {
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) getResponse();
        response.setServerAddress(getServerAddress());
        try {
            int[] range = dataHolder.readHoldingRegisterRange(getStartAddress(), getQuantity());
            response.setBuffer(range);
        } catch (ModbusProtocolException e) {
            response.setException();
            response.setModbusExceptionCode(e.getException().getValue());
        }
        return response;
    }

    @Override
    public boolean validateResponseImpl(ModbusResponse response) {
        if (!(response instanceof ReadHoldingRegistersResponse)) {
            return false;
        }
        ReadHoldingRegistersResponse r = (ReadHoldingRegistersResponse) response;
        return (r.getByteCount() == getQuantity() * 2);
    }

    @Override
    public boolean checkAddressRange(int startAddress, int quantity) {
        return Modbus.checkReadRegisterCount(quantity) &&
                Modbus.checkStartAddress(startAddress) &&
                Modbus.checkEndAddress(startAddress + quantity);
    }

    @Override
    public int getFunction() {
        return ModbusFunctionCode.READ_HOLDING_REGISTERS.toInt();
    }
}
