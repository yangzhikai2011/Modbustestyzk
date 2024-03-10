package com.example.modbustest0309;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modbustest0309.examples.Modbus4jUtils;
import com.example.modbustest0309.examples.Modbus4jWriteUtils;
import com.example.modbustest0309.jlibmodbus.Modbus;
import com.example.modbustest0309.jlibmodbus.exception.ModbusIOException;
import com.example.modbustest0309.jlibmodbus.exception.ModbusNumberException;
import com.example.modbustest0309.jlibmodbus.exception.ModbusProtocolException;
import com.example.modbustest0309.jlibmodbus.master.ModbusMaster;
import com.example.modbustest0309.jlibmodbus.master.ModbusMasterFactory;
import com.example.modbustest0309.jlibmodbus.tcp.TcpParameters;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

import java.net.InetAddress;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_1 = findViewById(R.id.button_1);
        Button button_2 = findViewById(R.id.button_2);
        Button button_3 = findViewById(R.id.button_3);

        button_1.setOnClickListener(v -> new Thread(TestActivity::readTest).start());

        button_2.setOnClickListener(v -> new Thread(Modbus4jUtils::batchReads).start());

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    try {
                        Modbus4jWriteUtils.writeHoldingRegister(1,0, 1, DataType.FOUR_BYTE_FLOAT);
                    } catch (ModbusTransportException e) {
                        throw new RuntimeException(e);
                    } catch (ErrorResponseException e) {
                        throw new RuntimeException(e);
                    } catch (ModbusInitException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        });

    }

    /**
     * 读取多个输入寄存器
     */
    private static void readTest() {
        try {
            // 设置主机TCP参数
            TcpParameters tcpParameters = new TcpParameters();

            // 设置TCP的ip地址
            InetAddress adress = InetAddress.getByName("192.168.0.101");

            // TCP参数设置ip地址
            // tcpParameters.setHost(InetAddress.getLocalHost());
            tcpParameters.setHost(adress);

            // TCP设置长连接
            tcpParameters.setKeepAlive(true);
            // TCP设置端口，这里设置是默认端口502
            tcpParameters.setPort(Modbus.TCP_PORT);

            // 创建一个主机
            ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            Modbus.setAutoIncrementTransactionId(true);

            int slaveId = 1;//从机地址
            int offset = 0;//寄存器读取开始地址
            int quantity = 10;//读取的寄存器数量


            try {
                if (!master.isConnected()) {
                    master.connect();// 开启连接
                }

                // 读取对应从机的数据，readInputRegisters读取的写寄存器，功能码04
                int[] registerValues = master.readInputRegisters(slaveId, offset, quantity);

                // 控制台输出
                for (int value : registerValues) {
                    System.out.println("Address: " + offset++ + ", Value: " + value);
                }

            } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
                e.printStackTrace();
            } finally {
                try {
                    master.disconnect();
                } catch (ModbusIOException e) {
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
