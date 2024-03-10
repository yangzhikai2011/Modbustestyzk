package com.example.modbustest0309.jlibmodbus.net.transport;

import com.example.modbustest0309.jlibmodbus.serial.SerialPort;

import java.io.IOException;
import java.net.Socket;

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

public class ModbusTransportFactory {
    static public ModbusTransport createRTU(SerialPort serial) {
        return new ModbusTransportRTU(serial);
    }

    static public ModbusTransport createASCII(SerialPort serial) {
        return new ModbusTransportASCII(serial);
    }

    static public ModbusTransport createTCP(Socket socket) throws IOException {
        return new ModbusTransportTCP(socket);
    }
}
