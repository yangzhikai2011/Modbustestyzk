package com.example.modbustest0309.jlibmodbus.data;

import com.example.modbustest0309.jlibmodbus.Modbus;
import com.example.modbustest0309.jlibmodbus.exception.IllegalDataAddressException;
import com.example.modbustest0309.jlibmodbus.exception.IllegalDataValueException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Observable;

abstract public class ModbusValues<T> extends Observable implements Iterable<T> {

    abstract public int getQuantity();

    abstract public T get(int offset) throws IllegalDataAddressException;

    abstract public void setImpl(int offset, T value) throws IllegalDataAddressException, IllegalDataValueException;

    abstract public int getByteCount();

    abstract public byte[] getBytes();

    abstract public void setBytesBe(byte[] bytes);

    final public void set(int offset, T value) throws IllegalDataAddressException, IllegalDataValueException {
        setImpl(offset, value);
        setChanged();
        notifyObservers(new int[]{offset, 1});
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < getQuantity();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    try {
                        return get(index++);
                    } catch (IllegalDataAddressException e) {
                        Modbus.log().severe(this.getClass().getSimpleName() + " " + e.getLocalizedMessage() + ": quantity = " + getQuantity() + ", index = " + index);
                        throw new NoSuchElementException();
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void remove() {

            }
        };
    }
}
